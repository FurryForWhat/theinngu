package com.theinngu32.common.mapper;

import com.theinngu32.common.dto.blacklist_old_yogi.BlacklistOldYogiCreateDTO;
import com.theinngu32.common.dto.blacklist_old_yogi.BlacklistOldYogiResponseDTO;
import com.theinngu32.common.entity.BlacklistOldYogi;
import org.mapstruct.InheritConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface BlacklistOldYogiMapper {

    BlacklistOldYogiMapper INSTANCE = Mappers.getMapper(BlacklistOldYogiMapper.class);

    @Mappings({
            @Mapping(source = "oldYogi.id", target = "oldYogiId"),
            @Mapping(source = "createdBy.id", target = "createdById"),
            @Mapping(source = "updatedBy.id", target = "updatedById")
    })
    BlacklistOldYogiResponseDTO toDto(BlacklistOldYogi blacklistOldYogi);

    @Mappings({
            @Mapping(source = "oldYogiId", target = "oldYogi.id")
    })
    BlacklistOldYogi toEntity(BlacklistOldYogiCreateDTO blacklistOldYogiCreateDTO);

    @InheritConfiguration(name = "toEntity")
    @Mappings({
            @Mapping(source = "createdById", target = "createdBy.id"),
            @Mapping(source = "updatedById", target = "updatedBy.id")
    })
    BlacklistOldYogi toEntity(BlacklistOldYogiResponseDTO blacklistOldYogiResponseDTO);
}
