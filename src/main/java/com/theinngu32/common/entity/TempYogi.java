package com.theinngu32.common.entity;

import com.theinngu32.common.converter.StatusConverter;
import com.theinngu32.common.entity.constants.Status;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import static com.theinngu32.common.utils.constants.TempYogiConstants.BLACKLIST;
import static com.theinngu32.common.utils.constants.TempYogiConstants.DATE_SEPERATOR;

@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper=false)
@Data
@Table(name = "temp_yogis")
@Entity
public class TempYogi extends MasterData {
    @Column
    private String name;
    
    @Column
    private String fatherName;

    @Column
    private String yogiId;

    @Column
    private String phone;

    private String passport;

    @Column
    private String nrcPrefixCode;

    @Column
    private String nrcDivision;

    @Column
    private String nrcType;

    @Column
    private String nrcPostFixDigit;

    @Column
    private String nrc;

    @Column
    private String gender;

    @Column
    private String address;

    @Column
    private String emergencyContact;

    @Column
    private String birthDate;

    @Column
    private String dobDay;

    @Column
    private String dobMonth;

    @Column
    private String dobYear;

    @Column
    private String monkDate;

    @Column
    private String monkDay;

    @Column
    private String monkMonth;

    @Column
    private String monkYear;

    @Column
    private String city;

    @Column
    private String region;

    @Column
    private String country;

    @Column
    private String yogiStatus;

    @Column
    private boolean blacklist;

    @Column
    private String foodNotEaten;

    @Column
    private String healthStatus;

    @Column
    private String education;

    @Column
    private String otherMedicationCenter;

    @Column
    private String level;

    @Column
    @Convert(converter = StatusConverter.class)
    private Status status;

    @Column
    private String remark;

    @Column
    private String recordType;

    @Column
    private Long recordId;

    @Column
    private String attachmentName;

    @Column
    private int rowIndex;

    @Column
    private boolean neededModification;

    @Column
    private String sheetName;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "old_yogi_id", nullable = true)
    private OldYogi oldYogi;

    @PrePersist
    @PreUpdate
    public void prePersistAndUpdate() {
        if (isValidDate(dobDay, dobMonth, dobYear)) {
            birthDate = String.join(DATE_SEPERATOR, dobDay, dobMonth, dobYear);
        }
        if (isValidDate(monkDay, monkMonth, monkYear)) {
            monkDate = String.join(DATE_SEPERATOR, monkDay, monkMonth, monkYear);
        }
        if(BLACKLIST.equals(yogiStatus)) {
            blacklist = true;
        }
    }

    /**
     * Helper method to validate if all date components (day, month, year) are valid.
     */
    private boolean isValidDate(String day, String month, String year) {
        return !(isNullOrEmpty(day) || isNullOrEmpty(month) || isNullOrEmpty(year));
    }

    /**
     * Utility method to check if a string is null or empty or blank.
     */
    private boolean isNullOrEmpty(String str) {
        return str == null || str.trim().isEmpty() || str.trim().equals("N/A");
    }
}
