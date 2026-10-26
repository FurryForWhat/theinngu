package com.theinngu32.common.controller;

import com.theinngu32.common.annotation.APIResource;
import com.theinngu32.common.dto.submissionary_group.SubMissionaryGroupCreateDTO;
import com.theinngu32.common.dto.submissionary_group.SubMissionaryGroupDTO;
import com.theinngu32.common.dto.submissionary_group.SubMissionaryGroupUpdateDTO;
import com.theinngu32.common.service.SubMissionaryGroupService;
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


@Tag(name = "Sub Missionary Group Management", description = "Sub Missionary Group Management APIs")
@APIResource(apiPath = "/sub-missionary-groups")
@RequiredArgsConstructor
public class SubMissionaryGroupController {

    private static final Logger logger = LoggerFactory.getLogger(SubMissionaryGroupController.class);
    private final SubMissionaryGroupService subMissionaryGroupService;

    @Operation(summary = "Create SubMissionary Group", description = "Create SubMissionary Group APIs")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201",
                    description = "SubMissionary group successfully created",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = SubMissionaryGroupDTO.class)
                    )
            )
    })
    @PostMapping
    public ResponseEntity<SubMissionaryGroupDTO> createSubMissionaryGroup(@RequestBody SubMissionaryGroupCreateDTO subMissionaryGroupCreateDTO) {
        try {
            logger.info("Create SubMissionaryGroup request : {}", JsonUtil.toPrettyJson(subMissionaryGroupCreateDTO));
            var subMissionaryGroup = subMissionaryGroupService.createSubMissionaryGroup(subMissionaryGroupCreateDTO);
            logger.info("Create SubMissionaryGroup response : {}", JsonUtil.toPrettyJson(subMissionaryGroup));
            return ResponseEntity.status(HttpStatus.CREATED).body(subMissionaryGroup);
        } catch (Exception e) {
            logger.error("Error Occur in create SubMissionaryGroup : {}", e.getMessage());
            throw e;
        }
    }

    @Operation(summary = "Retrieve All SubMissionary Groups", description = "Retrieve all SubMissionary Groups APIs")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Retrieve all sub-missionary groups",
                    content = @Content(
                            mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = SubMissionaryGroupDTO.class))
                    )
            )
    })
    @GetMapping
    public ResponseEntity<?> getAllSubMissionaryGroup() {
        try {
            logger.info("Retrieve All SubMissionary Groups");
            var subMissionaryGroups = subMissionaryGroupService.getAllSubMissionaryGroup();
            logger.info("Retrieve All SubMissionary Groups response: {}", JsonUtil.toPrettyJson(subMissionaryGroups));
            return ResponseEntity.ok(subMissionaryGroups);
        } catch (Exception e) {
            logger.error("Error Occur in retrieve all sub-missionary groups : {}", e.getMessage());
            throw e;
        }
    }

    @Operation(summary = "Delete SubMissionary Group", description = "Delete SubMissionary Group APIs")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "SubMissionary group successfully deleted",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = Boolean.class)
                    )
            )
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteSubMissionaryGroup(@PathVariable Long id){
        try {
            logger.info("Delete sub-missionary group request with id: {}", id);
            var deleteStatus = subMissionaryGroupService.deleteSubMissionaryGroupById(id);
            logger.info("Delete sub-missionary group response : {}", JsonUtil.toPrettyJson(deleteStatus));
            return ResponseEntity.ok(deleteStatus);
        } catch (Exception e) {
            logger.error("Error Occur in delete sub-missionary group : {}", e.getMessage());
            throw e;
        }
    }

    @Operation(summary = "Update SubMissionary Group", description = "Update SubMissionary Group APIs")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Sub-missionary group successfully updated",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = SubMissionaryGroupDTO.class)
                    )
            )
    })
    @PutMapping("/{id}")
    public ResponseEntity<?> updateSubMissionaryGroup(@PathVariable Long id, @RequestBody SubMissionaryGroupUpdateDTO subMissionaryGroupUpdateDTO) {
        try {
            logger.info("Update sub-missionary group request with id: {} request : {}", id, JsonUtil.toPrettyJson(subMissionaryGroupUpdateDTO));
            var subMissionaryGroup = subMissionaryGroupService.updateSubMissionaryGroup(id, subMissionaryGroupUpdateDTO);
            logger.info("Update sub-missionary group response : {}", JsonUtil.toPrettyJson(subMissionaryGroup));
            return ResponseEntity.ok(subMissionaryGroup);
        } catch (Exception e) {
            logger.error("Error Occur in update sub-missionary group : {}", e.getMessage());
            throw e;
        }
    }


    @Operation(summary = "Find SubMissionaryGroup By Id", description = "Find SubMissionaryGroup By Id APIs")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Find sub-missionary by id",
                    content = @Content(
                            mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = SubMissionaryGroupDTO.class))
                    )
            )
    })
    @GetMapping("/{id}")
    public ResponseEntity<?> findSubMissionaryGroupById(@PathVariable Long id){
        try {
            logger.info("Retrieve sub-missionary group by id: {} request", id);
            var subMissionaryGroups = subMissionaryGroupService.findSubMissionaryGroupById(id);
            logger.info("Retrieve sub-missionary group by id: {} response: {}", id, JsonUtil.toPrettyJson(subMissionaryGroups));
            return ResponseEntity.ok(subMissionaryGroups);
        } catch (Exception e) {
            logger.error("Error Occur in retrieve sub-missionary group by id : {}", e.getMessage());
            throw e;
        }
    }

    @Operation(summary = "Find SubMissionaryGroup By MissionaryGroupId", description = "Find SubMissionaryGroup By MissionaryGroupId APIs")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Find sub missionary groups by missionary group id",
                    content = @Content(
                            mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = SubMissionaryGroupDTO.class))
                    )
            )
    })
    @GetMapping("/missionary-groups/{id}")
    public ResponseEntity<?> findSubMissionaryGroupByMissionaryGroupId(@PathVariable Long id){
        try {
            logger.info("Retrieve sub-missionary group by missionary group id: {} request", id);
            var subMissionaryGroups = subMissionaryGroupService.findSubMissionaryGroupByMissionaryGroupId(id);
            logger.info("Retrieve sub-missionary group by missionary group id: {} response: {}", id, JsonUtil.toPrettyJson(subMissionaryGroups));
            return ResponseEntity.ok(subMissionaryGroups);
        } catch (Exception e) {
            logger.error("Error Occur in retrieve sub-missionary group by missionary group id : {}", e.getMessage());
            throw e;
        }
    }

}
