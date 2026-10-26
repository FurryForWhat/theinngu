package com.theinngu32.common.controller;

import com.theinngu32.common.annotation.APIResource;
import com.theinngu32.common.dto.SearchRequest;
import com.theinngu32.common.dto.blacklist_yogis.BlacklistYogiCreateDto;
import com.theinngu32.common.dto.blacklist_yogis.BlacklistYogiResponseDTO;
import com.theinngu32.common.dto.blacklist_yogis.BlacklistYogiUpdateDto;
import com.theinngu32.common.service.BlacklistYogiService;
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

@Tag(name = "Blacklist Yogi Management", description = "Blacklist Yogi Management APIs")
@APIResource(apiPath = "/blacklist-yogis")
@RequiredArgsConstructor
public class BlacklistYogiController {

    private static final Logger logger = LoggerFactory.getLogger(BlacklistYogiController.class);
    private final BlacklistYogiService blacklistYogiService;

    @Operation(summary = "Retrieve All Blacklist Yogis", description = "Retrieve all blacklist yogis APIs")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Retrieve all blacklist yogis",
                    content = @Content(
                            mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = BlacklistYogiResponseDTO.class))
                    )
            )
    })
    @GetMapping()
    public ResponseEntity<?> getAllBlacklistYogis() {
        try {
            logger.info("Retrieve all blacklist yogis request");
            var blacklistYogis = blacklistYogiService.getAllBlacklistYogis();
            logger.info("Retrieve all blacklist yogis response: {}", JsonUtil.toPrettyJson(blacklistYogis));
            return ResponseEntity.ok(blacklistYogis);
        } catch (Exception e) {
            logger.error("Error occur in retrieve all blacklist yogis: {}", e.getMessage());
            throw e;
        }
    }

    @Operation(summary = "Retrieve Blacklist Yogi By Id", description = "Retrieve blacklist yogi by id APIs")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Retrieve blacklist yogi by id",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = BlacklistYogiResponseDTO.class)
                    )
            )
    })
    @GetMapping("/{id}")
    public ResponseEntity<?> getBlacklistYogiById(@PathVariable Long id) {
        try {
            logger.info("Retrieve blacklist yogi by id: {} request", id);
            var blacklistYogi = blacklistYogiService.findBlacklistYogiById(id);
            logger.info("Retrieve blacklist yogi by id response: {}", JsonUtil.toPrettyJson(blacklistYogi));
            return ResponseEntity.ok(blacklistYogi);
        } catch (Exception e) {
            logger.error("Error occur in retrieve blacklist yogi by id: {}", e.getMessage());
            throw e;
        }
    }

    @Operation(summary = "Retrieve Blacklist Yogi By Yogi Id", description = "Retrieve blacklist yogi by yogi id APIs")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Retrieve blacklist yogi by yogi id",
                    content = @Content(
                            mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = BlacklistYogiResponseDTO.class))
                    )
            )
    })
    @GetMapping("/yogi/{yogiId}")
    public ResponseEntity<?> getBlacklistYogiByYogiId(@PathVariable Long yogiId) {
        try {
            logger.info("Retrieve blacklist yogi by yogi id: {} request", yogiId);
            var blacklistYogiList = blacklistYogiService.findBlacklistYogiByYogiId(yogiId);
            logger.info("Retrieve blacklist yogi by yogi id response: {}", JsonUtil.toPrettyJson(blacklistYogiList));
            return ResponseEntity.ok(blacklistYogiList);
        } catch (Exception e) {
            logger.error("Error occur in retrieve blacklist yogi by yogi id: {}", e.getMessage());
            throw e;
        }
    }

    @Operation(
            summary = "Retrieve Blacklist Yogi By Created By Id",
            description = "Retrieve blacklist yogi by created by id APIs"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Retrieve blacklist yogi by created by id",
                    content = @Content(
                            mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = BlacklistYogiResponseDTO.class))
                    )
            )
    })
    @GetMapping("/created-by/{createdById}")
    public ResponseEntity<?> getBlacklistYogisByCreatedById(@PathVariable Long createdById) {
        try {
            logger.info("Retrieve blacklist yogi by created by id: {} request", createdById);
            var blacklistYogiList = blacklistYogiService.findBlacklistYogiByCreatedById(createdById);
            logger.info("Retrieve blacklist yogi by created by id response: {}", JsonUtil.toPrettyJson(blacklistYogiList));
            return ResponseEntity.ok(blacklistYogiList);
        } catch (Exception e) {
            logger.error("Error occur in retrieve blacklist yogi by created by id: {}", e.getMessage());
            throw e;
        }
    }

    @Operation(
            summary = "Retrieve Blacklist Yogi By Yogi Id And Created By Id",
            description = "Retrieve blacklist yogi by yogi id and created by id APIs"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Retrieve blacklist yogi by yogi id and created by id",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = BlacklistYogiResponseDTO.class)
                    )
            )
    })
    @GetMapping("/yogi/{yogiId}/created-by/{createdById}")
    public ResponseEntity<?> getBlacklistYogiByYogiIdAndCreatedById(@PathVariable Long yogiId, @PathVariable Long createdById) {
        try {
            logger.info("Retrieve blacklist yogi by yogi id and created by id Request");
            var blacklistYogi = blacklistYogiService.findBlacklistYogiByYogiIdAndCreatedById(yogiId, createdById);
            logger.info("Retrieve blacklist yogi by yogi id and created by id response: {}", JsonUtil.toPrettyJson(blacklistYogi));
            return ResponseEntity.ok(blacklistYogi);
        } catch (Exception e) {
            logger.error("Error occur in retrieve blacklist yogi by yogi id and created by id: {}", e.getMessage());
            throw e;
        }
    }

    @Operation(summary = "Create Blacklist Yogi", description = "Create blacklist yogi APIs")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201",
                    description = "Blacklist yogi created",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = BlacklistYogiResponseDTO.class)
                    )
            )
    })
    @PostMapping()
    public ResponseEntity<?> createBlacklistYogi(@RequestBody BlacklistYogiCreateDto blacklistYogiCreateDto) {
        try {
            logger.info("Create blacklist yogi request: {}", JsonUtil.toPrettyJson(blacklistYogiCreateDto));
            var blacklistYogiDTO = blacklistYogiService.createBlacklistYogi(blacklistYogiCreateDto);
            logger.info("Create blacklist yogi response: {}", JsonUtil.toPrettyJson(blacklistYogiDTO));
            return ResponseEntity.status(HttpStatus.CREATED).body(blacklistYogiDTO);
        } catch (Exception e) {
            logger.error("Error occur in create blacklist yogi: {}", e.getMessage());
            throw e;
        }
    }

    @Operation(summary = "Update Blacklist Yogi", description = "Update blacklist yogi APIs")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Blacklist yogi updated",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = BlacklistYogiResponseDTO.class)
                    )
            )
    })
    @PutMapping("/{id}")
    public ResponseEntity<?> updateBlacklistYogi(@PathVariable Long id, @RequestBody BlacklistYogiUpdateDto blacklistYogiUpdateDto) {
        try {
            logger.info("Blacklist yogi update with id: {} request : {}", id, JsonUtil.toPrettyJson(blacklistYogiUpdateDto));
            var updatedBlacklistYogi = blacklistYogiService.updateBlacklistYogi(id, blacklistYogiUpdateDto);
            logger.info("Blacklist yogi update response: {}", JsonUtil.toPrettyJson(updatedBlacklistYogi));
            return ResponseEntity.ok(updatedBlacklistYogi);
        } catch (Exception e) {
            logger.error("Error occur in update blacklist yogi : {}", e.getMessage());
            throw e;
        }
    }

    @Operation(summary = "Delete Blacklist Yogi By Id", description = "Delete blacklist yogi by id APIs")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Deleted blacklist yogi by id",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = Boolean.class)
                    )
            )
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteBlacklistYogiById(@PathVariable Long id) {
        try {
            logger.info("Delete blacklist yogi by id request: {}", id);
            var deleteStatus = blacklistYogiService.deleteBlacklistYogiById(id);
            logger.info("Delete blacklist yogi response: {}", JsonUtil.toPrettyJson(deleteStatus));
            return ResponseEntity.ok(deleteStatus);
        } catch (Exception e) {
            logger.error("Error occur in delete blacklist yogi : {}", e.getMessage());
            throw e;
        }
    }

    @Operation(
            summary = "Delete Blacklist Yogi by Yogi Id And Created By Id",
            description = "Delete blacklist yogi by yogi id and created by id APIs"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Deleted blacklist yogi by yogi id and created by id",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = Boolean.class)
                    )
            )
    })
    @DeleteMapping("/delete")
    public ResponseEntity<?> deleteBlacklistYogiByYogiIdAndCreatedById(@RequestParam Long yogi_id, @RequestParam Long created_by_id) {
        try {
            logger.info("Delete blacklist yogi by Yogi Id : {} And Created By Id: {} request", yogi_id, created_by_id);
            var deleteStatus = blacklistYogiService.deleteBlacklistYogiByYogiIdAndCreatedById(yogi_id, created_by_id);
            logger.info("Delete blacklist yogi by yogi id and created by id response: {}", JsonUtil.toPrettyJson(deleteStatus));
            return ResponseEntity.ok(deleteStatus);
        } catch (Exception e) {
            logger.error("Error occur in delete blacklist yogi by yogi id and created by id : {}", e.getMessage());
            throw e;
        }
    }

    @Operation(summary = "Find Blacklist Yogi By Filter", description = "Find blacklist yogi by filter APIs")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Find blacklist yogi by filter",
                    content = @Content(
                            mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = BlacklistYogiResponseDTO.class))
                    )
            )
    })
    @PostMapping("/search")
    public ResponseEntity<?> findBlacklistYogisByFilter(
            @RequestBody SearchRequest request,
            @RequestParam int page,
            @RequestParam int size) {
        Pageable pageable = PageRequest.of(page, size);
        try {
            logger.info("Find blacklist yogi by filter request: {}", request);
            var blacklistYogis = blacklistYogiService.findBlacklistYogiByFilter(request, pageable);
            logger.info("Find blacklist yogi by filter response: {}", JsonUtil.toPrettyJson(blacklistYogis));
            return ResponseEntity.ok(blacklistYogis);
        } catch (Exception e) {
            logger.error("Error occur in find blacklist yogi by filter : {}", e.getMessage());
            throw e;
        }
    }

}
