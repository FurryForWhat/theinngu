package com.theinngu32.common.utils;

import com.theinngu32.common.dto.FileUploadRequestDto;
import com.theinngu32.common.exception.CannotConvertFileException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import static com.theinngu32.common.utils.ImageUtils.isSupportedImage;

@Slf4j
public class FileUtils {

    private static final Map<String, Long> SIZE_UNITS = Map.of(
            "KB", 1024L,
            "MB", 1024L * 1024,
            "GB", 1024L * 1024 * 1024
    );

    /**
     * Convert human-readable storage size (e.g., "3MB", "512KB", "1GB") to bytes
     */
    public long parseStorageSize(String sizeStr) {
        Pattern pattern = Pattern.compile("(\\d+)(KB|MB|GB)", Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(sizeStr.trim());

        if (matcher.matches()) {
            long size = Long.parseLong(matcher.group(1));
            String unit = matcher.group(2).toUpperCase();
            return size * SIZE_UNITS.getOrDefault(unit, 1024L);
        }

        throw new IllegalArgumentException("Invalid storage size format: " + sizeStr);
    }

    /**
     * Return file extension (excluding ".")
     */
    public static String getFileExtension(String originalFilename) {
        if (originalFilename != null && originalFilename.contains(".")) {
            return originalFilename.substring(originalFilename.lastIndexOf(".") + 1);
        }
        return "";
    }

    /**
     * Return metadata based on file extension
     */
    public static String getMetadata(byte[] bytes, String fileExtension) {
        if (isSupportedImage(fileExtension)) {
            try {
                var dimension = ImageUtils.getImageDimensions(bytes);
                return "{\"identified\":true,\"width\":%d,\"height\":%d,\"analyzed\":true}"
                        .formatted(dimension[0], dimension[1]);
            } catch (IOException e) {
                log.warn("Error extracting image dimensions: {}", e.getMessage());
            }
        }
        return "{\"identified\":true,\"analyzed\":true}";
    }

    /**
     * Convert MultipartFile to File
     */

    public static File convertMultipartFileToFile(FileUploadRequestDto dto) throws IOException {
        File tempFile = createTempFile(dto.filename());

        try (FileOutputStream fos = new FileOutputStream(tempFile);
             ByteArrayInputStream bis = new ByteArrayInputStream(dto.bytes())) {

            byte[] buffer = new byte[8192];
            int bytesRead;

            while ((bytesRead = bis.read(buffer)) != -1) {
                fos.write(buffer, 0, bytesRead);
            }
        }

        return tempFile;
    }



    /**
     * Determines the content type based on the file type.
     */
    public static String determineContentType(Resource resource) {
        try {
            return Files.probeContentType(resource.getFile().toPath());
        } catch (IOException e) {
            return MediaType.APPLICATION_OCTET_STREAM_VALUE; // Default to binary stream
        }
    }

    /**
     * Creates a temporary file to store the compressed image.
     *
     * @param originalFilename The original filename.
     * @return A temporary File object.
     */
    public static File createTempFile(String originalFilename) throws IOException {
            String sanitizedFilename = originalFilename.replaceAll("[^a-zA-Z0-9.-]", "_");
            Path tempFile = Files.createTempFile("theinngu_", UUID.randomUUID() + "_" + System.nanoTime() + sanitizedFilename);
            return tempFile.toFile();
    }

        /**
     * Converts a MultipartFile to a File object safely.
     */
    public static File convertFileSafely(FileUploadRequestDto dto) {
        try {
            return convertMultipartFileToFile(dto);
        } catch (IOException e) {
            throw new CannotConvertFileException("Failed to convert file: " + dto.filename(), e);
        }
    }

    /**
     * Get InputStream from classpath resource.
     * @param filename The file path inside `resources/`
     * @return InputStream of the file
     */
    public static InputStream getResourceAsStream(String filename) {
        return FileUtils.class.getClassLoader().getResourceAsStream(filename);
    }

    /**
     * Get file content as String from classpath resource.
     * @param filename The file path inside `resources/`
     * @return File content as a String
     */
    public static String getResourceAsString(String filename) {
        InputStream inputStream = getResourceAsStream(filename);
        if (inputStream == null) {
            throw new RuntimeException("File not found: " + filename);
        }
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8))) {
            return reader.lines().collect(Collectors.joining("\n"));
        } catch (Exception e) {
            throw new RuntimeException("Error reading file: " + filename, e);
        }
    }
}
