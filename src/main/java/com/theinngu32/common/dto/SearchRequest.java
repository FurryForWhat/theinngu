package com.theinngu32.common.dto;

import lombok.Data;
import org.springframework.data.domain.Pageable;

import java.util.List;

@Data
public class SearchRequest {
    private List<SearchFilter> filters;
}
