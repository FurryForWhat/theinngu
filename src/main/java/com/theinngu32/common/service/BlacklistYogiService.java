package com.theinngu32.common.service;

import com.theinngu32.common.dto.SearchRequest;
import com.theinngu32.common.dto.blacklist_yogis.BlacklistYogiCreateDto;
import com.theinngu32.common.dto.blacklist_yogis.BlacklistYogiResponseDTO;
import com.theinngu32.common.dto.blacklist_yogis.BlacklistYogiUpdateDto;
import com.theinngu32.common.entity.User;
import com.theinngu32.common.entity.BlacklistYogi;
import com.theinngu32.common.entity.Yogi;
import com.theinngu32.common.exception.ResourceAlreadyExistsException;
import com.theinngu32.common.exception.ResourceNotFoundException;
import com.theinngu32.common.mapper.BlacklistYogiMapper;
import com.theinngu32.common.repository.BlacklistYogiRepository;
import com.theinngu32.common.repository.UserRepository;
import com.theinngu32.common.repository.YogiRepository;
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
public class BlacklistYogiService {

    private final BlacklistYogiMapper blacklistYogiMapper;
    private final BlacklistYogiRepository blacklistYogiRepository;
    private final UserRepository userRepository;
    private final YogiRepository yogiRepository;
    private final GenericSearchService searchService;
    private final EntityManager entityManager;

    @Transactional
    public List<BlacklistYogiResponseDTO> getAllBlacklistYogis() {
        return blacklistYogiRepository.findAll().stream()
                .map(blacklistYogiMapper::toDto)
                .collect(Collectors.toList());
    }

    @Transactional
    public BlacklistYogiResponseDTO findBlacklistYogiById(Long id) {
        BlacklistYogi blacklistYogi = blacklistYogiRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Blacklist Yogi With Id %d Not Found".formatted(id)));
        return blacklistYogiMapper.toDto(blacklistYogi);
    }

    @Transactional
    public List<BlacklistYogiResponseDTO> findBlacklistYogiByYogiId(Long id) {
        Yogi yogi = yogiRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Yogi With Id %d Not Found".formatted(id)));
        List<BlacklistYogi> blacklistYogiList = blacklistYogiRepository.findByYogiId(yogi.getId())
                .orElseThrow(() -> new ResourceNotFoundException("No Blacklist Yogi found for the given Yogi with Id %d"
                        .formatted(id)));

        return blacklistYogiList.stream()
                .map(blacklistYogiMapper::toDto)
                .collect(Collectors.toList());
    }

    @Transactional
    public List<BlacklistYogiResponseDTO> findBlacklistYogiByCreatedById(Long id) {
        User createdBy = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User With Id %d Not Found".formatted(id)));
        List<BlacklistYogi> blacklistYogis = blacklistYogiRepository.findByCreatedById(createdBy.getId())
                .orElseThrow(() -> new ResourceNotFoundException("No Blacklist Yogi found for the given User with Id %d"
                        .formatted(id)));
        return blacklistYogis.stream()
                .map(blacklistYogiMapper::toDto)
                .collect(Collectors.toList());
    }

    @Transactional
    public BlacklistYogiResponseDTO findBlacklistYogiByYogiIdAndCreatedById(Long yogiId, Long createdById) {
        return blacklistYogiRepository.findByYogiIdAndCreatedById(yogiId, createdById)
                .map(blacklistYogiMapper::toDto)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Blacklist Yogi With Yogi Id %d and Created By Id %d Not Found".formatted(yogiId, createdById))
                );
    }

    @Transactional
    public BlacklistYogiResponseDTO createBlacklistYogi(BlacklistYogiCreateDto blacklistYogiCreateDto) {
        Yogi yogi = yogiRepository.findById(blacklistYogiCreateDto.getYogiId())
                .orElseThrow(() -> new ResourceNotFoundException("Yogi With Id %d Not Found"
                        .formatted(blacklistYogiCreateDto.getYogiId())));
        yogi.setBlacklisted(true);
        yogi.setBlacklistedAt(LocalDate.now());
        BlacklistYogi blacklistYogi = blacklistYogiMapper.toEntity(blacklistYogiCreateDto);
        blacklistYogi.setYogi(yogi);
        try {
            blacklistYogi = blacklistYogiRepository.save(blacklistYogi);
        } catch (Exception e) {
            throw new ResourceAlreadyExistsException("Yogi with Id %d is already being blacklisted by the user"
                    .formatted(yogi.getId()));
        }
        entityManager.refresh(blacklistYogi);
        return blacklistYogiMapper.toDto(blacklistYogi);
    }

    @Transactional
    public BlacklistYogiResponseDTO updateBlacklistYogi(Long id, BlacklistYogiUpdateDto blacklistYogiUpdateDto) {
        BlacklistYogi blacklistYogi = blacklistYogiRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Blacklist Yogi With Id %d Not Found".formatted(id)));
        Yogi yogi = yogiRepository.findById(blacklistYogiUpdateDto.getYogiId())
                .orElseThrow(() -> new ResourceNotFoundException("Yogi With Id %d Not Found"
                        .formatted(blacklistYogiUpdateDto.getYogiId())));
        blacklistYogi.setYogi(yogi);
        blacklistYogi.setRemark(blacklistYogiUpdateDto.getRemark());
        BlacklistYogi updatedBlacklistYogi = blacklistYogiRepository.save(blacklistYogi);
        blacklistYogiRepository.flush();
        entityManager.refresh(updatedBlacklistYogi);
        return blacklistYogiMapper.toDto(updatedBlacklistYogi);
    }

    @Transactional
    public boolean deleteBlacklistYogiById(Long id) {
        BlacklistYogi blacklistYogi = blacklistYogiRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Blacklist Yogi with Id %d Not Found".formatted(id)));
        Long yogiId = blacklistYogi.getYogi().getId();
        blacklistYogiRepository.delete(blacklistYogi);
        blacklistYogiRepository.flush();
        boolean hasOtherBlacklistEntries = blacklistYogiRepository.existsByYogiId(yogiId);
        if (!hasOtherBlacklistEntries) {
            yogiRepository.findById(yogiId).ifPresent(yogi -> yogi.setBlacklisted(false));
        }
        return true;
    }

    @Transactional
    public boolean deleteBlacklistYogiByYogiIdAndCreatedById(Long yogiId, Long createdById) {
        BlacklistYogi blacklistYogi = blacklistYogiRepository.findByYogiIdAndCreatedById(yogiId, createdById)
                .orElseThrow(() -> new ResourceNotFoundException("Blacklist Yogi with Yogi Id %d and Created By Id %d Not Found"
                        .formatted(yogiId, createdById)));
        blacklistYogiRepository.delete(blacklistYogi);
        blacklistYogiRepository.flush();
        boolean hasOtherBlacklistEntries = blacklistYogiRepository.existsByYogiId(yogiId);
        if (!hasOtherBlacklistEntries) {
            yogiRepository.findById(yogiId).ifPresent(yogi -> yogi.setBlacklisted(false));
        }
        return true;
    }

    @Transactional
    public Page<BlacklistYogiResponseDTO> findBlacklistYogiByFilter(SearchRequest request, Pageable pageable) {
        Page<BlacklistYogi> blacklistYogis = searchService.search(request, blacklistYogiRepository, pageable);
        return blacklistYogis.map(blacklistYogiMapper::toDto);
    }

}
