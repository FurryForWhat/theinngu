package com.theinngu32.common.mapper;

import com.theinngu32.common.dto.yogi.YogiCreateDTO;
import com.theinngu32.common.dto.yogi.YogiDTO;
import com.theinngu32.common.entity.Yogi;
import org.mapstruct.InheritConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface YogiMapper {

    YogiMapper INSTANCE = Mappers.getMapper(YogiMapper.class);
    @Mappings({
            @Mapping(source = "regionId", target = "region.id"),
            @Mapping(source = "cityId", target = "city.id"),
            @Mapping(source = "levelId", target = "level.id"),
            @Mapping(source = "subMissionaryGroupId", target = "subMissionaryGroup.id"),
    })
    Yogi toEntity(YogiCreateDTO yogiCreateDTO);

    @InheritConfiguration(name = "toEntity")
    @Mappings({
            @Mapping(source = "createdById", target = "createdBy.id"),
            @Mapping(source = "updatedById", target = "updatedBy.id")
    })
    Yogi toEntity(YogiDTO yogiDTO);

    @Mappings({
            @Mapping(source = "region.id", target = "regionId"),
            @Mapping(source = "city.id", target = "cityId"),
            @Mapping(source = "level.id", target = "levelId"),
            @Mapping(source = "subMissionaryGroup.id", target = "subMissionaryGroupId"),
            @Mapping(source = "createdBy.id", target = "createdById"),
            @Mapping(source = "updatedBy.id", target = "updatedById")
    })
    YogiDTO toDto(Yogi yogi);

}
