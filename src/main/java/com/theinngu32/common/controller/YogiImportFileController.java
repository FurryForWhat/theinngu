package com.theinngu32.common.controller;

import com.theinngu32.common.dto.importfile.yogi_import_file.YogiImportFileCreateDTO;
import com.theinngu32.common.dto.importfile.yogi_import_file.YogiImportFileResponseDTO;
import com.theinngu32.common.dto.importfile.yogi_import_file.YogiImportFileUpdateDTO;
import com.theinngu32.common.entity.constants.ProcessingStatus;
import com.theinngu32.common.entity.constants.Status;
import com.theinngu32.common.service.YogiImportFileService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/yogi_import_files")
@RequiredArgsConstructor
public class YogiImportFileController {
    private final YogiImportFileService yogiImportFileService;

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<YogiImportFileResponseDTO> create(
            @RequestPart("yogiRegisterFile") MultipartFile yogiRegisterFile,
            @RequestPart(value = "name") @RequestParam(value = "name") String name,
            @RequestPart(value = "status") @RequestParam(value = "status") Status status,
            @RequestPart(value = "processingStatus") @RequestParam(value = "processingStatus") ProcessingStatus processingStatus,
            @RequestPart(value = "subMissionaryGroupId") @RequestParam(value = "subMissionaryGroupId") Long subMissionaryGroupId) {

        YogiImportFileCreateDTO file = new YogiImportFileCreateDTO();
        file.setYogiRegisterFile(yogiRegisterFile);
        file.setFileName(name);
        file.setFileStatus(status);
        file.setProcessingStatus(processingStatus);
        file.setSubMissionaryGroupId(subMissionaryGroupId);

        YogiImportFileResponseDTO createdFile = yogiImportFileService.save(file);
        return ResponseEntity.ok(createdFile);
    }

    @GetMapping
    public ResponseEntity<List<YogiImportFileResponseDTO>> getAll() {
        List<YogiImportFileResponseDTO> files = yogiImportFileService.findAll();
        return ResponseEntity.ok(files);
    }

    @GetMapping("/{id}")
    public ResponseEntity<YogiImportFileResponseDTO> getById(@PathVariable Long id) {
        YogiImportFileResponseDTO file = yogiImportFileService.findById(id);
        return ResponseEntity.ok(file);
    }

    @PutMapping("/{id}")
    public ResponseEntity<YogiImportFileResponseDTO> update(@PathVariable Long id, @RequestBody YogiImportFileUpdateDTO fileDetails) {
        YogiImportFileResponseDTO updatedFile = yogiImportFileService.update(id, fileDetails);
        return ResponseEntity.ok(updatedFile);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        boolean deleted = yogiImportFileService.delete(id);
        return deleted ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }

    @GetMapping("/{recordId}/run")
    public ResponseEntity<Void> run(@PathVariable Long recordId) {
        this.yogiImportFileService.processFileById(recordId);
        return ResponseEntity.noContent().build();
    }
}
