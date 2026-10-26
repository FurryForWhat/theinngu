package com.theinngu32.common.dto.yogi;

import com.theinngu32.common.entity.constants.Rating;
import com.theinngu32.common.entity.constants.Status;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.time.LocalDate;

@EqualsAndHashCode(callSuper = true)
@Data
public class YogiUpdateDTO extends MasterYogiDTO{

    private boolean blacklist;
    private Status status;
    private LocalDate blacklistedAt;
    private boolean completed;
    private Rating rating;
    private String remark;
}
