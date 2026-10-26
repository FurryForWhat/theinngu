package com.theinngu32.common.converter;
import com.theinngu32.common.entity.constants.JoinedStatus;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class JoinedStatusConverter extends BaseEnumConverter<JoinedStatus, Integer>{
    protected JoinedStatusConverter() {
        super(JoinedStatus.class);
    }
}
