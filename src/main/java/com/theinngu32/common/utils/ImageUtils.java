package com.theinngu32.common.utils;

import com.theinngu32.common.dto.FileUploadRequestDto;
import com.theinngu32.common.exception.ImageCompressionException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.imageio.stream.ImageOutputStream;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.Iterator;

import static com.theinngu32.common.utils.FileUtils.*;
import static com.theinngu32.common.utils.constants.FileExtensions.*;
import static com.theinngu32.common.utils.constants.FileExtensions.GIF;

@RequiredArgsConstructor
@Slf4j
public class ImageUtils {
    private static final float QUALITY = 0.7f;

    /**
     * Retrieves the dimensions of the given file.
     */
    public static int[] getImageDimensions(byte[] bytes) throws IOException {
        if (bytes == null || bytes.length == 0) {
            throw new IOException("Empty or null byte array");
        }

        try (ByteArrayInputStream bis = new ByteArrayInputStream(bytes)) {
            BufferedImage image = ImageIO.read(bis);
            if (image != null) {
                return new int[]{image.getWidth(), image.getHeight()};
            } else {
                throw new IOException("Invalid or unsupported image file");
            }
        }
    }


    /**
     * Compresses an image file only if its size exceeds the specified limit.
     */
    public static File compressOnlyIfLargeImage(FileUploadRequestDto dto, long limit) throws IOException {
        if (!isSupportedImage(dto.extension()) || dto.fileSize() <= limit) {
            return convertFileSafely(dto);
        }

        BufferedImage image = readImageSafely(dto);
        File compressedFile = null;
        try {
            compressedFile = createTempFile(dto.extension());
        } catch (IOException e) {
            throw new IOException("Failed to create temp file for "+ dto.filename(), e);
        }
        return compressImageSafely(image, dto.extension(), compressedFile, dto.filename());
    }

    /**
     * Reads an image from a MultipartFile safely.
     */
    private static BufferedImage readImageSafely(FileUploadRequestDto dto) {
        try {
            try (ByteArrayInputStream bis = new ByteArrayInputStream(dto.bytes())) {
                BufferedImage image = ImageIO.read(bis);
                if (image == null) {
                    throw new ImageCompressionException("Invalid image file: " + dto.filename());
                }
                return image;
            }
        } catch (IOException e) {
            throw new ImageCompressionException("Error reading image file: " + dto.filename(), e);
        }
    }

    /**
     * Compresses an image and writes it to an output file.
     * @return The compressed image file.
     */
    private static File compressImageSafely(BufferedImage image, String extension, File outputFile, String originalFilename) {
        try (OutputStream os = new FileOutputStream(outputFile);
             ImageOutputStream ios = ImageIO.createImageOutputStream(os)) {

            Iterator<ImageWriter> writers = ImageIO.getImageWritersByFormatName(extension);
            if (!writers.hasNext()) {
                throw new ImageCompressionException("No suitable image writer found for format: " + extension);
            }
            ImageWriter writer = writers.next();
            writer.setOutput(ios);

            ImageWriteParam param = writer.getDefaultWriteParam();
            if (param.canWriteCompressed()) {
                param.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
                param.setCompressionQuality(QUALITY);
            }

            writer.write(null, new IIOImage(image, null, null), param);
            writer.dispose();
            return outputFile;

        } catch (IOException e) {
            throw new ImageCompressionException("Failed to compress image: " + originalFilename, e);
        }
    }

    /**
     * Check if an image format is supported
     */
    public static boolean isSupportedImage(String extension) {
        return extension.equalsIgnoreCase(JPG) ||
                extension.equalsIgnoreCase(JPEG) ||
                extension.equalsIgnoreCase(PNG) ||
                extension.equalsIgnoreCase(GIF);
    }
}
