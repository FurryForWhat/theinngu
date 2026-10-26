package com.theinngu32.common.entity;

import com.theinngu32.common.converter.GenderConverter;
import com.theinngu32.common.converter.StatusConverter;
import com.theinngu32.common.entity.constants.Gender;
import com.theinngu32.common.entity.constants.Status;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import java.time.LocalDate;

@MappedSuperclass
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@Data
@EqualsAndHashCode(callSuper = true)
public abstract class MasterYogi extends MasterData{

    @Column
    private String name;

    @Column
    private String fatherName;

    @Column
    private String healthStatus;

    @Column
    private String foodNotEaten;

    @Column
    private String address;

    @Column
    private String remark;

    @Column
    @Convert(converter = StatusConverter.class)
    private Status status;

    @Convert(converter = GenderConverter.class)
    @Column(name = "gender")
    private Gender genderType;

    @Column
    private String otherMeditationCenter;

    @Column
    private boolean blacklisted;

    @Column
    private LocalDate blacklistedAt;

    @Column
    private boolean isCompleted;

}
