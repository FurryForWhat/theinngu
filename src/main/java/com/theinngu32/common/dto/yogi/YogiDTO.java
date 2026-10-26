package com.theinngu32.common.dto.yogi;

import com.theinngu32.common.dto.ErrorDto;
import com.theinngu32.common.entity.constants.Rating;
import com.theinngu32.common.entity.constants.Status;
import com.theinngu32.common.entity.constants.errorMessage.ErrorAware;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
public class YogiDTO extends MasterYogiDTO implements ErrorAware {
    private long id;
    private boolean isBlacklist;
    private Status status;
    private LocalDate blacklistedAt;
    private boolean isCompleted;
    private Rating rating;
    private String remark;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Long createdById;
    private Long updatedById;
    private List<ErrorDto> errors;
}
