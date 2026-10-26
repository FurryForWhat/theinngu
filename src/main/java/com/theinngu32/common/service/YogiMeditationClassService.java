package com.theinngu32.common.service;

import com.theinngu32.common.dto.yogi_meditation_class.YogiMeditationClassCreateDTO;
import com.theinngu32.common.dto.yogi_meditation_class.YogiMeditationClassResponseDTO;
import com.theinngu32.common.dto.yogi_meditation_class.YogiMeditationClassUpdateDTO;
import com.theinngu32.common.entity.YogiMeditationClass;
import com.theinngu32.common.exception.ResourceNotFoundException;
import com.theinngu32.common.mapper.YogiMeditationClassMapper;
import com.theinngu32.common.repository.YogiMeditationClassRepository;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class YogiMeditationClassService {

    private final YogiMeditationClassMapper yogiMeditationClassMapper;
    private final YogiMeditationClassRepository yogiMeditationClassRepository;
    private final EntityManager entityManager;

    @Transactional(readOnly = true)
    public List<YogiMeditationClassResponseDTO> getAllYogiMeditationClasses() {
        return yogiMeditationClassRepository.findAll()
                .stream()
                .map(yogiMeditationClassMapper::toDTO)
                .toList();
    }

    @Transactional(readOnly = true)
    public YogiMeditationClassResponseDTO getYogiMeditationClassById(Long id) {
        return yogiMeditationClassRepository.findById(id)
                .map(yogiMeditationClassMapper::toDTO)
                .orElseThrow(() -> new ResourceNotFoundException("Yogi Meditation class with %d not found"
                        .formatted(id)));
    }

    @Transactional
    public YogiMeditationClassResponseDTO createYogiMeditationClass(YogiMeditationClassCreateDTO createDTO) {
        YogiMeditationClass yogiMeditationClass = yogiMeditationClassRepository.save(yogiMeditationClassMapper.toEntity(createDTO));
        entityManager.refresh(yogiMeditationClass);
        return yogiMeditationClassMapper.toDTO(yogiMeditationClass);
    }

    @Transactional
    public YogiMeditationClassResponseDTO updateYogiMeditationClass(Long id, YogiMeditationClassUpdateDTO updateDTO) {
        YogiMeditationClass yogiMeditationClass = yogiMeditationClassRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Yogi Meditation class with %d not found"
                        .formatted(id)));
        yogiMeditationClass.getMeditationClass().setId(updateDTO.getMeditationClassId());
        yogiMeditationClass.getYogi().setId(updateDTO.getYogiId());
        yogiMeditationClass.setJoinedDate(updateDTO.getJoinedDate());
        yogiMeditationClass.setStatus(updateDTO.getStatus());
        yogiMeditationClass.setJoinedStatus(updateDTO.getJoinedStatus());
        yogiMeditationClass.setRemark(updateDTO.getRemark());
        yogiMeditationClass.setRating(updateDTO.getRating());
        yogiMeditationClass.setBlacklisted(updateDTO.isBlacklisted());
        yogiMeditationClassRepository.save(yogiMeditationClass);
        yogiMeditationClassRepository.flush();
        entityManager.refresh(yogiMeditationClass);
        return yogiMeditationClassMapper.toDTO(yogiMeditationClass);
    }

    @Transactional
    public Boolean deleteYogiMeditationClass(Long id) {
        if (!yogiMeditationClassRepository.existsById(id)) {
            throw new ResourceNotFoundException("Yogi Meditation class with %d not found".formatted(id));
        }
        yogiMeditationClassRepository.deleteById(id);
        return true;
    }

    @Transactional(readOnly = true)
    public LocalDate getJoinDate(Long yogiId, Long classId) {
        return yogiMeditationClassRepository.findJoinedDateByYogiIdAndClassId(yogiId, classId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Yogi with ID %d is not found for class with ID %d.".formatted(yogiId, classId)
                ));
    }

    @Transactional(readOnly = true)
    public boolean isYogiBlacklistedForClass(Long yogiId, Long classId) {
        return yogiMeditationClassRepository.isBlacklistYogiByClassId(yogiId, classId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Yogi with ID %d is not found in the blacklist for class with ID %d."
                                .formatted(yogiId, classId)
                ));
    }
}
