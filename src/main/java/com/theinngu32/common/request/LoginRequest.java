package com.theinngu32.common.request;

import lombok.Data;

@Data
public class LoginRequest {
    private String username;
    private String password;
}
