package com.theinngu32.common.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.theinngu32.common.converter.StatusConverter;
import com.theinngu32.common.entity.constants.Status;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.HashSet;
import java.util.Set;

@EqualsAndHashCode(callSuper = true)
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "sub_missionary_groups")
public class SubMissionaryGroup extends MasterData {

    @Column
    private String name;
    @Column
    private String code;
    @Column
    private String description;

    @ManyToOne
    @JoinColumn(name = "missionary_group_id")
    private MissionaryGroup missionaryGroup;

    @Column
    @Convert(converter = StatusConverter.class)
    private Status status;

    @OneToMany(mappedBy = "subMissionaryGroup",fetch = FetchType.LAZY)
    @JsonIgnore
    private Set<Yogi> yogis = new HashSet<>();
}
