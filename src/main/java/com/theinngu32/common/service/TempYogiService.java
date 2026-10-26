package com.theinngu32.common.service;

import com.theinngu32.common.dto.SearchRequest;
import com.theinngu32.common.dto.temp_yogi.TempYogiCreateDTO;
import com.theinngu32.common.dto.temp_yogi.TempYogiResponseDTO;
import com.theinngu32.common.dto.temp_yogi.TempYogiUpdateDTO;
import com.theinngu32.common.entity.TempYogi;
import com.theinngu32.common.exception.ResourceNotFoundException;
import com.theinngu32.common.mapper.TempYogiMapper;
import com.theinngu32.common.repository.TempYogiRepository;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TempYogiService {

    private final TempYogiRepository tempYogiRepository;
    private final TempYogiMapper tempYogiMapper;
    private final GenericSearchService genericSearchService;
    private final EntityManager entityManager;

    @Transactional(readOnly = true)
    public List<TempYogiResponseDTO> getAllTempYogi(String recordType, Long recordId) {
        List<TempYogi> tempYogis = tempYogiRepository.findByRecordTypeAndRecordId(recordType, recordId)
                .orElseThrow(() -> new ResourceNotFoundException("Temp Yogis not found for "+ recordId + "-"+ recordType));
        return tempYogis.stream()
                .map(tempYogiMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public TempYogiResponseDTO getTempYogiById(Long id) {
        TempYogi tempYogi = tempYogiRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Temp Yogi with ID %d Not Found".formatted(id)));
        return tempYogiMapper.toDTO(tempYogi);
    }

    @Transactional(readOnly = true)
    public List<TempYogiResponseDTO> getTempYogiByCreatedById(Long createdById) {
        List<TempYogi> tempYogis = tempYogiRepository.findByCreatedById(createdById)
                .orElseThrow(() -> new ResourceNotFoundException("Temp Yogi with Created By ID %d Not Found".formatted(createdById)));
        return tempYogis.stream()
                .map(tempYogiMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<TempYogiResponseDTO> getTempYogiByRecordType(String recordType) {
        List<TempYogi> tempYogis = tempYogiRepository.findByRecordType(recordType)
                .orElseThrow(() -> new ResourceNotFoundException("Temp Yogi with Record Type %s Not Found".formatted(recordType)));
        return tempYogis.stream()
                .map(tempYogiMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Transactional
    public TempYogiResponseDTO createTempYogi(TempYogiCreateDTO tempYogiCreateDTO) {
        TempYogi tempYogi = tempYogiMapper.toEntity(tempYogiCreateDTO);
        tempYogiRepository.save(tempYogi);
        entityManager.refresh(tempYogi);
        return tempYogiMapper.toDTO(tempYogi);
    }

    @Transactional
    public TempYogiResponseDTO updateTempYogi(Long id, TempYogiUpdateDTO tempYogiUpdateDTO) {

        TempYogi tempYogi = tempYogiRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Temp Yogi with ID %d Not Found".formatted(id)));

        tempYogi.setName(tempYogiUpdateDTO.getName());
        tempYogi.setFatherName(tempYogiUpdateDTO.getFatherName());
        tempYogi.setYogiId(tempYogiUpdateDTO.getYogiId());
        tempYogi.setPhone(tempYogiUpdateDTO.getPhone());
        tempYogi.setNrc(tempYogiUpdateDTO.getNrc());
        tempYogi.setGender(tempYogiUpdateDTO.getGender());
        tempYogi.setAddress(tempYogiUpdateDTO.getAddress());
        tempYogi.setEmergencyContact(tempYogiUpdateDTO.getEmergencyContact());
        tempYogi.setBirthDate(tempYogiUpdateDTO.getBirthDate());
        tempYogi.setMonkDate(tempYogiUpdateDTO.getMonkDate());
        tempYogi.setCity(tempYogiUpdateDTO.getCityId());
        tempYogi.setRegion(tempYogiUpdateDTO.getRegionId());
        tempYogi.setBlacklist(tempYogiUpdateDTO.isBlacklist());
        tempYogi.setFoodNotEaten(tempYogiUpdateDTO.getFoodNotEaten());
        tempYogi.setHealthStatus(tempYogiUpdateDTO.getHealthStatus());
        tempYogi.setEducation(tempYogiUpdateDTO.getEducation());
        tempYogi.setOtherMedicationCenter(tempYogiUpdateDTO.getOtherMedicationCenter());
        tempYogi.setLevel(tempYogiUpdateDTO.getLevelId());
        tempYogi.setYogiStatus(tempYogiUpdateDTO.getYogiStatus());
        tempYogi.setRemark(tempYogiUpdateDTO.getRemark());

        TempYogi updatedTempYogi = tempYogiRepository.save(tempYogi);
        tempYogiRepository.flush();
        entityManager.refresh(tempYogi);
        return tempYogiMapper.toDTO(updatedTempYogi);
    }

    @Transactional
    public Boolean deleteTempYogi(Long id) {
        TempYogi tempYogi = tempYogiRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Temp Yogi with ID %d Not Found"
                        .formatted(id)));
        tempYogiRepository.delete(tempYogi);
        return true;
    }

    @Transactional(readOnly = true)
    public Page<TempYogiResponseDTO> findTempYogiByFilter(SearchRequest request, Pageable pageable) {
        Page<TempYogi> tempYogis = genericSearchService.search(request, tempYogiRepository, pageable);
        return tempYogis.map(tempYogiMapper::toDTO);
    }

}
