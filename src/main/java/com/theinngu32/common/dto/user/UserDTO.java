package com.theinngu32.common.dto.user;

import com.theinngu32.common.dto.ErrorDto;
import com.theinngu32.common.entity.constants.errorMessage.ErrorAware;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO implements ErrorAware {
    private Long id;
    private String name;
    private String email;
    private LocalDateTime confirmedAt;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Long role;
    public boolean isConfirmed() {
        return confirmedAt != null;
    }
    private List<ErrorDto> errors;

    @Override
    public void setErrors(List<ErrorDto> errors) {
        this.errors = errors;
    }
}
