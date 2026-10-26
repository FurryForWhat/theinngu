package com.theinngu32.common.dto.yogi;

import com.theinngu32.common.entity.constants.Gender;
import com.theinngu32.common.entity.constants.NrcType;
import lombok.Data;

import java.time.LocalDate;

@Data
public class MasterYogiDTO {
    private String name;
    private String fatherName;
    private String phone;
    private Gender genderType;
    private LocalDate birthDate;
    private LocalDate monkDate;
    private Long cityId;
    private Long regionId;
    private String address;
    private String emergencyContact;
    private String otherMeditationCenter;
    private String education;
    private String healthStatus;
    private String foodNotEaten;
    private Long subMissionaryGroupId;
    private Long levelId;

    private String postFixDigit;
    private NrcType nrcType;
    private Long nrcCodeId;

}
