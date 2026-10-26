package com.theinngu32.common.mapper;

import com.theinngu32.common.dto.submissionary_group.SubMissionaryGroupCreateDTO;
import com.theinngu32.common.dto.submissionary_group.SubMissionaryGroupDTO;
import com.theinngu32.common.entity.SubMissionaryGroup;
import org.mapstruct.InheritConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;
import org.springframework.context.annotation.Configuration;

@Configuration
@Mapper(componentModel = "spring")
public interface SubMissionaryGroupMapper {
    SubMissionaryGroupMapper INSTANCE = Mappers.getMapper(SubMissionaryGroupMapper.class);

    @Mappings({
            @Mapping(source = "missionaryGroupId", target = "missionaryGroup.id")
    })
    SubMissionaryGroup toEntity(SubMissionaryGroupCreateDTO subMissionaryGroupCreateDTO);

    @InheritConfiguration
    @Mappings({
            @Mapping(source = "createdById", target = "createdBy.id"),
            @Mapping(source = "updatedById", target = "updatedBy.id")
    })
    SubMissionaryGroup toEntity(SubMissionaryGroupDTO missionaryGroupResponseDTO);

    @Mappings({
            @Mapping(source = "missionaryGroup.id", target = "missionaryGroupId"),
            @Mapping(source = "createdBy.id", target = "createdById"),
            @Mapping(source = "updatedBy.id", target = "updatedById")
    })
    SubMissionaryGroupDTO toDto(SubMissionaryGroup subMissionaryGroup);
}

