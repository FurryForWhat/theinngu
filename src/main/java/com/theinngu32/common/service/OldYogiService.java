package com.theinngu32.common.service;

import com.theinngu32.common.dto.oldyogi.OldYogiCreateDTO;
import com.theinngu32.common.dto.oldyogi.OldYogiResponseDTO;
import com.theinngu32.common.dto.oldyogi.OldYogiUpdateDTO;
import com.theinngu32.common.entity.OldYogi;
import com.theinngu32.common.entity.constants.Gender;
import com.theinngu32.common.entity.constants.Status;
import com.theinngu32.common.exception.ResourceNotFoundException;
import com.theinngu32.common.mapper.OldYogiMapper;
import com.theinngu32.common.repository.OldYogiRepository;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OldYogiService {

    private static final Logger logger = LoggerFactory.getLogger(OldYogiService.class);

    private final OldYogiRepository oldYogiRepository;
    private final OldYogiMapper oldYogiMapper;
    private final EntityManager entityManager;

    @Transactional
    public OldYogiResponseDTO createOldYogi(OldYogiCreateDTO oldYogiCreateDTO) {
        OldYogi oldyogi = oldYogiMapper.toEntity(oldYogiCreateDTO);
        logger.info("Saving old yogi: {}", oldyogi);
        OldYogi savedOldYogi = oldYogiRepository.save(oldyogi);
        entityManager.refresh(savedOldYogi);
        return oldYogiMapper.toDTO(savedOldYogi);
    }

    @Transactional
    public OldYogiResponseDTO updateOldYogi(Long id, OldYogiUpdateDTO oldYogiUpdateDTO) {
        OldYogi oldYogi = oldYogiRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Old Yogi with Id %d not found".formatted(id)));
        if (oldYogiUpdateDTO.getName() != null) oldYogi.setName(oldYogiUpdateDTO.getName());
        if (oldYogiUpdateDTO.getFatherName() != null) oldYogi.setFatherName(oldYogiUpdateDTO.getFatherName());
        if (oldYogiUpdateDTO.getHealthStatus() != null) oldYogi.setHealthStatus(oldYogiUpdateDTO.getHealthStatus());
        if (oldYogiUpdateDTO.getFoodNotEaten() != null) oldYogi.setFoodNotEaten(oldYogiUpdateDTO.getFoodNotEaten());
        if (oldYogiUpdateDTO.getAddress() != null) oldYogi.setAddress(oldYogiUpdateDTO.getAddress());
        if (oldYogiUpdateDTO.getRemark() != null) oldYogi.setRemark(oldYogiUpdateDTO.getRemark());
        if (oldYogiUpdateDTO.getGenderType() != null ) oldYogi.setGenderType(Gender.valueOf(oldYogiUpdateDTO.getGenderType().toString()));
        if (oldYogiUpdateDTO.getOtherMeditationCenter() != null) oldYogi.setOtherMeditationCenter(oldYogiUpdateDTO.getOtherMeditationCenter());
        if (oldYogiUpdateDTO.getAge() != null) oldYogi.setAge(oldYogiUpdateDTO.getAge());
        if (oldYogiUpdateDTO.getNrc() != null) oldYogi.setNrc(oldYogiUpdateDTO.getNrc());

        oldYogi = oldYogiRepository.save(oldYogi);
        oldYogiRepository.flush();
        entityManager.refresh(oldYogi);
        return oldYogiMapper.toDTO(oldYogi);
    }

    @Transactional
    public boolean deleteYogi(Long id) {
        OldYogi oldYogi = oldYogiRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Old Yogi with Id %d not found".formatted(id)));
        oldYogi.setStatus(Status.DELETED);
        oldYogiRepository.save(oldYogi);
        return true;
    }

    @Transactional
    public List<OldYogiResponseDTO> getAllOldYogis() {
        return oldYogiRepository.findAll()
                .stream()
                .map(oldYogiMapper::toDTO)
                .collect(Collectors.toList());
    }

}
