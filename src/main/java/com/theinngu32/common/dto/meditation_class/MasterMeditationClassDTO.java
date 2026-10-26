package com.theinngu32.common.dto.meditation_class;

import com.theinngu32.common.entity.constants.ClassType;
import lombok.Data;

import java.time.LocalDate;

@Data
public class MasterMeditationClassDTO {
    private String title;
    private LocalDate startDate;
    private LocalDate endDate;
    private ClassType classType;
    private Long levelId;
    private Long subMissionaryGroupId;
}
