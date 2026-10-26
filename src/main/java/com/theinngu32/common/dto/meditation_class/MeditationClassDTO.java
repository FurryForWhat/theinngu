package com.theinngu32.common.dto.meditation_class;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.theinngu32.common.dto.ErrorDto;
import com.theinngu32.common.entity.constants.errorMessage.ErrorAware;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@JsonSerialize
public class MeditationClassDTO extends MasterMeditationClassDTO implements ErrorAware {
    private Long id;
    private LocalDateTime updatedAt;
    private LocalDateTime createdAt;
    private Long createdById;
    private Long updatedById;
    private List<ErrorDto> errors;

    @Override
    public void setErrors(List<ErrorDto> errors) {
        this.errors = errors;
    }

}

