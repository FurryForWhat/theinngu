package com.theinngu32.common.mapper;

import com.theinngu32.common.dto.oldyogi.OldYogiCreateDTO;
import com.theinngu32.common.dto.oldyogi.OldYogiResponseDTO;
import com.theinngu32.common.entity.OldYogi;
import org.mapstruct.InheritConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface OldYogiMapper {

    OldYogiMapper INSTANCE = Mappers.getMapper(OldYogiMapper.class);

    OldYogi toEntity(OldYogiCreateDTO oldYogiCreateDTO);

    @InheritConfiguration
    @Mappings({
            @Mapping(source = "updatedById", target = "updatedBy.id"),
            @Mapping(source = "createdById", target = "createdBy.id")
    })
    OldYogi toEntity(OldYogiResponseDTO oldYogiDTO);

    @Mappings({
            @Mapping(source = "updatedBy.id", target = "updatedById"),
            @Mapping(source = "createdBy.id", target = "createdById")
    })
    OldYogiResponseDTO toDTO(OldYogi oldYogi);

}
