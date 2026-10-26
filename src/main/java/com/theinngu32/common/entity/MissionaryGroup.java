package com.theinngu32.common.entity;

import com.theinngu32.common.converter.StatusConverter;
import com.theinngu32.common.entity.constants.Status;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@EqualsAndHashCode(callSuper = true)
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Data
@Table(name = "missionary_groups")
public class MissionaryGroup extends MasterData{

    @Column
    private String name;
    @Column
    private String code;
    @Column
    private boolean underControl;

    @Column
    @Convert(converter = StatusConverter.class)
    private Status status;

}
