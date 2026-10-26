package com.theinngu32.common.controller;

import com.theinngu32.common.annotation.APIResource;
import com.theinngu32.common.dto.importfile.yogi_enroll_file.YogiEnrollFileCreateDTO;
import com.theinngu32.common.dto.importfile.yogi_enroll_file.YogiEnrollFileResponseDTO;
import com.theinngu32.common.dto.importfile.yogi_enroll_file.YogiEnrollFileUpdateDTO;
import com.theinngu32.common.entity.constants.ProcessingStatus;
import com.theinngu32.common.entity.constants.Status;
import com.theinngu32.common.service.YogiEnrollFileService;
import com.theinngu32.common.utils.JsonUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Tag(name = "Yogi Enroll File Management", description = "Yogi Enroll File Management APIs")
@APIResource(apiPath = "yogi-enroll-file")
@RequiredArgsConstructor
public class YogiEnrollFileController {

    private final YogiEnrollFileService yogiEnrollFileService;
    private final Logger logger = LoggerFactory.getLogger(YogiEnrollFileController.class);

    @Operation(summary = "Retrieve All Yogi Enroll Files", description = "Retrieve all yogi enroll file APIs")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Retrieve all yogi enroll files",
                    content = @Content(
                            mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = YogiEnrollFileResponseDTO.class))
                    )
            )
    })
    @GetMapping
    public ResponseEntity<?> getAllYogiEnrollFiles() {
        try {
            logger.info("Request Retrieve All Yogi Enroll Files");
            var yogiEnrollFiles = yogiEnrollFileService.getAllYogiEnrollFiles();
            logger.info("Response Retrieve All Yogi Enroll Files");
            return ResponseEntity.ok(yogiEnrollFiles);
        } catch (Exception e) {
            logger.error("Error Occur In Retrieve All Yogi Enroll Files {}", e.getMessage());
            throw e;
        }
    }

    @Operation(summary = "Retrieve Yogi Enroll File By Id", description = "Retrieve yogi enroll file by id APIs")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Retrieve yogi enroll file by id",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = YogiEnrollFileResponseDTO.class)
                    )
            )
    })
    @GetMapping("/{id}")
    public ResponseEntity<?> getYogiEnrollFileById(@PathVariable Long id) {
        try {
            logger.info("Request Retrieve Yogi Enroll File by ID {}", id);
            var yogiEnrollFile = yogiEnrollFileService.getYogiEnrollFileById(id);
            logger.info("Response Retrieve Yogi Enroll File by ID {}", id);
            return ResponseEntity.ok(yogiEnrollFile);
        } catch (Exception e) {
            logger.error("Error Occur In Retrieve Yogi Enroll File {}", e.getMessage());
            throw e;
        }
    }

    @Operation(summary = "Create Yogi Enroll File", description = "Create yogi enroll file APIs")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201",
                    description = "Yogi enroll file created",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = YogiEnrollFileResponseDTO.class)
                    )
            )
    })
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> createYogiEnrollFile(
            @RequestPart("yogiEnrollFile") MultipartFile yogiEnrollFile,
            @RequestPart(value = "fileName") @RequestParam(value = "fileName") String fileName,
            @RequestPart(value = "fileStatus") @RequestParam(value = "fileStatus") Status status,
            @RequestPart(value = "version") @RequestParam(value = "version") String version,
            @RequestPart(value = "processingStatus") @RequestParam(value = "processingStatus") ProcessingStatus processingStatus,
            @RequestPart(value = "meditationClassId") @RequestParam(value = "meditationClassId") Long meditationClassId
    ) {
        YogiEnrollFileCreateDTO fileCreateDTO = new YogiEnrollFileCreateDTO();
        fileCreateDTO.setYogiEnrollFile(yogiEnrollFile);
        fileCreateDTO.setFileName(fileName);
        fileCreateDTO.setFileStatus(status);
        fileCreateDTO.setVersion(version);
        fileCreateDTO.setMeditationClassId(meditationClassId);
        fileCreateDTO.setProcessingStatus(processingStatus);

        try {
            logger.info("Request Create Yogi Enroll File");
            var createdFile = yogiEnrollFileService.createYogiEnrollFile(fileCreateDTO);
            logger.info("Response Create Yogi Enroll File {}", JsonUtil.toPrettyJson(createdFile));
            return ResponseEntity.ok(createdFile);
        } catch (Exception e) {
            logger.error("Error Occur In Create Yogi Enroll File {}", e.getMessage());
            throw e;
        }
    }

    @Operation(summary = "Update Yogi Enroll File", description = "Update yogi enroll file APIs")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Yogi enroll file updated",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = YogiEnrollFileResponseDTO.class)
                    )
            )
    })
    @PutMapping(value = "/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> updateYogiEnrollFile(
            @PathVariable Long id,
            @RequestPart("updateYogiEnrollFile") MultipartFile yogiEnrollFile,
            @RequestPart(value = "fileName") @RequestParam(value = "fileName") String fileName,
            @RequestPart(value = "fileStatus") @RequestParam(value = "fileStatus") Status status,
            @RequestPart(value = "version") @RequestParam(value = "version") String version,
            @RequestPart(value = "processingStatus") @RequestParam(value = "processingStatus") ProcessingStatus processingStatus,
            @RequestPart(value = "meditationClassId") @RequestParam(value = "meditationClassId") Long meditationClassId
    ) {
        YogiEnrollFileUpdateDTO fileUpdateDTO = new YogiEnrollFileUpdateDTO();
        fileUpdateDTO.setYogiEnrollFile(yogiEnrollFile);
        fileUpdateDTO.setFileName(fileName);
        fileUpdateDTO.setFileStatus(status);
        fileUpdateDTO.setVersion(version);
        fileUpdateDTO.setMeditationClassId(meditationClassId);
        fileUpdateDTO.setProcessingStatus(processingStatus);

        try {
            logger.info("Request Update Yogi Enroll File {}", id);
            var updatedFile = yogiEnrollFileService.updateYogiEnrollFile(id, fileUpdateDTO);
            logger.info("Response Update Yogi Enroll File {}", JsonUtil.toPrettyJson(updatedFile));
            return ResponseEntity.ok(updatedFile);
        } catch (Exception e) {
            logger.error("Error Occur In Update Yogi Enroll File {}", e.getMessage());
            throw e;
        }
    }

    @Operation(summary = "Delete Yogi Enroll File", description = "Delete yogi enroll file APIs")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Yogi enroll file deleted",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = Boolean.class)
                    )
            )
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteYogiEnrollFile(@PathVariable Long id) {
        try {
            logger.info("Request Delete Yogi Enroll File {}", id);
            var deleteStatus = yogiEnrollFileService.deleteYogiEnrollFile(id);
            logger.info("Response Delete Yogi Enroll File {}", deleteStatus);
            return deleteStatus ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
        } catch (Exception e) {
            logger.error("Error Occur In Delete Yogi Enroll File {}", e.getMessage());
            throw e;
        }
    }
}
