package com.theinngu32.common.entity;

import com.theinngu32.common.converter.ClassTypeConverter;
import com.theinngu32.common.entity.constants.ClassType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;

@EqualsAndHashCode(callSuper = true)
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "meditation_classes")
public class MeditationClass extends MasterData{

    @Column
    private String title;

    @Column
    private LocalDate startDate;

    @Column
    private LocalDate endDate;

    @Convert(converter = ClassTypeConverter.class)
    @Column(name = "class_type")
    private ClassType classType;

    @ManyToOne
    @JoinColumn(name = "level_id")
    private Level level;

    @ManyToOne
    @JoinColumn(name = "sub_missionary_group_id", nullable = false)
    private SubMissionaryGroup subMissionaryGroup;

}
