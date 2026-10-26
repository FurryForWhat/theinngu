package com.theinngu32.common.dto;

import com.theinngu32.common.entity.constants.Status;
import lombok.Data;

@Data
public class RoleDTO {
    private Long id;
    private String name;
    private String description;
    private Status status;
}
