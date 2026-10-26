package com.theinngu32.common.mapper;

import com.theinngu32.common.dto.importfile.yogi_import_file.YogiImportFileCreateDTO;
import com.theinngu32.common.dto.importfile.yogi_import_file.YogiImportFileResponseDTO;
import com.theinngu32.common.dto.importfile.yogi_import_file.YogiImportFileUpdateDTO;
import com.theinngu32.common.entity.importfile.YogiImportFile;
import org.mapstruct.InheritConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface YogiImportFileMapper {
    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    @Mappings({
            @Mapping(source = "updatedBy.id", target = "updatedById"),
            @Mapping(source = "createdBy.id", target = "createdById")
    })
    YogiImportFileResponseDTO toDto(YogiImportFile yogiImportFile);

    @InheritConfiguration
    YogiImportFile toEntity(YogiImportFileCreateDTO yogiImportFileCreateDTO);

    @InheritConfiguration
    YogiImportFile toEntity(YogiImportFileUpdateDTO yogiImportFileUpdateDTO);

    @InheritConfiguration
    YogiImportFile toEntity(YogiImportFileResponseDTO yogiImportFileResponseDTO);
}
