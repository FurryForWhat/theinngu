package com.theinngu32.common.mapper;

import com.theinngu32.common.dto.RoleDTO;
import com.theinngu32.common.entity.Role;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.springframework.context.annotation.Configuration;

@Configuration
@Mapper(componentModel = "spring")
public interface RoleMapper {

    RoleMapper INSTANCE = Mappers.getMapper(RoleMapper.class);

    RoleDTO toDto(Role role);

    Role toEntity(RoleDTO roleDTO);
}

