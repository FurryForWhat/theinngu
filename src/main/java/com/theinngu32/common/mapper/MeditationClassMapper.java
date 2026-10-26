package com.theinngu32.common.mapper;

import com.theinngu32.common.dto.meditation_class.MeditationClassCreateDTO;
import com.theinngu32.common.dto.meditation_class.MeditationClassDTO;
import com.theinngu32.common.entity.MeditationClass;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface MeditationClassMapper {

    MeditationClassMapper INSTANCE = Mappers.getMapper(MeditationClassMapper.class);

    @Mappings({
            @Mapping(source = "levelId", target = "level.id"),
            @Mapping(source = "subMissionaryGroupId", target = "subMissionaryGroup.id")
    })
    MeditationClass toEntity(MeditationClassCreateDTO meditationClassCreateDTO);

    @Mappings({
            @Mapping(source = "createdById", target = "createdBy.id"),
            @Mapping(source = "updatedById", target = "updatedBy.id")
    })
    MeditationClass toEntity(MeditationClassDTO meditationClassDTO);

    @Mappings({
            @Mapping(source = "level.id", target = "levelId"),
            @Mapping(source = "subMissionaryGroup.id", target = "subMissionaryGroupId"),
            @Mapping(source = "createdBy.id", target = "createdById"),
            @Mapping(source = "updatedBy.id", target = "updatedById")
    })
    MeditationClassDTO toDTO(MeditationClass meditationClass);
}
