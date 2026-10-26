package com.theinngu32.common.service;

import com.theinngu32.common.dto.submissionary_group.SubMissionaryGroupCreateDTO;
import com.theinngu32.common.dto.submissionary_group.SubMissionaryGroupDTO;
import com.theinngu32.common.dto.submissionary_group.SubMissionaryGroupUpdateDTO;
import com.theinngu32.common.entity.MissionaryGroup;
import com.theinngu32.common.entity.SubMissionaryGroup;
import com.theinngu32.common.exception.ResourceNotFoundException;
import com.theinngu32.common.mapper.SubMissionaryGroupMapper;
import com.theinngu32.common.repository.MissionaryGroupRepository;
import com.theinngu32.common.repository.SubMissionaryGroupRepository;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SubMissionaryGroupService {

    private final SubMissionaryGroupRepository subMissionaryGroupRepository;
    private final SubMissionaryGroupMapper subMissionaryGroupMapper;
    private final EntityManager entityManager;
    private final MissionaryGroupRepository missionaryGroupRepository;

    @Transactional
    public List<SubMissionaryGroupDTO> getAllSubMissionaryGroup() {
        List<SubMissionaryGroup> entities = subMissionaryGroupRepository.findAll();
        return entities.stream()
                .map(subMissionaryGroupMapper::toDto)
                .collect(Collectors.toList());
    }

    @Transactional
    public SubMissionaryGroupDTO createSubMissionaryGroup(SubMissionaryGroupCreateDTO subMissionaryGroupCreateDTO) {
        SubMissionaryGroup subMissionaryGroup = subMissionaryGroupMapper.toEntity(subMissionaryGroupCreateDTO);
        SubMissionaryGroup savedSubMissionaryGroup = subMissionaryGroupRepository.save(subMissionaryGroup);
        entityManager.refresh(savedSubMissionaryGroup);
        return subMissionaryGroupMapper.toDto(savedSubMissionaryGroup);
    }

    @Transactional
    public SubMissionaryGroupDTO updateSubMissionaryGroup(Long id, SubMissionaryGroupUpdateDTO subMissionaryGroupUpdateDTO){
        SubMissionaryGroup subMissionaryGroup=subMissionaryGroupRepository.findById(id)
                .orElseThrow(()-> new ResourceNotFoundException("SubMissionary group with Id %d not found".formatted(id)));
        subMissionaryGroup.setName(subMissionaryGroupUpdateDTO.getName());
        subMissionaryGroup.setCode(subMissionaryGroupUpdateDTO.getCode());
        subMissionaryGroup.setDescription(subMissionaryGroupUpdateDTO.getDescription());
        subMissionaryGroup.setStatus(subMissionaryGroupUpdateDTO.getStatus());
        MissionaryGroup missionaryGroup = missionaryGroupRepository.findById(subMissionaryGroupUpdateDTO.getMissionaryGroupId())
                        .orElseThrow(()-> new ResourceNotFoundException("Missionary group with Id %d not found".formatted(subMissionaryGroupUpdateDTO.getMissionaryGroupId())));
        subMissionaryGroup.setMissionaryGroup(missionaryGroup);
        SubMissionaryGroup updatedSubMissionaryGroup = subMissionaryGroupRepository.save(subMissionaryGroup);
        subMissionaryGroupRepository.flush();
        entityManager.refresh(updatedSubMissionaryGroup);
        return subMissionaryGroupMapper.toDto(updatedSubMissionaryGroup);
    }

    @Transactional
    public boolean deleteSubMissionaryGroupById(Long id){
        SubMissionaryGroup subMissionaryGroup = subMissionaryGroupRepository.findById(id)
                .orElseThrow(()-> new ResourceNotFoundException("SubMissionary group with Id %d not found".formatted(id)));
        subMissionaryGroupRepository.delete(subMissionaryGroup);
        return true;
    }

    @Transactional
    public SubMissionaryGroupDTO findSubMissionaryGroupById(Long id) {
        SubMissionaryGroup subMissionaryGroup = subMissionaryGroupRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("SubMissionaryGroup not found with ID: " + id));
        return subMissionaryGroupMapper.toDto(subMissionaryGroup);
    }

    @Transactional(readOnly = true)
    public List<SubMissionaryGroupDTO> findSubMissionaryGroupByMissionaryGroupId(Long id) {
        MissionaryGroup missionaryGroup = missionaryGroupRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("MissionaryGroup not found with ID: " + id));
        List<SubMissionaryGroup> subMissionaryGroup = subMissionaryGroupRepository.findByMissionaryGroupId(id)
                .orElseThrow(() -> new ResourceNotFoundException("SubMissionaryGroup not found with ID: " + id));
        return subMissionaryGroup.stream()
                .map(subMissionaryGroupMapper::toDto)
                .collect(Collectors.toList());
    }
}


