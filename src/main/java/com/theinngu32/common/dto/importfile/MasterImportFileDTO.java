package com.theinngu32.common.dto.importfile;

import com.theinngu32.common.converter.ProcessingStatusConverter;
import com.theinngu32.common.converter.StatusConverter;
import com.theinngu32.common.entity.constants.ProcessingStatus;
import com.theinngu32.common.entity.constants.Status;
import jakarta.persistence.Convert;
import lombok.Data;

@Data
public class MasterImportFileDTO {
    private String fileName;
    @Convert(converter = StatusConverter.class)
    private Status fileStatus;
    private String version;
    @Convert(converter = ProcessingStatusConverter.class)
    private ProcessingStatus processingStatus;

}
