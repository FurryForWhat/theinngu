package com.theinngu32.common.service;

import com.theinngu32.common.dto.importfile.yogi_import_file.MasterYogiImportFileDTO;
import com.theinngu32.common.dto.importfile.yogi_import_file.YogiImportFileCreateDTO;
import com.theinngu32.common.dto.importfile.yogi_import_file.YogiImportFileResponseDTO;
import com.theinngu32.common.dto.importfile.yogi_import_file.YogiImportFileUpdateDTO;
import com.theinngu32.common.entity.*;
import com.theinngu32.common.entity.importfile.YogiImportFile;
import com.theinngu32.common.exception.ResourceNotFoundException;
import com.theinngu32.common.mapper.YogiImportFileMapper;
import com.theinngu32.common.repository.SubMissionaryGroupRepository;
import com.theinngu32.common.repository.YogiImportFileRepository;
import com.theinngu32.common.service.excel.ProcessExcelService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class YogiImportFileService {
    private final ProcessExcelService processExcelService;
    private final SubMissionaryGroupRepository subMissionaryGroupRepository;
    private final YogiImportFileRepository yogiImportFileRepository;
    private final YogiImportFileMapper yogiImportFileMapper;

    @Transactional
    public YogiImportFileResponseDTO save(MasterYogiImportFileDTO file) {
        YogiImportFileCreateDTO yogiImportFileCreateDTO = (YogiImportFileCreateDTO) file;
        YogiImportFile yogiImportFile = yogiImportFileMapper.toEntity(yogiImportFileCreateDTO);
        yogiImportFile.setImportFile(yogiImportFileCreateDTO.getYogiRegisterFile());
//        SubMissionaryGroup subMissionaryGroup = subMissionaryGroupRepository.findById(file.getSubMissionaryGroupId()).orElseThrow(
//                () -> new ResourceNotFoundException("SubMissionaryGroup with ID %d not found.".formatted(file.getSubMissionaryGroupId()))
//        );
//        yogiImportFile.setSubMissionaryGroup(subMissionaryGroup);
        yogiImportFile = yogiImportFileRepository.save(yogiImportFile);
        processExcelService.uploadAndSaveFile(yogiImportFile);
        return yogiImportFileMapper.toDto(yogiImportFile);
    }

    @Transactional(readOnly = true)
    public List<YogiImportFileResponseDTO> findAll() {
        return yogiImportFileRepository.findAll()
                .stream()
                .map(yogiImportFileMapper::toDto)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public YogiImportFileResponseDTO findById(Long id) {
        YogiImportFile yogiImportFile = yogiImportFileRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("YogiImportFile with ID %d not found.".formatted(id)));
        String fileUrl = processExcelService.getFileDownloadUrl(yogiImportFile);
        YogiImportFileResponseDTO responseDTO = yogiImportFileMapper.toDto(yogiImportFile);
        responseDTO.setFileUrl(fileUrl);
        return responseDTO;
    }

    @Transactional
    public YogiImportFileResponseDTO update(Long id, MasterYogiImportFileDTO fileDetails) {
        YogiImportFile yogiImportFile = yogiImportFileRepository.findById(id).orElseThrow();
        YogiImportFileUpdateDTO yogiImportFileUpdateDTO = (YogiImportFileUpdateDTO) fileDetails;
        SubMissionaryGroup subMissionaryGroup = this.subMissionaryGroupRepository.findById(fileDetails.getSubMissionaryGroupId()).orElseThrow();
        yogiImportFile.setFileName(yogiImportFileUpdateDTO.getFileName());
        yogiImportFile.setSubMissionaryGroup(subMissionaryGroup);
        yogiImportFile.setImportFile(yogiImportFileUpdateDTO.getYogiRegisterFile());
        processExcelService.uploadAndSaveFile(yogiImportFile);
        yogiImportFile = yogiImportFileRepository.save(yogiImportFile);
        return yogiImportFileMapper.toDto(yogiImportFile);
    }

    @Transactional
    public boolean delete(Long id) {
        return yogiImportFileRepository.findById(id).map(file -> {
            yogiImportFileRepository.delete(file);
            return true;
        }).orElse(false);
    }

    public void processFileById(Long recordId) {
        YogiImportFile yogiImportFile = yogiImportFileRepository.findById(recordId)
                .orElseThrow(() -> new ResourceNotFoundException("YogiImportFile with ID %d not found.".formatted(recordId)));
        processExcelService.runFile(yogiImportFile);
    }
}
