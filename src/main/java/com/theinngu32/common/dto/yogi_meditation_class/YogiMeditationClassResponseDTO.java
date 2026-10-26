package com.theinngu32.common.dto.yogi_meditation_class;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

@EqualsAndHashCode(callSuper = true)
@Data
public class YogiMeditationClassResponseDTO extends MasterYogiMeditationClassDTO{
    private Long id;
    private LocalDateTime updatedAt;
    private LocalDateTime createdAt;
    private Long createdById;
    private Long updatedById;
}
