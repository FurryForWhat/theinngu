package com.theinngu32.common.converter;
import com.theinngu32.common.entity.constants.Rating;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class RatingConverter extends BaseEnumConverter<Rating, Integer> {

    public RatingConverter() {
        super(Rating.class);
    }
}
