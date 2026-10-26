package com.theinngu32.common.entity.importfile;

import com.theinngu32.common.entity.MeditationClass;
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
@Table(name = "yogi_enroll_files")
public class YogiEnrollFile extends MasterImportFile {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "meditation_class_id", nullable = false)
    private MeditationClass meditationClass;

}
