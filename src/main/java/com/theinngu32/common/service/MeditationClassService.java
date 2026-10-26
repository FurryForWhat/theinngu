package com.theinngu32.common.service;

import com.theinngu32.common.dto.meditation_class.MeditationClassCreateDTO;
import com.theinngu32.common.dto.meditation_class.MeditationClassDTO;
import com.theinngu32.common.dto.SearchRequest;
import com.theinngu32.common.dto.meditation_class.MeditationClassUpdateDTO;
import com.theinngu32.common.entity.Level;
import com.theinngu32.common.entity.MeditationClass;
import com.theinngu32.common.exception.MeditationClassValidationException;
import com.theinngu32.common.entity.SubMissionaryGroup;
import com.theinngu32.common.exception.ResourceNotFoundException;
import com.theinngu32.common.mapper.MeditationClassMapper;
import com.theinngu32.common.repository.LevelRepository;
import com.theinngu32.common.repository.MeditationClassRepository;
import com.theinngu32.common.repository.SubMissionaryGroupRepository;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;


@Service
@RequiredArgsConstructor
public class MeditationClassService {

    private final MeditationClassRepository meditationClassRepository;
    private final MeditationClassMapper meditationClassMapper;
    private final GenericSearchService genericSearchService;
    private final EntityManager entityManager;
    private final LevelRepository levelRepository;
    private final SubMissionaryGroupRepository subMissionaryGroupRepository;

    @Transactional
    public MeditationClassDTO saveNewMeditationClass(MeditationClassCreateDTO meditationClassCreateDTO){
        MeditationClass meditationClass = meditationClassMapper.toEntity(meditationClassCreateDTO);
        List<String> errorMessages = this.validateMeditationClass(meditationClass);
        if(errorMessages.isEmpty()){
            MeditationClass resultEntity = meditationClassRepository.save(meditationClass);
            entityManager.refresh(resultEntity);
            return meditationClassMapper.toDTO(resultEntity);
        }
        throw new MeditationClassValidationException("Validation Failed : ",String.join(", ",errorMessages));
    }

    @Transactional(readOnly = true)
    public List<MeditationClassDTO> getAllMeditationClasses() {
        return meditationClassRepository.findAll().stream().map(meditationClassMapper::toDTO).toList();
    }

    @Transactional
    public void deleteMeditationClass(Long meditationClassId) {
        if(!meditationClassRepository.existsById(meditationClassId)){
            throw new ResourceNotFoundException("Meditation class with id %d not found".formatted(meditationClassId));
        }
        meditationClassRepository.deleteById(meditationClassId);
    }

    @Transactional
    public MeditationClassDTO updateExistingMediationClass(Long id, MeditationClassUpdateDTO meditationClassUpdateDTO) {
        MeditationClass meditationClass = meditationClassRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Meditation class with id %d not found".formatted(id)));
        meditationClass.setTitle(meditationClassUpdateDTO.getTitle());
        meditationClass.setStartDate(meditationClassUpdateDTO.getStartDate());
        meditationClass.setEndDate(meditationClassUpdateDTO.getEndDate());
        meditationClass.setClassType(meditationClassUpdateDTO.getClassType());
        Level level = levelRepository.findById(meditationClassUpdateDTO.getLevelId())
                .orElseThrow(() -> new ResourceNotFoundException("Level with id %d not found".formatted(meditationClassUpdateDTO.getLevelId())));
        meditationClass.setLevel(level);
        SubMissionaryGroup subMissionaryGroup = subMissionaryGroupRepository.findById(meditationClassUpdateDTO.getSubMissionaryGroupId())
                .orElseThrow(() -> new ResourceNotFoundException("Sub missionary group with id %d not found".formatted(meditationClassUpdateDTO.getSubMissionaryGroupId())));
        meditationClass.setSubMissionaryGroup(subMissionaryGroup);
        MeditationClass updatedMediationClass = meditationClassRepository.save(meditationClass);
        meditationClassRepository.flush();
        entityManager.refresh(updatedMediationClass);
        return meditationClassMapper.toDTO(updatedMediationClass);
    }

    public List<String> validateMeditationClass(MeditationClass meditationClass){
        List<String> errorMessages = new ArrayList<>();

        if(meditationClass.getTitle().isEmpty()){
            errorMessages.add("Title cannot be empty.");
        }

        if(meditationClass.getStartDate().isAfter(meditationClass.getEndDate())){
            errorMessages.add("Start date must come before the end date");
        }

        if(meditationClass.getLevel() == null){
            errorMessages.add("Level cannot be null.");
        }

        if(meditationClass.getClassType() == null){
            errorMessages.add("Class type cannot be null.");
        }


        return errorMessages;
    }

    @Transactional(readOnly = true)
    public Page<MeditationClassDTO> findMeditationClassesByFilters(SearchRequest request, Pageable pageable){
        Page<MeditationClass> meditationClasses = genericSearchService.search(request, meditationClassRepository ,pageable);
        return meditationClasses.map(meditationClassMapper::toDTO);
    }

    @Transactional(readOnly = true)
    public MeditationClassDTO findMeditationClassById(long id){
        MeditationClass meditationClass = meditationClassRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Meditation class with id %d not found".formatted(id)));
        return meditationClassMapper.toDTO(meditationClass);
    }

}
