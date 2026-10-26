package com.theinngu32.common.service;

import com.theinngu32.common.dto.SearchRequest;
import com.theinngu32.common.dto.missionary_group.MissionaryGroupCreateDTO;
import com.theinngu32.common.dto.missionary_group.MissionaryGroupDTO;
import com.theinngu32.common.dto.missionary_group.MissionaryGroupUpdateDTO;
import com.theinngu32.common.entity.MissionaryGroup;
import com.theinngu32.common.exception.BadRequestException;
import com.theinngu32.common.exception.ResourceNotFoundException;
import com.theinngu32.common.mapper.MissionaryGroupMapper;
import com.theinngu32.common.repository.MissionaryGroupRepository;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@RequiredArgsConstructor
public class MissionaryGroupService {

    private final MissionaryGroupRepository missionaryGroupRepository;
    private final MissionaryGroupMapper missionaryGroupMapper;
    private final GenericSearchService searchService;
    private final EntityManager entityManager;

    @Transactional
    public List<MissionaryGroupDTO> getAll() {
        List<MissionaryGroup> missionaryGroups = missionaryGroupRepository.findAll();
        return missionaryGroups.stream()
                .map(missionaryGroupMapper::toDto)
                .toList();
    }

    @Transactional
    public MissionaryGroupDTO getById(Long id) {
        MissionaryGroup missionaryGroup = missionaryGroupRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Missionary group with ID %d not found.".formatted(id)));
        return missionaryGroupMapper.toDto(missionaryGroup) ;
    }

    @Transactional
    public MissionaryGroupDTO create(MissionaryGroupCreateDTO missionaryGroupCreateDTO) {
        MissionaryGroup existingGroup = missionaryGroupRepository.findByName(missionaryGroupCreateDTO.getName());

        if (existingGroup != null) {
            throw new BadRequestException("Missionary group already exists!");
        }
        MissionaryGroup missionaryGroup = missionaryGroupMapper.toEntity(missionaryGroupCreateDTO);
        MissionaryGroup savedMissionaryGroup = missionaryGroupRepository.save(missionaryGroup);
        entityManager.refresh(savedMissionaryGroup);
        return missionaryGroupMapper.toDto(savedMissionaryGroup);
    }

    @Transactional
    public MissionaryGroupDTO update(Long id, MissionaryGroupUpdateDTO missionaryGroupUpdateDTO) {
        MissionaryGroup missionaryGroup = missionaryGroupRepository.findById(id)
                        .orElseThrow(() -> new ResourceNotFoundException("Missionary group with ID %d not found.".formatted(id)));

        missionaryGroup.setName(missionaryGroupUpdateDTO.getName());
        missionaryGroup.setCode(missionaryGroupUpdateDTO.getCode());
        missionaryGroup.setStatus(missionaryGroupUpdateDTO.getStatus());
        missionaryGroup.setUnderControl(missionaryGroupUpdateDTO.isUnderControl());
        MissionaryGroup updatedMissionaryGroup = missionaryGroupRepository.save(missionaryGroup);
        missionaryGroupRepository.flush();
        entityManager.refresh(updatedMissionaryGroup);
        return missionaryGroupMapper.toDto(updatedMissionaryGroup);
    }

    @Transactional
    public boolean delete(Long id) {
        MissionaryGroup missionaryGroup = missionaryGroupRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User with ID %d not found.".formatted(id)));
        missionaryGroupRepository.delete(missionaryGroup);
        return true;
    }

    @Transactional
    public Page<MissionaryGroupDTO> findMissionaryGroupByFilter(SearchRequest request, Pageable pageable) {
        Page<MissionaryGroup> missionaryGroups = searchService.search(request, missionaryGroupRepository, pageable);
        return missionaryGroups.map(missionaryGroupMapper::toDto);
    }
}
