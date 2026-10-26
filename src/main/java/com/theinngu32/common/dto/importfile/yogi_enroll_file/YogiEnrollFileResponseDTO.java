package com.theinngu32.common.dto.importfile.yogi_enroll_file;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

@EqualsAndHashCode(callSuper = true)
@Data
public class YogiEnrollFileResponseDTO extends MasterYogiEnrollFileDTO {
    private Long id;
    private String fileUrl;
    private LocalDateTime updatedAt;
    private LocalDateTime createdAt;
    private Long createdById;
    private Long updatedById;
}
