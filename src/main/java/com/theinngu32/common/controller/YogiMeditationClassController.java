package com.theinngu32.common.controller;

import com.theinngu32.common.annotation.APIResource;
import com.theinngu32.common.dto.yogi_meditation_class.YogiMeditationClassCreateDTO;
import com.theinngu32.common.dto.yogi_meditation_class.YogiMeditationClassResponseDTO;
import com.theinngu32.common.dto.yogi_meditation_class.YogiMeditationClassUpdateDTO;
import com.theinngu32.common.service.YogiMeditationClassService;
import com.theinngu32.common.utils.JsonUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.ArraySchema;

import java.time.LocalDate;
import java.util.List;

@Tag(name = "Yogi Meditation Class Management", description = "APIs for managing Yogi Meditation Classes")
@APIResource(apiPath = "/yogi-meditation-classes")
@RequiredArgsConstructor
@Slf4j
public class YogiMeditationClassController {
    
    private final YogiMeditationClassService yogiMeditationClassService;

    @Operation(summary = "Retrieve All Yogi Meditation Classes", description = "Retrieve all yogi meditation classes APIs")
    @ApiResponse(responseCode = "200", description = "Retrieve all yogi meditation classes",
            content = @Content(mediaType = "application/json",
                    array = @ArraySchema(schema = @Schema(implementation = YogiMeditationClassResponseDTO.class))))
    @GetMapping
    public ResponseEntity<List<YogiMeditationClassResponseDTO>> getAllYogiMeditationClasses() {
        log.info("Request to retrieve all yogi meditation classes received.");
        var classes = yogiMeditationClassService.getAllYogiMeditationClasses();
        log.info("Yogi Meditation classes retrieved: {}", JsonUtil.toPrettyJson(classes));
        return ResponseEntity.ok(classes);
    }

    @Operation(summary = "Retrieve Yogi Meditation Class By Id", description = "Retrieve yogi meditation class by id API")
    @ApiResponse(responseCode = "200", description = "Retrieve yogi meditation class by id",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = YogiMeditationClassResponseDTO.class)))
    @GetMapping("/{id}")
    public ResponseEntity<YogiMeditationClassResponseDTO> getYogiMeditationClassById(@PathVariable Long id) {
        log.info("Request to retrieve yogi meditation class with ID: {}", id);
        var yogiClass = yogiMeditationClassService.getYogiMeditationClassById(id);
        if (yogiClass != null) {
            log.info("Yogi Meditation class retrieved: {}", JsonUtil.toPrettyJson(yogiClass));
        } else {
            log.warn("Yogi Meditation class with ID {} not found.", id);
        }
        return ResponseEntity.ok(yogiClass);
    }

    @Operation(summary = "Create Yogi Meditation Class", description = "Create a new yogi meditation class API")
    @ApiResponse(responseCode = "201", description = "Yogi Meditation class created",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = Long.class)))
    @PostMapping
    public ResponseEntity<YogiMeditationClassResponseDTO> createYogiMeditationClass(@RequestBody YogiMeditationClassCreateDTO createDTO) {
        log.info("Request to create yogi meditation class: {}", JsonUtil.toPrettyJson(createDTO));
        var yogiMeditationClass = yogiMeditationClassService.createYogiMeditationClass(createDTO);
        log.info("Yogi Meditation class created with ID: {}", JsonUtil.toPrettyJson(yogiMeditationClass));
        return ResponseEntity.ok(yogiMeditationClass);
    }

    @Operation(summary = "Update Yogi Meditation Class", description = "Update an existing yogi meditation class API")
    @ApiResponse(responseCode = "200", description = "Yogi Meditation class updated",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = YogiMeditationClassResponseDTO.class)))
    @PutMapping("/{id}")
    public ResponseEntity<YogiMeditationClassResponseDTO> updateYogiMeditationClass(
            @PathVariable Long id, 
            @RequestBody YogiMeditationClassUpdateDTO updateDTO
    ) {
        log.info("Request to update yogi meditation class with ID: {}", id);
        var updatedClass = yogiMeditationClassService.updateYogiMeditationClass(id, updateDTO);
        if (updatedClass != null) {
            log.info("Yogi Meditation class updated: {}", JsonUtil.toPrettyJson(updatedClass));
        } else {
            log.warn("Yogi Meditation class with ID {} not found for update.", id);
        }
        return ResponseEntity.ok(updatedClass);
    }

    @Operation(summary = "Delete Yogi Meditation Class", description = "Delete a yogi meditation class API")
    @ApiResponse(responseCode = "200", description = "Yogi Meditation class deleted",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = Boolean.class)))
    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> deleteYogiMeditationClass(@PathVariable Long id) {
        log.info("Request to delete yogi meditation class with ID: {}", id);
        var deleted = yogiMeditationClassService.deleteYogiMeditationClass(id);
        if (deleted) {
            log.info("Yogi Meditation class with ID {} deleted successfully.", id);
        } else {
            log.warn("Yogi Meditation class with ID {} not found for deletion.", id);
        }
        return ResponseEntity.ok(deleted);
    }

    @Operation(summary = "Retrieve Yogi Join Date By Class", description = "Retrieve the join date of a yogi for a specific class.")
    @ApiResponse(responseCode = "200", description = "Yogi's join date retrieved",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = LocalDate.class)))
    @GetMapping("/{yogiId}/class/{classId}/join-date")
    public ResponseEntity<LocalDate> getJoinDate(@PathVariable Long yogiId, @PathVariable Long classId) {
        log.info("Request to retrieve join date for Yogi ID: {} in Class ID: {}", yogiId, classId);
        LocalDate joinDate = yogiMeditationClassService.getJoinDate(yogiId, classId);
        log.info("Yogi with ID {} joined class with ID {} on: {}", yogiId, classId, joinDate);
        return ResponseEntity.ok(joinDate);
    }

    @Operation(summary = "Check if Yogi is Blacklisted for Class", description = "Check if a yogi is blacklisted for a specific class.")
    @ApiResponse(responseCode = "200", description = "Yogi's blacklist status retrieved",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = Boolean.class)))
    @GetMapping("/{yogiId}/class/{classId}/blacklisted")
    public ResponseEntity<Boolean> isYogiBlacklistedForClass(@PathVariable Long yogiId, @PathVariable Long classId) {
        log.info("Request to check if Yogi ID: {} is blacklisted for Class ID: {}", yogiId, classId);
        boolean isBlacklisted = yogiMeditationClassService.isYogiBlacklistedForClass(yogiId, classId);
        log.info("Yogi with ID {} blacklisted status for class with ID {}: {}", yogiId, classId, isBlacklisted);
        return ResponseEntity.ok(isBlacklisted);
    }
}
