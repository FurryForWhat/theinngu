package com.theinngu32.common.service;

import com.theinngu32.common.dto.SearchRequest;
import com.theinngu32.common.dto.yogi_nrc.YogiNrcCreateDTO;
import com.theinngu32.common.dto.yogi_nrc.YogiNrcUpdateDTO;
import com.theinngu32.common.entity.*;
import com.theinngu32.common.exception.ResourceAlreadyExistsException;
import com.theinngu32.common.exception.ResourceNotFoundException;
import com.theinngu32.common.mapper.YogiNrcMapper;
import com.theinngu32.common.repository.NrcCodeRepository;
import com.theinngu32.common.repository.YogiNrcRepository;
import com.theinngu32.common.repository.YogiRepository;
import com.theinngu32.common.dto.yogi_nrc.YogiNrcDTO;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class YogiNrcService {

    private final NrcCodeRepository nrcCodeRepository;
    private final YogiRepository yogiRepository;
    private final YogiNrcRepository yogiNrcRepository;
    private final GenericSearchService searchService;
    private final YogiNrcMapper yogiNrcMapper;
    private final EntityManager entityManager;

    @Transactional
    public List<YogiNrcDTO> getAll() {
        return yogiNrcRepository.findAll().stream()
                .map(yogiNrcMapper::toDto)
                .toList();
    }

    @Transactional
    public YogiNrcDTO getById(long id) {
        YogiNrc yogiNrc = yogiNrcRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("YogiNrc with ID %d not found.".formatted(id)));
        return yogiNrcMapper.toDto(yogiNrc);
    }

    @Transactional
    public YogiNrcDTO create(YogiNrcCreateDTO yogiNrcCreateDTO) {
        nrcCodeRepository.findById(yogiNrcCreateDTO.getNrcCodeId())
                .orElseThrow(() -> new ResourceNotFoundException("NRC Code not found with ID: " + yogiNrcCreateDTO.getNrcCodeId()));
        yogiRepository.findById(yogiNrcCreateDTO.getYogiId())
                .orElseThrow(() -> new ResourceNotFoundException("Yogi not found with ID: " + yogiNrcCreateDTO.getYogiId()));
        if (yogiNrcRepository.findYogiNrcByPostFixDigitAndTypeAndNrcCodeId(yogiNrcCreateDTO.getPostFixDigit(), yogiNrcCreateDTO.getType(), yogiNrcCreateDTO.getNrcCodeId()))
            throw new ResourceAlreadyExistsException("Yogi Nrc is already existed with nrcCodeId %d and type %s and postFixDigit %s"
                    .formatted(yogiNrcCreateDTO.getNrcCodeId(), yogiNrcCreateDTO.getType(), yogiNrcCreateDTO.getPostFixDigit()));
        if (yogiNrcRepository.findYogiNrcByYogiId(yogiNrcCreateDTO.getYogiId()) != null)
            throw new ResourceAlreadyExistsException("Yogi with Id %d already have Yogi Nrc".formatted(yogiNrcCreateDTO.getYogiId()));
        YogiNrc yogiNrc = yogiNrcMapper.toEntity(yogiNrcCreateDTO);
        yogiNrc = yogiNrcRepository.save(yogiNrc);
        entityManager.refresh(yogiNrc);
        return yogiNrcMapper.toDto(yogiNrc);
    }

    @Transactional
    public YogiNrcDTO update(Long id, YogiNrcUpdateDTO yogiNrcUpdateDTO) {
        YogiNrc yogiNrc = yogiNrcRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Yogi NRC not found with ID: " + id));
        NrcCode nrcCode = nrcCodeRepository.findById(yogiNrcUpdateDTO.getNrcCodeId())
                .orElseThrow(() -> new ResourceNotFoundException("NRC Code not found with ID: " + yogiNrcUpdateDTO.getNrcCodeId()));
        Yogi yogi = yogiRepository.findById(yogiNrcUpdateDTO.getYogiId())
                .orElseThrow(() -> new ResourceNotFoundException("Yogi not found with ID: " + yogiNrcUpdateDTO.getYogiId()));
        if (yogiNrcRepository.findYogiNrcByPostFixDigitAndTypeAndNrcCodeIdAndIdNot(yogiNrcUpdateDTO.getPostFixDigit(), yogiNrcUpdateDTO.getType(), yogiNrcUpdateDTO.getNrcCodeId(), id))
            throw new ResourceAlreadyExistsException("Yogi Nrc already exists with nrcCodeId %d and type %s and postFixDigit %s"
                    .formatted(yogiNrcUpdateDTO.getNrcCodeId(), yogiNrcUpdateDTO.getType(), yogiNrcUpdateDTO.getPostFixDigit()));
        if (yogiNrcRepository.findYogiNrcByYogiId(yogiNrcUpdateDTO.getYogiId()) != null && !yogiNrc.getYogi().getId().equals(yogiNrcUpdateDTO.getYogiId()))
            throw new ResourceAlreadyExistsException("Yogi with Id %d already have Yogi Nrc".formatted(yogiNrcUpdateDTO.getYogiId()));
        yogiNrc.setPostFixDigit(yogiNrcUpdateDTO.getPostFixDigit());
        yogiNrc.setType(yogiNrcUpdateDTO.getType());
        yogiNrc.setNrcCode(nrcCode);
        yogiNrc.setYogi(yogi);
               YogiNrc updatedYogiNrc = yogiNrcRepository.save(yogiNrc);
        yogiNrcRepository.flush();
        entityManager.refresh(updatedYogiNrc);
        return yogiNrcMapper.toDto(updatedYogiNrc);
    }

    @Transactional
    public boolean delete(Long id) {
        YogiNrc yogiNrc = yogiNrcRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Yogi NRC not found with ID: " + id));
        yogiNrcRepository.delete(yogiNrc);
        return true;
    }

    @Transactional
    public Page<YogiNrcDTO> findYogiNrcByFilter(SearchRequest request, Pageable pageable) {
        Page<YogiNrc> yogiNrcs = searchService.search(request, yogiNrcRepository, pageable);
        return yogiNrcs.map(yogiNrcMapper::toDto);
    }
}
