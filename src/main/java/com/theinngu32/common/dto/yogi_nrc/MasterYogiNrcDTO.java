package com.theinngu32.common.dto.yogi_nrc;

import com.theinngu32.common.entity.constants.NrcType;
import lombok.Data;

@Data
public class MasterYogiNrcDTO {
    private String postFixDigit;
    private NrcType type;
    private Long nrcCodeId;
    private Long yogiId;
}
