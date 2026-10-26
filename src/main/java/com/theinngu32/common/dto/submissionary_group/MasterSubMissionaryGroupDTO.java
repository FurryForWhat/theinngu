package com.theinngu32.common.dto.submissionary_group;

import com.theinngu32.common.entity.constants.Status;
import lombok.Data;

@Data
public class MasterSubMissionaryGroupDTO {
    private String name;
    private String code;
    private String description;
    private Long missionaryGroupId;
    private Status status;
}
