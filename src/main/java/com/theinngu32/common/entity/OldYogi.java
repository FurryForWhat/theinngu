package com.theinngu32.common.entity;

import com.theinngu32.common.entity.constants.Status;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@EqualsAndHashCode(callSuper = true)
@SuperBuilder
@Entity
@Data
@NoArgsConstructor
@Table(name = "old_yogis")
public class OldYogi extends MasterYogi{

    @Column
    private String age;

    @Column
    private String nrc;

    @Column
    private int preCount;

    @PrePersist
    @PreUpdate
    public void updateEntityBeforeSave() {
        boolean isAllFieldsFilled =
                        isNotEmpty(this.getName()) &&
                        isNotEmpty(this.getFatherName()) &&
                        isNotEmpty(this.getHealthStatus()) &&
                        isNotEmpty(this.getFoodNotEaten()) &&
                        isNotEmpty(this.getAddress()) &&
                        isNotEmpty(this.getRemark()) &&
                        this.getStatus() != null &&
                        this.getGenderType() != null &&
                        isNotEmpty(this.getOtherMeditationCenter()) &&
                        isNotEmpty(this.getAge()) &&
                        isNotEmpty(this.getNrc());

        this.setCompleted(isAllFieldsFilled);

        if (this.getStatus() == null) {
            this.setStatus(Status.ACTIVE);  // Assuming Status is an enum or a similar object
        }
    }

    private boolean isNotEmpty(String value) {
        return value != null && !value.trim().isEmpty();
    }

}
