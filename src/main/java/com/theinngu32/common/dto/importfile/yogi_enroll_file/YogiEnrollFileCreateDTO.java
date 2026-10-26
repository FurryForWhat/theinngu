package com.theinngu32.common.dto.importfile.yogi_enroll_file;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.web.multipart.MultipartFile;

@EqualsAndHashCode(callSuper = true)
@Data
public class YogiEnrollFileCreateDTO extends MasterYogiEnrollFileDTO {
    private MultipartFile yogiEnrollFile;
}
