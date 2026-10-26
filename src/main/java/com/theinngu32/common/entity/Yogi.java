package com.theinngu32.common.entity;

import com.theinngu32.common.converter.RatingConverter;
import com.theinngu32.common.entity.constants.Rating;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import java.time.LocalDate;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@SuperBuilder
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "yogis")
public class Yogi extends MasterYogi{
    @Column
    private String yogiId;

    @Column
    private String nrc;

    @Column
    private String phone;

    @Column
    private LocalDate birthDate;

    @Column
    private LocalDate monkDate;

    @Column
    private String emergencyContact;

    @Column
    private String education;

    @ManyToOne
    @JoinColumn(name = "level_id")
    private Level level;

    @ManyToOne
    @JoinColumn(name = "old_yogi_id")
    private OldYogi oldYogi;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "city_id")
    private City city;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "region_id")
    private Region region;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sub_missionary_group_id")
    private SubMissionaryGroup subMissionaryGroup;

    @Convert(converter = RatingConverter.class)
    @Column(name = "rating")
    private Rating rating;

}
