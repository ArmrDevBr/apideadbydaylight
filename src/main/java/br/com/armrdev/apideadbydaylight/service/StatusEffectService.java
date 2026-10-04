package br.com.armrdev.apideadbydaylight.service;

import br.com.armrdev.apideadbydaylight.dto.StatusEffectRequestDTO;
import br.com.armrdev.apideadbydaylight.dto.StatusEffectResponseDTO;
import br.com.armrdev.apideadbydaylight.entity.StatusEffect;
import br.com.armrdev.apideadbydaylight.entity.enums.Role;
import br.com.armrdev.apideadbydaylight.entity.enums.StatusType;
import br.com.armrdev.apideadbydaylight.exception.ResourceNotFoundException;
import br.com.armrdev.apideadbydaylight.repository.StatusEffectRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class StatusEffectService {

    private final StatusEffectRepository repository;

    @Transactional(readOnly = true)
    public List<StatusEffectResponseDTO> findAll() {
        return repository.findAll()
                .stream()
                .map(StatusEffectResponseDTO::fromEntity)
                .toList();
    }

    @Transactional(readOnly = true)
    public StatusEffectResponseDTO findById(UUID id) {
        StatusEffect entity = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Efeito de status não encontrado com ID: " + id));
        return StatusEffectResponseDTO.fromEntity(entity);
    }

    @Transactional
    public StatusEffectResponseDTO create(StatusEffectRequestDTO dto) {
        StatusEffect entity = new StatusEffect();
        copyDtoToEntity(dto, entity);
        StatusEffect saved = repository.save(entity);
        return StatusEffectResponseDTO.fromEntity(saved);
    }

    @Transactional
    public StatusEffectResponseDTO update(UUID id, StatusEffectRequestDTO dto) {
        StatusEffect entity = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Efeito de status não encontrado com ID: " + id));
        copyDtoToEntity(dto, entity);
        StatusEffect updated = repository.save(entity);
        return StatusEffectResponseDTO.fromEntity(updated);
    }

    @Transactional
    public void delete(UUID id) {
        if (!repository.existsById(id)) {
            throw new ResourceNotFoundException("Efeito de status não encontrado com ID: " + id);
        }
        repository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public List<StatusEffectResponseDTO> findByType(StatusType type) {
        return repository.findByType(type)
                .stream()
                .map(StatusEffectResponseDTO::fromEntity)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<StatusEffectResponseDTO> findByAffectedRole(Role role) {
        return repository.findByAffectedRole(role)
                .stream()
                .map(StatusEffectResponseDTO::fromEntity)
                .toList();
    }

    private void copyDtoToEntity(StatusEffectRequestDTO dto, StatusEffect entity) {
        entity.setName(dto.name());
        entity.setType(dto.type());
        entity.setAffectedRole(dto.affectedRole());
        entity.setDescription(dto.description());
        entity.setIconUrl(dto.iconUrl());
    }
}
