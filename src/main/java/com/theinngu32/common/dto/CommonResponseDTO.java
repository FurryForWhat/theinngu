package com.theinngu32.common.dto;

import lombok.Data;

@Data
public class CommonResponseDTO<T> {

    private T responseDTO;
    private String message;
}
