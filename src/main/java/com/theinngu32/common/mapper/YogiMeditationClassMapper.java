package com.theinngu32.common.mapper;

import com.theinngu32.common.dto.yogi_meditation_class.YogiMeditationClassCreateDTO;
import com.theinngu32.common.dto.yogi_meditation_class.YogiMeditationClassResponseDTO;
import com.theinngu32.common.entity.YogiMeditationClass;
import org.mapstruct.InheritConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface YogiMeditationClassMapper {
    YogiMeditationClassMapper INSTANCE = Mappers.getMapper(YogiMeditationClassMapper.class);

    @Mappings({
            @Mapping(source = "meditationClassId", target = "meditationClass.id"),
            @Mapping(source = "yogiId", target = "yogi.id"),
    })
    YogiMeditationClass toEntity(YogiMeditationClassCreateDTO yogiMeditationClassCreateDTO);

    @InheritConfiguration(name = "toEntity")
    @Mappings({
            @Mapping(source = "updatedById", target = "updatedBy.id"),
            @Mapping(source = "createdById", target = "createdBy.id")
    })
    YogiMeditationClass toEntity(YogiMeditationClassResponseDTO yogiMeditationClassResponseDTO);

    @Mappings({
            @Mapping(source = "updatedBy.id", target = "updatedById"),
            @Mapping(source = "createdBy.id", target = "createdById"),
            @Mapping(source = "yogi.id", target = "yogiId"),
            @Mapping(source = "meditationClass.id", target = "meditationClassId")
    })
    YogiMeditationClassResponseDTO toDTO(YogiMeditationClass yogiMeditationClass);

}
