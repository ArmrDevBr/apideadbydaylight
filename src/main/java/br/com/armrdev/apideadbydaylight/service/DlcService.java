package br.com.armrdev.apideadbydaylight.service;

import br.com.armrdev.apideadbydaylight.dto.DlcRequestDTO;
import br.com.armrdev.apideadbydaylight.dto.DlcResponseDTO;
import br.com.armrdev.apideadbydaylight.entity.Dlc;
import br.com.armrdev.apideadbydaylight.exception.ResourceNotFoundException;
import br.com.armrdev.apideadbydaylight.repository.DlcRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class DlcService {

    private final DlcRepository repository;

    @Transactional(readOnly = true)
    public List<DlcResponseDTO> findAll() {
        return repository.findAll()
                .stream()
                .map(DlcResponseDTO::fromEntity)
                .toList();
    }

    @Transactional(readOnly = true)
    public DlcResponseDTO findById(UUID id) {
        Dlc entity = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("DLC não encontrada com ID: " + id));
        return DlcResponseDTO.fromEntity(entity);
    }

    @Transactional
    public DlcResponseDTO create(DlcRequestDTO dto) {
        Dlc entity = new Dlc();
        copyDtoToEntity(dto, entity);
        Dlc saved = repository.save(entity);
        return DlcResponseDTO.fromEntity(saved);
    }

    @Transactional
    public DlcResponseDTO update(UUID id, DlcRequestDTO dto) {
        Dlc entity = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("DLC não encontrada com ID: " + id));
        copyDtoToEntity(dto, entity);
        Dlc updated = repository.save(entity);
        return DlcResponseDTO.fromEntity(updated);
    }

    @Transactional
    public void delete(UUID id) {
        if (!repository.existsById(id)) {
            throw new ResourceNotFoundException("DLC não encontrada com ID: " + id);
        }
        repository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public List<DlcResponseDTO> findByName(String name) {
        return repository.findByNameContainingIgnoreCase(name)
                .stream()
                .map(DlcResponseDTO::fromEntity)
                .toList();
    }

    private void copyDtoToEntity(DlcRequestDTO dto, Dlc entity) {
        entity.setName(dto.name());
        entity.setDescription(dto.description());
        entity.setImageUrl(dto.imageUrl());
        entity.setReleaseDate(dto.releaseDate());
    }
}
