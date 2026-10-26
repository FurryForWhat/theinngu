package com.theinngu32.common.converter;

import com.theinngu32.common.entity.constants.ProcessingStatus;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class ProcessingStatusConverter extends BaseEnumConverter<ProcessingStatus, Integer> {
    public ProcessingStatusConverter() {
        super(ProcessingStatus.class);
    }
}
