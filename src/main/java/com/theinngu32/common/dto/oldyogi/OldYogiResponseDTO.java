package com.theinngu32.common.dto.oldyogi;

import com.theinngu32.common.dto.ErrorDto;
import com.theinngu32.common.entity.constants.Status;
import com.theinngu32.common.entity.constants.errorMessage.ErrorAware;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
public class OldYogiResponseDTO extends MasterOldYogiDTO implements ErrorAware {
   private Long id;
   private Status status;
   private boolean isBlacklist;
   private LocalDate blacklistedAt;
   private boolean isCompleted;
   private int preCount;
   private LocalDateTime createdAt;
   private LocalDateTime updatedAt;
   private Long createdById;
   private Long updatedById;
   private List<ErrorDto> errors;

   @Override
   public void setErrors(List<ErrorDto> errors) {
      this.errors = errors;
   }
}