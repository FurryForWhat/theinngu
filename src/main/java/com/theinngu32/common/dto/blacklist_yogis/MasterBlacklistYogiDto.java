package com.theinngu32.common.dto.blacklist_yogis;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MasterBlacklistYogiDto {
    private Long yogiId;
    private String remark;
}
