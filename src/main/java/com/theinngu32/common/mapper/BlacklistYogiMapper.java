package com.theinngu32.common.mapper;

import com.theinngu32.common.dto.blacklist_yogis.BlacklistYogiCreateDto;
import com.theinngu32.common.dto.blacklist_yogis.BlacklistYogiResponseDTO;
import com.theinngu32.common.entity.BlacklistYogi;
import org.mapstruct.InheritConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface BlacklistYogiMapper {

    BlacklistYogiMapper INSTANCE = Mappers.getMapper(BlacklistYogiMapper.class);

    @Mappings({
            @Mapping(source = "yogi.id", target = "yogiId"),
            @Mapping(source = "createdBy.id", target = "createdById"),
            @Mapping(source = "updatedBy.id", target = "updatedById")
    })
    BlacklistYogiResponseDTO toDto(BlacklistYogi blacklistYogi);

    @Mappings({
            @Mapping(source = "yogiId", target = "yogi.id")
    })
    BlacklistYogi toEntity(BlacklistYogiCreateDto blacklistYogiCreateDto);

    @InheritConfiguration(name = "toEntity")
    @Mappings({
            @Mapping(source = "createdById", target = "createdBy.id"),
            @Mapping(source = "updatedById", target = "updatedBy.id")
    })
    BlacklistYogi toEntity(BlacklistYogiResponseDTO blacklistYogiResponseDTO);
}
