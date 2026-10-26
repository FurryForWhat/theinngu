package com.theinngu32.common.entity;

import com.theinngu32.common.converter.JoinedStatusConverter;
import com.theinngu32.common.converter.RatingConverter;
import com.theinngu32.common.converter.StatusConverter;
import com.theinngu32.common.entity.constants.JoinedStatus;
import com.theinngu32.common.entity.constants.Rating;
import com.theinngu32.common.entity.constants.Status;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;

@EqualsAndHashCode(callSuper=false)
@SuperBuilder
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name = "yogi_meditation_classes")
public class YogiMeditationClass extends MasterData{

    @ManyToOne
    @JoinColumn(name = "meditation_class_id")
    private MeditationClass meditationClass;

    @ManyToOne
    @JoinColumn(name = "yogi_id")
    private Yogi yogi;

    @Column
    private LocalDate joinedDate;

    @Column
    @Convert(converter = StatusConverter.class)
    private Status status;

    @Column
    @Convert(converter = JoinedStatusConverter.class)
    private JoinedStatus joinedStatus;

    @Column
    @Convert(converter = RatingConverter.class)
    private Rating rating;

    @Column
    private String remark;

    @Column
    private boolean blacklisted;
}
