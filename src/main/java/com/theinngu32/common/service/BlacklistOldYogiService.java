package com.theinngu32.common.service;

import com.theinngu32.common.dto.SearchRequest;
import com.theinngu32.common.dto.blacklist_old_yogi.BlacklistOldYogiCreateDTO;
import com.theinngu32.common.dto.blacklist_old_yogi.BlacklistOldYogiResponseDTO;
import com.theinngu32.common.dto.blacklist_old_yogi.BlacklistOldYogiUpdateDTO;
import com.theinngu32.common.entity.*;
import com.theinngu32.common.exception.ResourceAlreadyExistsException;
import com.theinngu32.common.exception.ResourceNotFoundException;
import com.theinngu32.common.mapper.BlacklistOldYogiMapper;
import com.theinngu32.common.repository.BlacklistOldYogiRepository;
import com.theinngu32.common.repository.OldYogiRepository;
import com.theinngu32.common.repository.UserRepository;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BlacklistOldYogiService {

    private final BlacklistOldYogiMapper blacklistOldYogiMapper;
    private final BlacklistOldYogiRepository blacklistOldYogiRepository;
    private final UserRepository userRepository;
    private final OldYogiRepository oldYogiRepository;
    private final GenericSearchService searchService;
    private final EntityManager entityManager;

    @Transactional
    public List<BlacklistOldYogiResponseDTO> getAllBlacklistOldYogis() {
        return blacklistOldYogiRepository.findAll().stream()
                .map(blacklistOldYogiMapper::toDto)
                .collect(Collectors.toList());
    }

    @Transactional
    public BlacklistOldYogiResponseDTO findBlacklistOldYogiById(Long id) {
        BlacklistOldYogi blacklistOldYogi = blacklistOldYogiRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Blacklist Old Yogi With Old Yogi Id %d Not Found"
                        .formatted(id)));
        return blacklistOldYogiMapper.toDto(blacklistOldYogi);
    }

    @Transactional
    public List<BlacklistOldYogiResponseDTO> findBlacklistOldYogiByOldYogiId(Long id) {
        OldYogi oldYogi = oldYogiRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Old Yogi With Old Yogi Id %d Not Found".formatted(id)));
        List<BlacklistOldYogi> blacklistOldYogiList = blacklistOldYogiRepository.findByYogiId(oldYogi.getId())
                .orElseThrow(() -> new ResourceNotFoundException("No BlacklistYogi Found for the Given Old Yogi with Id %d"
                        .formatted(id)));

        return blacklistOldYogiList.stream()
                .map(blacklistOldYogiMapper::toDto)
                .collect(Collectors.toList());
    }

    @Transactional
    public List<BlacklistOldYogiResponseDTO> findBlacklistOldYogiByCreatedById(Long id) {
        User createdBy = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User Not Found With Id %d".formatted(id)));
        List<BlacklistOldYogi> blacklistOldYogiList = blacklistOldYogiRepository.findByCreatedById(createdBy.getId())
                .orElseThrow(() -> new ResourceNotFoundException("No Blacklist Old Yogi Found With Given User Id %d"
                        .formatted(id)));
        return blacklistOldYogiList.stream()
                .map(blacklistOldYogiMapper::toDto)
                .collect(Collectors.toList());
    }

    @Transactional
    public BlacklistOldYogiResponseDTO findBlacklistOldYogiByOldYogiIdAndCreatedById(Long oldYogiId, Long createdById) {
        return blacklistOldYogiRepository.findByYogiIdAndCreatedById(oldYogiId, createdById)
                .map(blacklistOldYogiMapper::toDto)
                .orElseThrow(() -> new ResourceNotFoundException("Blacklist Old Yogi With Old Yogi Id %d and Created By Id %d Not Found"
                        .formatted(oldYogiId, createdById)));
    }

    @Transactional
    public BlacklistOldYogiResponseDTO createBlacklistOldYogi(BlacklistOldYogiCreateDTO blacklistOldYogiCreateDTO) {
        OldYogi oldYogi = oldYogiRepository.findById(blacklistOldYogiCreateDTO.getOldYogiId())
                .orElseThrow(() -> new ResourceNotFoundException("Old Yogi With Id %d Not Found"
                        .formatted(blacklistOldYogiCreateDTO.getOldYogiId())));
        oldYogi.setBlacklisted(true);
        oldYogi.setBlacklistedAt(LocalDate.now());
        BlacklistOldYogi blacklistOldYogi = blacklistOldYogiMapper.toEntity(blacklistOldYogiCreateDTO);
        blacklistOldYogi.setOldYogi(oldYogi);
        try {
            blacklistOldYogi = blacklistOldYogiRepository.save(blacklistOldYogi);
        } catch (Exception e) {
            throw new ResourceAlreadyExistsException("Old yogi with Id %d is already being blacklisted by the user."
                    .formatted(oldYogi.getId()));
        }
        entityManager.refresh(blacklistOldYogi);
        return blacklistOldYogiMapper.toDto(blacklistOldYogi);
    }

    @Transactional
    public BlacklistOldYogiResponseDTO updateBlacklistOldYogi(Long id, BlacklistOldYogiUpdateDTO blacklistOldYogiUpdateDTO) {
        BlacklistOldYogi blacklistOldYogi = blacklistOldYogiRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Blacklist Old Yogi With Id %d Not Found".formatted(id)));
        OldYogi oldYogi = oldYogiRepository.findById(blacklistOldYogiUpdateDTO.getOldYogiId())
                .orElseThrow(() -> new ResourceNotFoundException("Old Yogi With Id %d Not Found"
                        .formatted(blacklistOldYogiUpdateDTO.getOldYogiId())));
        blacklistOldYogi.setOldYogi(oldYogi);
        blacklistOldYogi.setRemark(blacklistOldYogiUpdateDTO.getRemark());
        BlacklistOldYogi updatedBlacklistOldYogi = blacklistOldYogiRepository.save(blacklistOldYogi);
        blacklistOldYogiRepository.flush();
        entityManager.refresh(updatedBlacklistOldYogi);
        return blacklistOldYogiMapper.toDto(updatedBlacklistOldYogi);
    }

    @Transactional
    public boolean deleteBlacklistOldYogiById(Long id) {
        BlacklistOldYogi blacklistOldYogi = blacklistOldYogiRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Blacklist Old Yogi with Id %d Not Found".formatted(id)));
        Long oldYogiId = blacklistOldYogi.getOldYogi().getId();
        blacklistOldYogiRepository.delete(blacklistOldYogi);
        blacklistOldYogiRepository.flush();
        boolean hasOtherBlacklistEntries = blacklistOldYogiRepository.existsByOldYogiId(oldYogiId);
        if (!hasOtherBlacklistEntries) {
            oldYogiRepository.findById(oldYogiId).ifPresent(oldYogi -> oldYogi.setBlacklisted(false));
        }
        return true;
    }

    @Transactional
    public boolean deleteBlacklistOldYogiByOldYogiIdAndCreatedById(Long oldYogiId, Long createdById) {
        BlacklistOldYogi blacklistOldYogi = blacklistOldYogiRepository.findByYogiIdAndCreatedById(oldYogiId, createdById)
                .orElseThrow(() -> new ResourceNotFoundException("Blacklist Old Yogi With Old Yogi Id %d and Created By Id %d Not Found"
                        .formatted(oldYogiId, createdById)));
        blacklistOldYogiRepository.delete(blacklistOldYogi);
        blacklistOldYogiRepository.flush();
        boolean hasOtherBlacklistEntries = blacklistOldYogiRepository.existsByOldYogiId(oldYogiId);
        if (!hasOtherBlacklistEntries) {
            oldYogiRepository.findById(oldYogiId).ifPresent(oldYogi -> oldYogi.setBlacklisted(false));
        }
        return true;
    }

    @Transactional
    public Page<BlacklistOldYogiResponseDTO> findBlacklistOldYogisByFilter(SearchRequest request, Pageable pageable) {
        Page<BlacklistOldYogi> blacklistOldYogis = searchService.search(request, blacklistOldYogiRepository, pageable);
        return blacklistOldYogis.map(blacklistOldYogiMapper::toDto);
    }

}
