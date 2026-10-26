package com.theinngu32.common.dto.importfile.yogi_enroll_file;

import com.theinngu32.common.converter.ProcessingStatusConverter;
import com.theinngu32.common.converter.StatusConverter;
import com.theinngu32.common.dto.importfile.MasterImportFileDTO;
import com.theinngu32.common.entity.constants.ProcessingStatus;
import com.theinngu32.common.entity.constants.Status;
import jakarta.persistence.Convert;
import lombok.Data;

@Data
public class MasterYogiEnrollFileDTO extends MasterImportFileDTO {
    private Long meditationClassId;
}
