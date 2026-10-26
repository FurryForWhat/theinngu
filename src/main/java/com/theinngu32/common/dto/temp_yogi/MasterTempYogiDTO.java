package com.theinngu32.common.dto.temp_yogi;

import com.theinngu32.common.entity.constants.Status;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MasterTempYogiDTO {
    private String name;
    private String fatherName;
    private String yogiId;
    private String phone;
    private String nrc;
    private String gender;
    private String address;
    private String emergencyContact;
    private String birthDate;
    private String monkDate;
    private String cityId;
    private String regionId;
    private boolean blacklist;
    private String foodNotEaten;
    private String healthStatus;
    private String education;
    private String otherMedicationCenter;
    private String levelId;
    private String yogiStatus;
    private Status status;
    private String remark;
    private Long recordId;
    private String recordType;
    private Long oldYogiId;
}
