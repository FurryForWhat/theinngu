package com.theinngu32.common.service;

import com.theinngu32.common.dto.importfile.yogi_enroll_file.YogiEnrollFileCreateDTO;
import com.theinngu32.common.dto.importfile.yogi_enroll_file.YogiEnrollFileResponseDTO;
import com.theinngu32.common.dto.importfile.yogi_enroll_file.YogiEnrollFileUpdateDTO;
import com.theinngu32.common.entity.MeditationClass;
import com.theinngu32.common.entity.importfile.YogiEnrollFile;
import com.theinngu32.common.exception.ResourceNotFoundException;
import com.theinngu32.common.mapper.YogiEnrollFileMapper;
import com.theinngu32.common.repository.MeditationClassRepository;
import com.theinngu32.common.repository.YogiEnrollFileRepository;
import com.theinngu32.common.service.excel.ProcessExcelService;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class YogiEnrollFileService {

    private final YogiEnrollFileRepository yogiEnrollFileRepository;
    private final YogiEnrollFileMapper yogiEnrollFileMapper;
    private final ProcessExcelService processExcelService;
    private final MeditationClassRepository meditationClassRepository;
    private final EntityManager entityManager;

    @Transactional(readOnly = true)
    public List<YogiEnrollFileResponseDTO> getAllYogiEnrollFiles() {
        return yogiEnrollFileRepository.findAll().stream()
                .map(yogiEnrollFileMapper::toDto)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public YogiEnrollFileResponseDTO getYogiEnrollFileById(Long id) {
        YogiEnrollFile yogiEnrollFile = yogiEnrollFileRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Yogi Enroll File With Id %d Not Found".formatted(id)));
        return yogiEnrollFileMapper.toDto(yogiEnrollFile);
    }

    @Transactional
    public YogiEnrollFileResponseDTO createYogiEnrollFile(YogiEnrollFileCreateDTO yogiEnrollFileCreateDTO) {
        YogiEnrollFile yogiEnrollFile = yogiEnrollFileMapper.toEntity(yogiEnrollFileCreateDTO);
        yogiEnrollFile.setImportFile(yogiEnrollFileCreateDTO.getYogiEnrollFile());
        processExcelService.uploadAndSaveFile(yogiEnrollFile);
        entityManager.refresh(yogiEnrollFile);
        yogiEnrollFile = yogiEnrollFileRepository.save(yogiEnrollFile);
        return yogiEnrollFileMapper.toDto(yogiEnrollFile);
    }

    @Transactional
    public YogiEnrollFileResponseDTO updateYogiEnrollFile(Long id, YogiEnrollFileUpdateDTO yogiEnrollFileUpdateDTO) {
        YogiEnrollFile yogiEnrollFile = yogiEnrollFileRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Yogi Enroll File With Id %d Not Found".formatted(id)));
        MeditationClass meditationClass = meditationClassRepository.findById(yogiEnrollFileUpdateDTO.getMeditationClassId())
                .orElseThrow(() -> new ResourceNotFoundException("Meditation Class With Id %d Not Found".formatted(yogiEnrollFileUpdateDTO.getMeditationClassId())));

        yogiEnrollFile.setMeditationClass(meditationClass);
        yogiEnrollFile.setFileName(yogiEnrollFileUpdateDTO.getFileName());
        yogiEnrollFile.setFileStatus(yogiEnrollFileUpdateDTO.getFileStatus());
        yogiEnrollFile.setProcessingStatus(yogiEnrollFileUpdateDTO.getProcessingStatus());
        yogiEnrollFile.setVersion(yogiEnrollFileUpdateDTO.getVersion());
        yogiEnrollFile.setImportFile(yogiEnrollFileUpdateDTO.getYogiEnrollFile());
        processExcelService.uploadAndSaveFile(yogiEnrollFile);
        yogiEnrollFile = yogiEnrollFileRepository.save(yogiEnrollFile);
        entityManager.refresh(yogiEnrollFile);
        return yogiEnrollFileMapper.toDto(yogiEnrollFile);
    }

    @Transactional
    public boolean deleteYogiEnrollFile(Long id) {
        return yogiEnrollFileRepository.findById(id)
                .map(file -> {
                    yogiEnrollFileRepository.delete(file);
                    return true;
                })
                .orElse(false);

    }
}
