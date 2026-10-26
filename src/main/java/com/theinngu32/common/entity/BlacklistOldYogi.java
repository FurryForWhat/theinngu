package com.theinngu32.common.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@EqualsAndHashCode(callSuper=false)
@SuperBuilder
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name = "blacklist_old_yogis", uniqueConstraints = {@UniqueConstraint(columnNames = {"old_yogi_id", "created_by_id"})})
public class BlacklistOldYogi extends MasterData {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "old_yogi_id")
    private OldYogi oldYogi;

    @Column
    private String remark;

}
