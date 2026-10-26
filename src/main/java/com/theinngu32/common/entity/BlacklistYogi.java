package com.theinngu32.common.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@EqualsAndHashCode(callSuper = true)
@SuperBuilder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "blacklist_yogis", uniqueConstraints = {@UniqueConstraint(columnNames = {"yogi_id", "created_by_id"})})
public class BlacklistYogi extends MasterData {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "yogi_id", nullable = false)
    private Yogi yogi;

    @Column
    private String remark;
}
