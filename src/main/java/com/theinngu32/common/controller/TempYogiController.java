package com.theinngu32.common.controller;

import com.theinngu32.common.annotation.APIResource;
import com.theinngu32.common.dto.SearchRequest;
import com.theinngu32.common.dto.temp_yogi.TempYogiCreateDTO;
import com.theinngu32.common.dto.temp_yogi.TempYogiResponseDTO;
import com.theinngu32.common.dto.temp_yogi.TempYogiUpdateDTO;
import com.theinngu32.common.service.TempYogiService;
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
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Temp Yogis Management", description = "Temp Yogis Management APIs")
@RequiredArgsConstructor
@APIResource(apiPath = "/temp-yogi")
public class TempYogiController {

    private static final Logger logger = LoggerFactory.getLogger(TempYogiController.class);
    private final TempYogiService tempYogiService;

    @Operation(
            summary = "Retrieve All Temp Yogis By Record Type And Record Id",
            description = "Retrieve all temp yogi by record type and record id APIs"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Retrieve all temp yogis by record type and record id",
                    content = @Content(
                            mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = TempYogiResponseDTO.class))
                    )
            )
    })
    @GetMapping("/record-type/{recordType}/record-id/{recordId}")
    public ResponseEntity<?> getAllTempYogis(@PathVariable String recordType, @PathVariable Long recordId) {
        try {
            logger.info("Request Retrieve All Temp Yogis By Record Type {} And Record Id {}", recordType, recordId);
            var tempYogis = tempYogiService.getAllTempYogi(recordType, recordId);
            logger.info("Response Retrieve All Temp Yogis By Record Type And Record Id");
            return ResponseEntity.ok(tempYogis);
        } catch (Exception e) {
            logger.error("Error Occur in Retrieve All Temp Yogis: {}", e.getMessage());
            throw e;
        }
    }

    @Operation(summary = "Retrieve Temp Yogi By Id", description = "Retrieve temp yogi by id APIs")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Retrieve temp yogi by id",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = TempYogiResponseDTO.class)
                    )
            )
    })
    @GetMapping("/{id}")
    public ResponseEntity<?> getTempYogiById(@PathVariable Long id) {
        try {
            logger.info("Request Retrieve Temp Yogi By Id: {} ", id);
            var tempYogi = tempYogiService.getTempYogiById(id);
            logger.info("Response Retrieve Temp Yogi By Id: {} ", tempYogi);
            return ResponseEntity.ok(tempYogi);
        } catch (Exception e) {
            logger.error("Error Occur in Retrieve Temp Yogi by Id: {}", e.getMessage());
            throw e;
        }
    }


    @Operation(
            summary = "Retrieve Temp Yogis By Created by Id",
            description = "Retrieve temp yogis by created by id APIs"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Retrieve temp yogis by created by id",
                    content = @Content(
                            mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = TempYogiResponseDTO.class))
                    )
            )
    })
    @GetMapping("/created-by/{createdById}")
    public ResponseEntity<?> getTempYogiByCreatedBy(@PathVariable Long createdById) {
        try {
            logger.info("Request Retrieve Temp Yogis By Created By Id: {}", createdById);
            var tempYogis = tempYogiService.getTempYogiByCreatedById(createdById);
            logger.info("Response Retrieve Temp Yogis By Created By Id: {}", JsonUtil.toPrettyJson(tempYogis));
            return ResponseEntity.ok(tempYogis);
        } catch (Exception e) {
            logger.error("Error Occur in Retrieve Temp Yogis By Created By Id: {}", e.getMessage());
            throw e;
        }
    }

    @Operation(summary = "Retrieve Temp Yogi By Record Type", description = "Retrieve temp yogi by record type APIs")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Retrieve temp yogi by record type",
                    content = @Content(
                            mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = TempYogiResponseDTO.class))
                    )
            )
    })
    @GetMapping("/record-type/{recordType}")
    public ResponseEntity<?> getTempYogiByRecordType(@PathVariable String recordType) {
        try {
            logger.info("Request Retrieve Temp Yogi By Record Type: {}", recordType);
            var tempYogi = tempYogiService.getTempYogiByRecordType(recordType);
            logger.info("Response Retrieve Temp Yogi By Record Type: {}", JsonUtil.toPrettyJson(tempYogi));
            return ResponseEntity.ok(tempYogi);
        } catch (Exception e) {
            logger.error("Error Occur in Retrieve Temp Yogi By Record Type: {}", e.getMessage());
            throw e;
        }
    }

    @Operation(summary = "Create Temp Yogi", description = "Create temp yogi APIs")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201",
                    description = "Temp yogi created",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = TempYogiResponseDTO.class)
                    )
            )
    })
    @PostMapping("")
    public ResponseEntity<?> createTempYogi(@RequestBody TempYogiCreateDTO tempYogiCreateDTO) {
        try {
            logger.info("Request Create Temp Yogi: {}", JsonUtil.toPrettyJson(tempYogiCreateDTO));
            var tempYogi = tempYogiService.createTempYogi(tempYogiCreateDTO);
            logger.info("Response Create Temp Yogi: {}", JsonUtil.toPrettyJson(tempYogi));
            return ResponseEntity.status(HttpStatus.CREATED).body(tempYogi);
        } catch (Exception e) {
            logger.error("Error occur in Create Temp Yogi: {}", e.getMessage());
            throw e;
        }
    }

    @Operation(summary = "Update Temp Yogi", description = "Update temp yogi APIs")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Temp yogi updated",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = TempYogiResponseDTO.class)
                    )
            )
    })
    @PutMapping("/{id}")
    public ResponseEntity<?> updateTempYogi(@PathVariable Long id, @RequestBody TempYogiUpdateDTO tempYogiUpdateDTO) {
        try {
            logger.info("Request Update Temp Yogi: {} with {}", id, JsonUtil.toPrettyJson(tempYogiUpdateDTO));
            var updateTempYogi = tempYogiService.updateTempYogi(id, tempYogiUpdateDTO);
            logger.info("Response Update Temp Yogi: {}", JsonUtil.toPrettyJson(updateTempYogi));
            return ResponseEntity.ok(updateTempYogi);
        } catch (Exception e) {
            logger.error("Error occur in Update Temp Yogi: {}", e.getMessage());
            throw e;
        }
    }

    @Operation(summary = "Delete Temp Yogi", description = "Delete temp yogi APIs")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Temp Yogi deleted",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = Boolean.class)
                    )
            )
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteTempYogi(@PathVariable Long id) {
        try {
            logger.info("Request Delete Temp Yogi: {}", id);
            var deleteStatus = tempYogiService.deleteTempYogi(id);
            logger.info("Response Delete Temp Yogi: {}", deleteStatus);
            return ResponseEntity.ok(deleteStatus);
        } catch (Exception e) {
            logger.error("Error occur in Delete Temp Yogi: {}", e.getMessage());
            throw e;
        }
    }

    @Operation(
            summary = "Find Temp Yogis By Filter",
            description = "Find temp yogis by filter APIs"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Temp yogis by filter found",
                    content = @Content(
                            mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = TempYogiResponseDTO.class))
                    )
            )
    })
    @PostMapping("/search")
    public ResponseEntity<?> findTempYogisByFilter(
            @RequestBody SearchRequest searchRequest,
            @RequestParam int page,
            @RequestParam int size
    ) {
        Pageable pageable = PageRequest.of(page, size);
        try {
            logger.info("Request Find Temp Yogis By Filter: {}", JsonUtil.toPrettyJson(searchRequest));
            var tempYogis = tempYogiService.findTempYogiByFilter(searchRequest, pageable);
            logger.info("Response Find Temp Yogis By Filter: {}", JsonUtil.toPrettyJson(tempYogis));
            return ResponseEntity.ok(tempYogis);
        } catch (Exception e) {
            logger.error("Error occur in Find Temp Yogis By Filter: {}", e.getMessage());
            throw e;
        }
    }
}
