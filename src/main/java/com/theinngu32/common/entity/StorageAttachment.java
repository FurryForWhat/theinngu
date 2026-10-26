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
@Table(name = "storage_attachments")
public class StorageAttachment extends MasterData{
    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String recordType;

    @Column(nullable = false)
    private Long recordId;

    @OneToOne
    @JoinColumn(name = "blob_id", referencedColumnName = "id", nullable = false)
    private StorageBlob storageBlob;
}
