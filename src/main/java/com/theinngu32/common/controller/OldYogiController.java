package com.theinngu32.common.controller;

import com.theinngu32.common.annotation.APIResource;
import com.theinngu32.common.dto.oldyogi.OldYogiCreateDTO;
import com.theinngu32.common.dto.oldyogi.OldYogiResponseDTO;
import com.theinngu32.common.dto.oldyogi.OldYogiUpdateDTO;
import com.theinngu32.common.service.OldYogiService;
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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Old Yogis Management", description = "Old Yogis Management APIs")
@RequiredArgsConstructor
@APIResource(apiPath = "old-yogis")
public class OldYogiController {

    private static final Logger logger = LoggerFactory.getLogger(OldYogiController.class);
    private final OldYogiService oldYogiService;

    @Operation(summary = "Retrieve All Old Yogis", description = "Retrieve all old yogis APIs")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Retrieve all old yogis",
                    content = @Content(
                            mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = OldYogiResponseDTO.class))
                    )
            )
    })
    @GetMapping
    public ResponseEntity<List<OldYogiResponseDTO>> getAllOldYogis(){
        logger.info("Retrieve All Old Yogis");
        var oldYogis = oldYogiService.getAllOldYogis();
        logger.info("Retrieve All Old Yogis response: {}", JsonUtil.toPrettyJson(oldYogis));
        return ResponseEntity.status(HttpStatus.OK).body(oldYogis);
    }

    @Operation(summary = "Create Old Yogi", description = "Create Old Yogi APIs")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201",
                    description = "Old Yogi created",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = OldYogiResponseDTO.class)
                    )
            )
    })
    @PostMapping
    public ResponseEntity<OldYogiResponseDTO> createNewOldYogis(@RequestBody OldYogiCreateDTO oldYogiCreateDTO){
        logger.info("Create old yogi request : {}", JsonUtil.toPrettyJson(oldYogiCreateDTO));
        var oldyogiDTO = oldYogiService.createOldYogi(oldYogiCreateDTO);
        logger.info("Create old yogi response : {}", JsonUtil.toPrettyJson(oldyogiDTO));
        return ResponseEntity.status(HttpStatus.CREATED).body(oldyogiDTO);
    }

    @Operation(summary = "Update Old Yogi", description = "Update old yogi APIs")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Old yogi updated",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = OldYogiResponseDTO.class)
                    )
            )
    })
    @PutMapping("/{id}")
    public ResponseEntity<OldYogiResponseDTO> updateOldYogi(@PathVariable Long id, @RequestBody OldYogiUpdateDTO oldYogiUpdateDTO){
        logger.info("old yogi update request with id: {} request : {}", id, JsonUtil.toPrettyJson(oldYogiUpdateDTO));
        var oldYogi = oldYogiService.updateOldYogi(id, oldYogiUpdateDTO);
        logger.info("old yogi update response : {}", JsonUtil.toPrettyJson(oldYogi));
        return ResponseEntity.ok(oldYogi);
    }

    @Operation(summary = "Delete Old Yogi", description = "Delete old yogi APIs")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Old yogi deleted",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = Boolean.class)
                    )
            )
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> deleteOldYogi(@PathVariable Long id){
        logger.info("Delete old yogi request with id: {}", id);
        var deleteStatus = oldYogiService.deleteYogi(id);
        logger.info("Delete nrc code response : {}", JsonUtil.toPrettyJson(deleteStatus));
        return ResponseEntity.ok(deleteStatus);
    }

}
