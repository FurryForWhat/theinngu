package com.theinngu32.common.entity.importfile;

import com.theinngu32.common.converter.ProcessingStatusConverter;
import com.theinngu32.common.converter.StatusConverter;
import com.theinngu32.common.entity.MasterData;
import com.theinngu32.common.entity.constants.ProcessingStatus;
import com.theinngu32.common.entity.constants.Status;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.Transient;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.web.multipart.MultipartFile;

@MappedSuperclass
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@Data
@EqualsAndHashCode(callSuper = false)
public class MasterImportFile extends MasterData {
    @Column
    private String fileName;

    @Column
    @Convert(converter = StatusConverter.class)
    private Status fileStatus;

    @Column
    private String version;

    @Column
    @Convert(converter = ProcessingStatusConverter.class)
    private ProcessingStatus processingStatus;

    @Transient
    private MultipartFile importFile;
}
