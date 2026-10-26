package com.theinngu32.common.controller;

import com.theinngu32.common.annotation.APIResource;
import com.theinngu32.common.dto.meditation_class.MeditationClassCreateDTO;
import com.theinngu32.common.dto.meditation_class.MeditationClassDTO;
import com.theinngu32.common.dto.SearchRequest;
import com.theinngu32.common.dto.meditation_class.MeditationClassUpdateDTO;
import com.theinngu32.common.service.MeditationClassService;
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
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@Tag(name = "Meditation Class Management", description = "Meditation Class Management APIs")
@APIResource(apiPath = "/meditation-classes")
@RequiredArgsConstructor
public class MeditationClassController {

    private final MeditationClassService meditationClassService;
    private static final Logger logger = LoggerFactory.getLogger(MeditationClassController.class);

    @Operation(summary = "Saves a meditation class", description = "This method creates a new meditation class in the database.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Returns the newly created meditation class in Json format.",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = MeditationClassDTO.class)))
    })
    @PostMapping
    public ResponseEntity<MeditationClassDTO> handleSaveMeditationClassRequest(@RequestBody MeditationClassCreateDTO meditationClassCreateDTO){
        try {
            logger.info("Request to save a new meditation class {}", JsonUtil.toPrettyJson(meditationClassCreateDTO));
            var mediationClass = meditationClassService.saveNewMeditationClass(meditationClassCreateDTO);
            logger.info("Saved meditation class {}", JsonUtil.toPrettyJson(mediationClass));
            return ResponseEntity.ok(mediationClass);
        } catch (Exception e) {
            logger.error("Error saving meditation class {}", e.getMessage());
            throw e;
        }
    }

    @Operation(summary = "Finds a meditation class", description = "This method searches the database for new meditation classes that match the search criteria.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Returns a list of matched meditation class in Json format.",
                    content = @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema( implementation = MeditationClassDTO.class))))
    })
    @PostMapping("/search")
    public ResponseEntity<Page<MeditationClassDTO>> handleSearchRequest(@RequestBody SearchRequest searchRequest,
                                                                        @RequestParam int currentPage,
                                                                        @RequestParam int pageSize){
        try {
            logger.info("Request to find meditation classes by filters {}", JsonUtil.toPrettyJson(searchRequest));
            var resultPages = meditationClassService.findMeditationClassesByFilters(searchRequest, PageRequest.of(currentPage, pageSize));
            logger.info("Found {} meditation classes", resultPages.getTotalElements());
            return ResponseEntity.ok(resultPages);
        } catch (Exception e) {
            logger.error("Error finding meditation classes by filters {}", e.getMessage());
            throw e;
        }
    }

    @Operation(summary = "Gets all the meditation class", description = "This method retrieves every meditation class from the database.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Returns a list of meditation classes in Json format.",
                    content = @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema( implementation = MeditationClassDTO.class))))
    })
    @GetMapping
    public ResponseEntity<List<MeditationClassDTO>> handleGetAllMeditationClassRequest(){
        try {
            logger.info("Request to get all meditation classes");
            var meditationClasses = meditationClassService.getAllMeditationClasses();
            logger.info("Found meditation classes {}", meditationClasses);
            return ResponseEntity.ok(meditationClasses);
        } catch (Exception e) {
            logger.error("Error getting all meditation classes {}", e.getMessage());
            throw e;
        }
    }


    @Operation(summary = "Finds a meditation class by ID", description = "This method retrieves a meditation class by ID from the database.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Returns a newly created meditation class in Json format.",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = MeditationClassDTO.class)))
    })
    @GetMapping("/{id}")
    public ResponseEntity<MeditationClassDTO> handleGetMeditationClassByIdRequest(@PathVariable Long id){
        try {
            logger.info("Request to find meditation class by ID {}", id);
            var mediationClass = meditationClassService.findMeditationClassById(id);
            logger.info("Found meditation class {}", JsonUtil.toPrettyJson(mediationClass));
            return ResponseEntity.ok(mediationClass);
        } catch (Exception e) {
            logger.error("Error finding meditation class by ID {}", e.getMessage());
            throw e;
        }
    }


    @Operation(summary = "Updates a meditation class", description = "This method updates the information of an existing meditation class in the database.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Returns the updated meditation class in Json format.",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = MeditationClassDTO.class)))
    })
    @PutMapping("/{id}")
    public ResponseEntity<MeditationClassDTO> handleUpdateMeditationClassRequest(@PathVariable Long id, @RequestBody MeditationClassUpdateDTO meditationClassUpdateDTO) {
        try {
            logger.info("Update meditation class with ID {}", id);
            var updatedMediationClass = meditationClassService.updateExistingMediationClass(id, meditationClassUpdateDTO);
            logger.info("Updated meditation class {}", JsonUtil.toPrettyJson(updatedMediationClass));
            return ResponseEntity.ok(updatedMediationClass);
        } catch (Exception e) {
            logger.error("Error updating meditation class {}", e.getMessage());
            throw e;
        }
    }

    @Operation(summary = "Deletes a meditation class", description = "This method deletes an existing meditation class by ID from the database.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Returns a string to indicate that the delete process is successful.",
                    content = @Content(mediaType = "text/html"))
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<String> handleDeleteMeditationClassRequest(@PathVariable Long id){
        try {
            logger.info("Request to delete meditation class with ID {}", id);
            meditationClassService.deleteMeditationClass(id);
            logger.info("Deleted meditation class with ID {}", id);
            return ResponseEntity.ok("Delete works");
        } catch (Exception e) {
            logger.error("Error deleting meditation class with ID {}", e.getMessage());
            throw e;
        }
    }

}
