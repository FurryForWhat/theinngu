package com.theinngu32.common.mapper;

import com.theinngu32.common.dto.missionary_group.MissionaryGroupDTO;
import com.theinngu32.common.dto.missionary_group.MissionaryGroupCreateDTO;
import com.theinngu32.common.entity.MissionaryGroup;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface MissionaryGroupMapper {
    MissionaryGroupMapper INSTANCE = Mappers.getMapper(MissionaryGroupMapper.class);

    @Mappings({
            @Mapping(source = "underControl", target = "underControl")
    })
    MissionaryGroup toEntity(MissionaryGroupCreateDTO missionaryGroupCreateDTO);

    @Mappings({
            @Mapping(source = "createdById", target = "createdBy.id"),
            @Mapping(source = "updatedById", target = "updatedBy.id")
    })
    MissionaryGroup toEntity(MissionaryGroupDTO missionaryGroupDTO);

    @Mappings({
            @Mapping(source = "createdBy.id", target = "createdById"),
            @Mapping(source = "updatedBy.id", target = "updatedById"),
    })
    MissionaryGroupDTO toDto(MissionaryGroup missionaryGroup);
}
