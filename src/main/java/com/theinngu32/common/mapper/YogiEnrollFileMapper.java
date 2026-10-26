package com.theinngu32.common.mapper;

import com.theinngu32.common.dto.importfile.yogi_enroll_file.YogiEnrollFileCreateDTO;
import com.theinngu32.common.dto.importfile.yogi_enroll_file.YogiEnrollFileResponseDTO;
import com.theinngu32.common.entity.importfile.YogiEnrollFile;
import org.mapstruct.InheritConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface YogiEnrollFileMapper {
    YogiEnrollFileMapper INSTANCE = Mappers.getMapper(YogiEnrollFileMapper.class);

    @Mappings({
            @Mapping(source = "createdBy.id", target = "createdById"),
            @Mapping(source = "updatedBy.id", target = "updatedById"),
            @Mapping(source = "meditationClass.id", target = "meditationClassId")
    })
    YogiEnrollFileResponseDTO toDto(YogiEnrollFile yogiEnrollFile);

    @Mapping(source = "meditationClassId", target = "meditationClass.id")
    YogiEnrollFile toEntity(YogiEnrollFileCreateDTO yogiEnrollFileCreateDTO);

    @InheritConfiguration
    @Mappings({
            @Mapping(source = "updatedById", target = "updatedBy.id"),
            @Mapping(source = "createdById", target = "createdBy.id")
    })
    YogiEnrollFile toEntity(YogiEnrollFileResponseDTO yogiEnrollFileResponseDTO);

}
