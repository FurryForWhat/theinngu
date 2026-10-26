package com.theinngu32.common.controller;

import com.theinngu32.common.annotation.APIResource;
import com.theinngu32.common.dto.SearchRequest;
import com.theinngu32.common.dto.missionary_group.MissionaryGroupDTO;
import com.theinngu32.common.dto.missionary_group.MissionaryGroupCreateDTO;
import com.theinngu32.common.dto.missionary_group.MissionaryGroupUpdateDTO;
import com.theinngu32.common.service.MissionaryGroupService;
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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Missionary Group", description = "Missionary Group APIs")
@APIResource(apiPath = "/missionary-groups")
@RequiredArgsConstructor
public class MissionaryGroupController {

    private final MissionaryGroupService missionaryService;
    private static final Logger logger = LoggerFactory.getLogger(MissionaryGroupController.class);

    @Operation(summary = "Retrieve all missionary group", description = "Retrieve all missionary group APIs")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Retrieve all missionary group",
                    content = @Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = MissionaryGroupDTO.class))))
    })
    @GetMapping()
    public ResponseEntity<?> getAllMissionaryGroup() {
        try {
            logger.info("Retrieve all missionary group request");
            var missionaryGroups = missionaryService.getAll();
            logger.info("Retrieve all missionary group response : {}", JsonUtil.toPrettyJson(missionaryGroups));
            return ResponseEntity.ok(missionaryGroups);
        } catch (Exception e) {
            logger.error("Error occur in retrieve all missionary group : {}", e.getMessage());
            throw e;
        }
    }

    @Operation(summary = "Retrieve missionary group by id", description = "Retrieve missionary group APIs")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Retrieve missionary group by Id",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = MissionaryGroupDTO.class))
            )
    })
    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable Long id) {
        try {
            logger.info("Retrieve missionary group by id");
            var missionaryGroup = missionaryService.getById(id);
            logger.info("Retrieve missionary group response : {}", JsonUtil.toPrettyJson(missionaryGroup));
            return ResponseEntity.ok(missionaryGroup);
        } catch (Exception e) {
            logger.error("Error occur in retrieve missionary group : {}", e.getMessage());
            throw e;
        }
    }

    @Operation(summary = "Create missionary group", description = "Create missionary group APIs")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Created missionary group",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = MissionaryGroupDTO.class)
                    )
            )
    })
    @PostMapping()
    public ResponseEntity<?> create(@RequestBody MissionaryGroupCreateDTO missionaryGroupCreateDTO) {
        try {
            logger.info("Create missionary group");
            var missionaryGroup = missionaryService.create(missionaryGroupCreateDTO);
            logger.info("Create missionary group response : {}", JsonUtil.toPrettyJson(missionaryGroup));
            return ResponseEntity.ok(missionaryGroup);
        } catch (Exception e) {
            logger.info("Error occur in creating missionary group : {}", e.getMessage());
            throw e;
        }
    }

    @Operation(summary = "Update missionary group", description = "Update missionary group APIs")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Updated missionary group",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = MissionaryGroupDTO.class)
                    )
            )
    })
    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody MissionaryGroupUpdateDTO missionaryGroupUpdateDTO) {
        try {
            logger.info("User update with id : {} request : {}", id, JsonUtil.toPrettyJson(missionaryGroupUpdateDTO));
            var updatedMissionaryGroup = missionaryService.update(id, missionaryGroupUpdateDTO);
            logger.info("User update response : {}", JsonUtil.toPrettyJson(updatedMissionaryGroup));
            return ResponseEntity.ok(updatedMissionaryGroup);
        } catch (Exception e) {
            logger.info("Error occur in updating missionary group : {}", e.getMessage());
            throw e;
        }
    }

    @Operation(summary = "Delete missionary group", description = "Delete missionary group APIs")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Deleted missionary group",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Boolean.class)
                    )
            )
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        try {
            logger.info("Missionary delete with id : {} ", id);
            var deleteStatus = missionaryService.delete(id);
            logger.info("Missionary delete response : {}", JsonUtil.toPrettyJson(deleteStatus));
            return ResponseEntity.ok(deleteStatus);
        } catch (Exception e) {
            logger.error("Error occur in delete missionary : {}", e.getMessage());
            throw e;
        }
    }

    @Operation(summary = "Search Missionary Group By Filter", description = "Search Missionary Group By Filter APIs")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Search missionary group",
                    content = @Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = MissionaryGroupDTO.class))
                    )
            )
    })
    @PostMapping("/search")
    public ResponseEntity<Page<MissionaryGroupDTO>> findMissionaryGroupByFilter(
            @RequestBody SearchRequest request,
            @RequestParam int page,
            @RequestParam int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<MissionaryGroupDTO> missionaryGroupDTOS = missionaryService.findMissionaryGroupByFilter(request, pageable);
        return ResponseEntity.ok(missionaryGroupDTOS);
    }
}
