package com.theinngu32.common.mapper;

import com.theinngu32.common.dto.temp_yogi.TempYogiCreateDTO;
import com.theinngu32.common.dto.temp_yogi.TempYogiResponseDTO;
import com.theinngu32.common.entity.TempYogi;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface TempYogiMapper {

    TempYogiMapper INSTANCE = Mappers.getMapper(TempYogiMapper.class);

    @Mappings({
            @Mapping(source = "updatedBy.id", target = "updatedById"),
            @Mapping(source = "createdBy.id", target = "createdById"),
            @Mapping(source = "oldYogi.id", target = "oldYogiId"),
            @Mapping(source = "city", target = "cityId"),
            @Mapping(source = "region", target = "regionId"),
            @Mapping(source = "level", target = "levelId")
    })
    TempYogiResponseDTO toDTO(TempYogi tempYogi);

    @Mappings({
            @Mapping(source = "oldYogiId", target = "oldYogi.id"),
            @Mapping(source = "regionId", target = "region"),
            @Mapping(source = "cityId", target = "city"),
            @Mapping(source = "levelId", target = "level")
    })
    TempYogi toEntity(TempYogiCreateDTO tempYogiCreateDTO);

    @InheritConfiguration
    @Mappings({
            @Mapping(source = "createdById", target = "createdBy.id"),
            @Mapping(source = "updatedById", target = "updatedBy.id")
    })
    TempYogi toEntity(TempYogiResponseDTO tempYogiResponseDTO);
}
