package com.theinngu32.common.dto.yogi_meditation_class;

import com.theinngu32.common.entity.constants.JoinedStatus;
import com.theinngu32.common.entity.constants.Rating;
import com.theinngu32.common.entity.constants.Status;
import lombok.Data;

import java.time.LocalDate;

@Data
public class MasterYogiMeditationClassDTO {
    private Long meditationClassId;
    private Long yogiId;
    private Status status;
    private JoinedStatus joinedStatus;
    private LocalDate joinedDate;
    private Rating rating;
    private String remark;
    private boolean blacklisted;
}
