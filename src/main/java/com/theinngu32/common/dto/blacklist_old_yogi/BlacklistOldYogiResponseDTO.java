package com.theinngu32.common.dto.blacklist_old_yogi;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BlacklistOldYogiResponseDTO extends MasterBlacklistOldYogiDTO {
    private Long id;
    private LocalDateTime updatedAt;
    private LocalDateTime createdAt;
    private Long updatedById;
    private Long createdById;
}
