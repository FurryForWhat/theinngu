package com.theinngu32.common.dto.importfile.yogi_import_file;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.web.multipart.MultipartFile;

@EqualsAndHashCode(callSuper = true)
@Data
public class YogiImportFileCreateDTO extends MasterYogiImportFileDTO{
    private MultipartFile yogiRegisterFile;
}
