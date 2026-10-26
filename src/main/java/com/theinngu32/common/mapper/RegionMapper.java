package com.theinngu32.common.mapper;

import com.theinngu32.common.dto.region.RegionCreateDTO;
import com.theinngu32.common.dto.region.RegionDTO;
import com.theinngu32.common.entity.Region;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface RegionMapper {

    RegionMapper INSTANCE = Mappers.getMapper(RegionMapper.class);

    Region toEntity(RegionCreateDTO regionCreateDTO);

    @Mappings({
            @Mapping(source = "createdById", target = "createdBy.id"),
            @Mapping(source = "updatedById", target = "updatedBy.id")
    })
    Region toEntity(RegionDTO regionDTO);

    @Mappings({
            @Mapping(source = "createdBy.id", target = "createdById"),
            @Mapping(source = "updatedBy.id", target = "updatedById")
    })
    RegionDTO toDto(Region region);

}
