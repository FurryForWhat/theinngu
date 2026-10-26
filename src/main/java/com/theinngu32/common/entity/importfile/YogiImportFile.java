package com.theinngu32.common.entity.importfile;

import com.theinngu32.common.converter.ProcessingStatusConverter;
import com.theinngu32.common.converter.StatusConverter;
import com.theinngu32.common.entity.MasterData;
import com.theinngu32.common.entity.SubMissionaryGroup;
import com.theinngu32.common.entity.constants.ProcessingStatus;
import com.theinngu32.common.entity.constants.Status;
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
@Table(name = "yogi_import_files")
public class YogiImportFile extends MasterImportFile {
    @ManyToOne
    @JoinColumn(name = "sub_missionary_group_id")
    private SubMissionaryGroup subMissionaryGroup;

}
