package com.theinngu32.common.controller;

import com.theinngu32.common.annotation.APIResource;
import com.theinngu32.common.dto.SearchRequest;
import com.theinngu32.common.dto.blacklist_old_yogi.BlacklistOldYogiCreateDTO;
import com.theinngu32.common.dto.blacklist_old_yogi.BlacklistOldYogiResponseDTO;
import com.theinngu32.common.dto.blacklist_old_yogi.BlacklistOldYogiUpdateDTO;
import com.theinngu32.common.service.BlacklistOldYogiService;
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

@Tag(name = "Blacklist Old Yogi Management", description = "Blacklist Old Yogi Management APIs")
@RequiredArgsConstructor
@APIResource(apiPath = "/blacklist-old-yogis")
public class BlacklistOldYogiController {

    private static final Logger logger = LoggerFactory.getLogger(BlacklistOldYogiController.class);
    private final BlacklistOldYogiService blacklistOldYogiService;

    @Operation(summary = "Retrieve All Blacklist Old Yogis", description = "Retrieve all blacklist old yogis APIs")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Retrieve all blacklist old yogis",
                    content = @Content(
                            mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = BlacklistOldYogiResponseDTO.class))
                    )
            )
    })
    @GetMapping()
    public ResponseEntity<?> getAllBlacklistOldYogis() {
        try {
            logger.info("Retrieve all blacklist old yogis request");
            var blacklistOldYogis = blacklistOldYogiService.getAllBlacklistOldYogis();
            logger.info("Retrieve all blacklist old yogis response: {}", JsonUtil.toPrettyJson(blacklistOldYogis));
            return ResponseEntity.ok(blacklistOldYogis);
        } catch (Exception e) {
            logger.error("Error occur in retrieve all blacklist old yogis: {}", e.getMessage());
            throw e;
        }
    }

    @Operation(summary = "Retrieve Blacklist Old Yogi by Id", description = "Retrieve blacklist old yogi by id APIs")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Retrieve blacklist old yogi by id",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = BlacklistOldYogiResponseDTO.class)
                    )
            )
    })
    @GetMapping("/{id}")
    public ResponseEntity<?> getBlacklistOldYogiById(@PathVariable Long id) {
        try {
            logger.info("Retrieve blacklist old yogi by id request");
            var blacklistOldYogi = blacklistOldYogiService.findBlacklistOldYogiById(id);
            logger.info("Retrieve blacklist old yogi by id response: {}", JsonUtil.toPrettyJson(blacklistOldYogi));
            return ResponseEntity.ok(blacklistOldYogi);
        } catch (Exception e) {
            logger.error("Error occur in retrieve blacklist old yogi by id: {}", e.getMessage());
            throw e;
        }
    }

    @Operation(
            summary = "Retrieve Blacklist Old Yogi By Old Yogi Id",
            description = "Retrieve blacklist old yogi by old yogi id APIs"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Retrieve blacklist old yogi by old yogi id",
                    content = @Content(
                            mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = BlacklistOldYogiResponseDTO.class))
                    )
            )
    })
    @GetMapping("/old-yogi/{oldYogiId}")
    public ResponseEntity<?> getBlacklistOldYogiByOldYogiId(@PathVariable Long oldYogiId) {
        try {
            logger.info("Retrieve blacklist old yogi by old yogi id: {} request", oldYogiId);
            var blacklistOldYogiList = blacklistOldYogiService.findBlacklistOldYogiByOldYogiId(oldYogiId);
            logger.info("Retrieve blacklist old yogi by old yogi id response: {}", JsonUtil.toPrettyJson(blacklistOldYogiList));
            return ResponseEntity.ok(blacklistOldYogiList);
        } catch (Exception e) {
            logger.error("Error occur in retrieve blacklist old yogi by old yogi id: {}", e.getMessage());
            throw e;
        }
    }

    @Operation(
            summary = "Retrieve Blacklist Old Yogi By Created By Id",
            description = "Retrieve blacklist old yogi by created by id APIs"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Retrieve blacklist old yogi by created by id",
                    content = @Content(
                            mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = BlacklistOldYogiResponseDTO.class))
                    )
            )
    })
    @GetMapping("/created-by/{createdById}")
    public ResponseEntity<?> getBlacklistOldYogisByCreatedById(@PathVariable Long createdById) {
        try {
            logger.info("Retrieve blacklist old yogi by created by id: {} request", createdById);
            var blacklistOldYogiList = blacklistOldYogiService.findBlacklistOldYogiByCreatedById(createdById);
            logger.info("Retrieve blacklist old yogi by created by id response: {}", JsonUtil.toPrettyJson(blacklistOldYogiList));
            return ResponseEntity.ok(blacklistOldYogiList);
        } catch (Exception e) {
            logger.error("Error occur in retrieve blacklist old yogi by created by id: {}", e.getMessage());
            throw e;
        }
    }

    @Operation(
            summary = "Retrieve Blacklist Old Yogi By Old Yogi Id And Created By Id",
            description = "Retrieve blacklist old yogi by old yogi id and created by id APIs"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Retrieve blacklist old yogi by old yogi id and created by id",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = BlacklistOldYogiResponseDTO.class)
                    )
            )
    })
    @GetMapping("/old-yogi/{oldYogiId}/created-by/{createdById}")
    public ResponseEntity<?> getBlacklistOldYogiByOldYogiIdAndCreatedById(
            @PathVariable Long oldYogiId, @PathVariable Long createdById
    ) {
        try {
            logger.info("Retrieve blacklist old yogi by old yogi id: {} and created by id {} request", oldYogiId, createdById);
            var blacklistOldYogi = blacklistOldYogiService.findBlacklistOldYogiByOldYogiIdAndCreatedById(oldYogiId, createdById);
            logger.info("Retrieve blacklist old yogi by old yogi id and created by id response: {}", JsonUtil.toPrettyJson(blacklistOldYogi));
            return ResponseEntity.ok(blacklistOldYogi);
        } catch (Exception e) {
            logger.error("Error occur in retrieve blacklist old yogi by old yogi id and created by id: {}", e.getMessage());
            throw e;
        }
    }

    @Operation(summary = "Create Blacklist Old Yogi", description = "Create blacklist old yogi APIs")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201",
                    description = "Blacklist old yogi created",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = BlacklistOldYogiResponseDTO.class)
                    )
            )
    })
    @PostMapping("")
    public ResponseEntity<?> createBlacklistOldYogi(
            @RequestBody BlacklistOldYogiCreateDTO blacklistOldYogiCreateDTO
    ) {
        try {
            logger.info("Create blacklist old yogi request: {}", JsonUtil.toPrettyJson(blacklistOldYogiCreateDTO));
            var blacklistOldYogiDTO = blacklistOldYogiService.createBlacklistOldYogi(blacklistOldYogiCreateDTO);
            logger.info("Create blacklist old yogi response: {}", JsonUtil.toPrettyJson(blacklistOldYogiDTO));
            return ResponseEntity.status(HttpStatus.CREATED).body(blacklistOldYogiDTO);
        } catch (Exception e) {
            logger.error("Error occur in retrieve blacklist old yogi: {}", e.getMessage());
            throw e;
        }
    }

    @Operation(summary = "Update Blacklist Old Yogi", description = "Update blacklist old yogi APIs")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Blacklist old yogi updated",
                    content = @Content(
                            schema = @Schema(implementation = BlacklistOldYogiResponseDTO.class)
                    )
            )
    })
    @PutMapping("/{id}")
    public ResponseEntity<?> updateBlacklistOldYogi(
            @PathVariable Long id, @RequestBody BlacklistOldYogiUpdateDTO blacklistOldYogiUpdateDTO
    ) {
        try {
            logger.info("Blacklist old yogi update with id: {} request: {}", id, JsonUtil.toPrettyJson(blacklistOldYogiUpdateDTO));
            var updatedBlacklistOldYogi = blacklistOldYogiService.updateBlacklistOldYogi(id, blacklistOldYogiUpdateDTO);
            logger.info("Blacklist old yogi update response: {}", JsonUtil.toPrettyJson(updatedBlacklistOldYogi));
            return ResponseEntity.ok(updatedBlacklistOldYogi);
        } catch (Exception e) {
            logger.error("Error occur in update blacklist old yogi: {}", e.getMessage());
            throw e;
        }
    }

    @Operation(summary = "Delete Blacklist Old Yogi By Id", description = "Delete blacklist old yogi by id APIs")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Deleted blacklist old yogi by id",
                    content = @Content(
                            schema = @Schema(implementation = Boolean.class)
                    )
            )
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteBlacklistOldYogiById(@PathVariable Long id) {
        try {
            logger.info("Delete blacklist old yogi by id request: {}", id);
            var deleteStatus = blacklistOldYogiService.deleteBlacklistOldYogiById(id);
            logger.info("Delete blacklist old yogi by id response: {}", JsonUtil.toPrettyJson(deleteStatus));
            return ResponseEntity.ok(deleteStatus);
        } catch (Exception e) {
            logger.error("Error occur in delete blacklist old yogi by id: {}", e.getMessage());
            throw e;
        }
    }

    @Operation(
            summary = "Delete Blacklist Old Yogi By Old Yogi Id And Created By Id",
            description = "Delete blacklist old yogi by old yogi id and created by id APIs"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Deleted blacklist old yogi by old yogi and created by id",
                    content = @Content(
                            schema = @Schema(implementation = Boolean.class)
                    )
            )
    })
    @DeleteMapping("/delete")
    public ResponseEntity<?> deleteBlacklistOldYogiByOldYogiIdAndCreatedById(
            @RequestParam Long old_yogi_id, @RequestParam Long created_by_id
    ) {
        try {
            logger.info("Delete blacklist old yogi by old yogi id: {} and created by id request: {}", old_yogi_id, created_by_id);
            var deleteStatus = blacklistOldYogiService.deleteBlacklistOldYogiByOldYogiIdAndCreatedById(old_yogi_id, created_by_id);
            logger.info("Delete blacklist old yogi by old yogi id and created by id response: {}", JsonUtil.toPrettyJson(deleteStatus));
            return ResponseEntity.ok(deleteStatus);
        } catch (Exception e) {
            logger.error("Error occur in delete blacklist old yogi by old yogi id and created by: {}", e.getMessage());
            throw e;
        }
    }

    @Operation(summary = "Find Blacklist Old Yogi By Filter", description = "Find blacklist old yogi by filter APIs")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Find blacklist old yogi by filter",
                    content = @Content(
                            mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = BlacklistOldYogiResponseDTO.class))
                    )
            )
    })
    @PostMapping("/search")
    public ResponseEntity<?> findBlacklistOldYogisByFilter(
            @RequestBody SearchRequest request,
            @RequestParam int page,
            @RequestParam int size) {
        Pageable pageable = PageRequest.of(page, size);
        try {
            logger.info("Find blacklist old yogi by filter request: {}", JsonUtil.toPrettyJson(request));
            var blacklistYogis = blacklistOldYogiService.findBlacklistOldYogisByFilter(request, pageable);
            logger.info("Find blacklist old yogi by filter response: {}", JsonUtil.toPrettyJson(blacklistYogis));
            return ResponseEntity.ok(blacklistYogis);
        } catch (Exception e) {
            logger.error("Error occur in find blacklist old yogi by filter: {}", e.getMessage());
            throw e;
        }
    }

}
