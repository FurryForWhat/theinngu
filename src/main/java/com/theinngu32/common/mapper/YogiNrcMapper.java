package com.theinngu32.common.mapper;

import com.theinngu32.common.dto.yogi_nrc.YogiNrcCreateDTO;
import com.theinngu32.common.dto.yogi_nrc.YogiNrcDTO;
import com.theinngu32.common.entity.YogiNrc;
import org.mapstruct.InheritConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface YogiNrcMapper {
    YogiNrcMapper INSTANCE = Mappers.getMapper(YogiNrcMapper.class);

    @Mappings({
            @Mapping(source = "nrcCodeId", target = "nrcCode.id"),
            @Mapping(source = "yogiId", target = "yogi.id")
    })
    YogiNrc toEntity(YogiNrcCreateDTO yogiNrcCreateDTO);

    @InheritConfiguration(name = "toEntity")
    @Mappings({
            @Mapping(source = "createdById", target = "createdBy.id"),
            @Mapping(source = "updatedById", target = "updatedBy.id")
    })
    YogiNrc toEntity(YogiNrcDTO yogiNrcDTO);

    @Mappings({
            @Mapping(source = "nrcCode.id", target = "nrcCodeId"),
            @Mapping(source = "yogi.id", target = "yogiId"),
            @Mapping(source = "createdBy.id", target = "createdById"),
            @Mapping(source = "updatedBy.id", target = "updatedById")
    })
    YogiNrcDTO toDto(YogiNrc yogiNrc);
}
