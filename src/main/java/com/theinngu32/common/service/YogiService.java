package com.theinngu32.common.service;

import java.util.List;
import java.util.stream.Collectors;

import com.theinngu32.common.dto.yogi_nrc.YogiNrcCreateDTO;
import com.theinngu32.common.entity.*;
import com.theinngu32.common.exception.ResourceNotFoundException;
import com.theinngu32.common.repository.*;
import com.theinngu32.common.service.validation.YogiValidationService;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.theinngu32.common.dto.SearchRequest;
import com.theinngu32.common.dto.yogi.MasterYogiDTO;
import com.theinngu32.common.dto.yogi.YogiCreateDTO;
import com.theinngu32.common.dto.yogi.YogiDTO;
import com.theinngu32.common.dto.yogi.YogiUpdateDTO;
import com.theinngu32.common.entity.constants.Status;
import com.theinngu32.common.mapper.YogiMapper;

@Service
@RequiredArgsConstructor
public class YogiService {

    private final GenericSearchService genericSearchService;
    private final CityRepository cityRepository;
    private final RegionRepository regionRepository;
    private final YogiRepository yogiRepository;
    private final SubMissionaryGroupRepository subMissionaryGroupRepository;
    private final YogiMapper yogiMapper;
    private final LevelRepository levelRepository;
    private final YogiNrcService yogiNrcService;

    @Transactional
    public YogiDTO createYogi(YogiCreateDTO yogiCreateDTO){
        Yogi yogi = yogiMapper.toEntity(yogiCreateDTO);
        yogi.setStatus(Status.ACTIVE);
        yogi = yogiRepository.save(yogi);

        YogiNrcCreateDTO yogiNrcCreateDTO = new YogiNrcCreateDTO();

        yogiNrcCreateDTO.setYogiId(yogi.getId());
        yogiNrcCreateDTO.setNrcCodeId(yogiCreateDTO.getNrcCodeId());
        yogiNrcCreateDTO.setType(yogiCreateDTO.getNrcType());
        yogiNrcCreateDTO.setPostFixDigit(yogiCreateDTO.getPostFixDigit());

        yogiNrcService.create(yogiNrcCreateDTO);

        return yogiMapper.toDto(yogi);
    }

    @Transactional
    public YogiDTO updateYogi(Long id, YogiUpdateDTO yogiUpdateDTO){
        Yogi yogi = yogiRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Yogi Not Found !!"));
        updateYogiAttributes(yogi, yogiUpdateDTO);
        Yogi updatedYogi = yogiRepository.save(yogi);
        return yogiMapper.toDto(updatedYogi);
    }

    @Transactional
    public void deleteYogi(Long id){
        Yogi yogi = yogiRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Yogi Not Found !!"));
        yogi.setStatus(Status.DELETED);
        yogiRepository.save(yogi);
    }

    @Transactional
    public YogiDTO findYogiById(Long id){
        Yogi yogi = yogiRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Yogi with this id Not Found !!"));
        return yogiMapper.toDto(yogi);
    }

    @Transactional
    public List<YogiDTO> getAllYogis(){
        return yogiRepository.findAll().stream().map(yogiMapper::toDto).collect(Collectors.toList());
    }

    @Transactional
    public Page<YogiDTO> findYogiByFilter(SearchRequest request, Pageable pageable){
        Page<Yogi> yogis = genericSearchService.search(request,yogiRepository,pageable);
        return yogis.map(yogiMapper::toDto);
    }

    private boolean isComplete(MasterYogiDTO dto) {
        return dto.getName() != null &&
                dto.getFatherName() != null &&
                dto.getPhone() != null &&
                dto.getGenderType() != null &&
                dto.getBirthDate() != null &&
                dto.getMonkDate() != null &&
                dto.getCityId() != null &&
                dto.getRegionId() != null &&
                dto.getAddress() != null &&
                dto.getEmergencyContact() != null &&
                dto.getLevelId() != null;
    }

    private void updateYogiAttributes(Yogi yogi, YogiUpdateDTO yogiUpdateDTO){

        City city = cityRepository.findById(yogiUpdateDTO.getCityId())
                .orElseThrow(() -> new ResourceNotFoundException("City with Id %d not found!"));
        Region region = regionRepository.findById(yogiUpdateDTO.getRegionId())
                .orElseThrow(() -> new ResourceNotFoundException("Region with Id %d not found!"));
        SubMissionaryGroup subMissionaryGroup = subMissionaryGroupRepository.findById(yogiUpdateDTO.getSubMissionaryGroupId())
                .orElseThrow(() -> new ResourceNotFoundException("SubMissionaryGroup with Id %d not found!"));
        Level level = levelRepository.findById(yogiUpdateDTO.getLevelId())
                .orElseThrow(() -> new ResourceNotFoundException("Level with Id %d not found!"));

        yogi.setName(yogiUpdateDTO.getName());
        yogi.setFatherName(yogiUpdateDTO.getFatherName());
        yogi.setPhone(yogiUpdateDTO.getPhone());
        yogi.setGenderType(yogiUpdateDTO.getGenderType());
        yogi.setBirthDate(yogiUpdateDTO.getBirthDate());
        yogi.setMonkDate(yogiUpdateDTO.getMonkDate());
        yogi.setCity(city);
        yogi.setRegion(region);
        yogi.setAddress(yogiUpdateDTO.getAddress());
        yogi.setEmergencyContact(yogiUpdateDTO.getEmergencyContact());
        yogi.setOtherMeditationCenter(yogiUpdateDTO.getOtherMeditationCenter());
        yogi.setEducation(yogiUpdateDTO.getEducation());
        yogi.setHealthStatus(yogiUpdateDTO.getHealthStatus());
        yogi.setFoodNotEaten(yogiUpdateDTO.getFoodNotEaten());
        yogi.setBlacklisted(yogiUpdateDTO.isBlacklist());
        yogi.setStatus(yogiUpdateDTO.getStatus());
        yogi.setBlacklistedAt(yogiUpdateDTO.getBlacklistedAt());
        yogi.setCompleted(yogiUpdateDTO.isCompleted());
        yogi.setRating(yogiUpdateDTO.getRating());
        yogi.setSubMissionaryGroup(subMissionaryGroup);
        yogi.setLevel(level);
        yogi.setRemark(yogiUpdateDTO.getRemark());
    }
}
