package com.theinngu32.common.dto.oldyogi;

import com.theinngu32.common.entity.constants.Gender;
import lombok.Data;

@Data
public class MasterOldYogiDTO {
    private String name;
    private String fatherName;
    private String healthStatus;
    private String foodNotEaten;
    private String address;
    private String remark;
    private Gender genderType;
    private String otherMeditationCenter;
    private String age;
    private String nrc;
}
