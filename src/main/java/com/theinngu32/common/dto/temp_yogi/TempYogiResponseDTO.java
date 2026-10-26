package com.theinngu32.common.dto.temp_yogi;

import com.theinngu32.common.dto.ErrorDto;
import com.theinngu32.common.entity.constants.errorMessage.ErrorAware;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
public class TempYogiResponseDTO extends MasterTempYogiDTO implements ErrorAware {
    private LocalDateTime updatedAt;
    private LocalDateTime createdAt;
    private Long createdById;
    private Long updatedById;
    private List<ErrorDto> errors;
}
