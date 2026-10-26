package com.theinngu32.common.converter;
import com.theinngu32.common.entity.constants.Status;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class StatusConverter extends BaseEnumConverter<Status, Integer>{

     public StatusConverter() {
        super(Status.class);
    }
}