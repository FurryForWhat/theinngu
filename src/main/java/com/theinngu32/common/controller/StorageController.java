package com.theinngu32.common.controller;

import com.theinngu32.common.dto.FileUploadRequestDto;
import com.theinngu32.common.entity.StorageBlob;
import com.theinngu32.common.service.storage.StorageService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.core.io.Resource;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

import static com.theinngu32.common.utils.FileUtils.determineContentType;
import static com.theinngu32.common.utils.FileUtils.getFileExtension;
import static com.theinngu32.common.utils.constants.ApiEndpoints.STORAGE_BASE_URL;

@Tag(name = "File Storage Operations", description = "Operations for managing file storage")
@RestController
@RequestMapping(STORAGE_BASE_URL)
@RequiredArgsConstructor
public class StorageController {

    private final StorageService storageService;

    @Operation(summary = "Upload and Save File", description = "Upload a file and save the metadata associated with it.")
    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Void> uploadFile(
            @RequestPart @Parameter(description = "File to upload", required = true) MultipartFile file,
            @RequestParam("attachmentName") @Parameter(description = "Attachment name") String attachmentName,
            @RequestParam("recordId") @Parameter(description = "Record ID") Long recordId
    ) throws IOException {
        FileUploadRequestDto dto = new FileUploadRequestDto(
                file.getBytes(),
                file.getOriginalFilename(),
                file.getSize(),
                file.getContentType(),
                getFileExtension(file.getOriginalFilename()));
        storageService.uploadAndSave(dto, attachmentName, StorageController.class.getSimpleName(), recordId);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }


    @Operation(summary = "Retrieve File", description = "Retrieve a file based on the file key (filename).")
    @GetMapping("/retrieve/{key}")
    public ResponseEntity<Resource> retrieveFile(
            @PathVariable("key") @Parameter(description = "File key (filename)") String key) {
        return storageService.retrieveFile(key).map(resource -> {
            String contentType = determineContentType(resource);
            return ResponseEntity.ok()
                    .contentType(MediaType.parseMediaType(contentType))
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"%s\""
                            .formatted(storageService.getFilenameByKey(key)))
                    .body(resource);
        }).orElse(ResponseEntity.notFound().build());
    }


    @Operation(summary = "Delete File", description = "Delete a file based on the file key (filename).")
    @DeleteMapping("/delete/{key}")
    public ResponseEntity<String> deleteFile(
            @PathVariable("key") @Parameter(description = "File key (filename)") String key) throws ExecutionException, InterruptedException {

        var deleted =  storageService.deleteFile(key).get();
        if (deleted) {
            return ResponseEntity.ok("File deleted successfully.");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("File not found.");
        }
    }
}
