package com.theinngu32.common.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
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
@Table(name = "storage_blobs")
public class StorageBlob extends MasterData {
    @Column(nullable = false)
    private String key;

    @Column(nullable = false)
    private String filename;

    @Column
    private String contentType;

    @Column(columnDefinition = "text")
    private String metadata;

    @Column(nullable = false)
    private String serviceName;

    @Column(nullable = false)
    private long byteSize;

    @Column
    private String checksum;
}
