package com.theinngu32.common.dto.missionary_group;

import com.theinngu32.common.entity.constants.Status;
import lombok.Data;

@Data
public class MasterMissionaryGroupDTO {
    private String name;
    private String code;
    private boolean isUnderControl;
    private Status status;
}
