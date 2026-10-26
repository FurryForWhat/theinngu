package com.theinngu32.common.service;
import com.theinngu32.common.dto.SearchRequest;
import com.theinngu32.common.specifications.GenericSpecification;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Service;

@Service
public class GenericSearchService{
    public <T> Page<T> search(SearchRequest request, JpaSpecificationExecutor<T> repository, Pageable pageable) {
        Specification<T> spec = new GenericSpecification<>(request.getFilters());
        return repository.findAll(spec, pageable);
    }
}

