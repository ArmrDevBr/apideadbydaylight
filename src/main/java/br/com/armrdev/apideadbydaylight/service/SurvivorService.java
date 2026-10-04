package br.com.armrdev.apideadbydaylight.service;

import br.com.armrdev.apideadbydaylight.dto.SurvivorRequestDTO;
import br.com.armrdev.apideadbydaylight.dto.SurvivorResponseDTO;
import br.com.armrdev.apideadbydaylight.entity.Dlc;
import br.com.armrdev.apideadbydaylight.entity.Survivor;
import br.com.armrdev.apideadbydaylight.exception.ResourceNotFoundException;
import br.com.armrdev.apideadbydaylight.repository.DlcRepository;
import br.com.armrdev.apideadbydaylight.repository.SurvivorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class SurvivorService {

    private final SurvivorRepository survivorRepository;
    private final DlcRepository dlcRepository;

    @Transactional(readOnly = true)
    public List<SurvivorResponseDTO> findAll() {
        return survivorRepository.findAll()
                .stream()
                .map(SurvivorResponseDTO::fromEntity)
                .toList();
    }

    @Transactional(readOnly = true)
    public SurvivorResponseDTO findById(UUID id) {
        Survivor entity = survivorRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Sobrevivente não encontrado com ID: " + id));
        return SurvivorResponseDTO.fromEntity(entity);
    }

    @Transactional
    public SurvivorResponseDTO create(SurvivorRequestDTO dto) {
        Survivor entity = new Survivor();
        copyDtoToEntity(dto, entity);
        Survivor saved = survivorRepository.save(entity);
        return SurvivorResponseDTO.fromEntity(saved);
    }

    @Transactional
    public SurvivorResponseDTO update(UUID id, SurvivorRequestDTO dto) {
        Survivor entity = survivorRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Sobrevivente não encontrado com ID: " + id));
        copyDtoToEntity(dto, entity);
        Survivor updated = survivorRepository.save(entity);
        return SurvivorResponseDTO.fromEntity(updated);
    }

    @Transactional
    public void delete(UUID id) {
        if (!survivorRepository.existsById(id)) {
            throw new ResourceNotFoundException("Sobrevivente não encontrado com ID: " + id);
        }
        survivorRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public List<SurvivorResponseDTO> findByName(String name) {
        return survivorRepository.findByNameContainingIgnoreCase(name)
                .stream()
                .map(SurvivorResponseDTO::fromEntity)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<SurvivorResponseDTO> findByLicensed(Boolean licensed) {
        return survivorRepository.findByLicensed(licensed)
                .stream()
                .map(SurvivorResponseDTO::fromEntity)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<SurvivorResponseDTO> findByDlcId(UUID dlcId) {
        return survivorRepository.findByDlcId(dlcId)
                .stream()
                .map(SurvivorResponseDTO::fromEntity)
                .toList();
    }

    private void copyDtoToEntity(SurvivorRequestDTO dto, Survivor entity) {
        entity.setName(dto.name());
        entity.setGender(dto.gender());
        entity.setBirthday(dto.birthday());
        entity.setLore(dto.lore());
        entity.setHeight(dto.height());
        entity.setWeight(dto.weight());
        entity.setLicensed(dto.licensed());
        entity.setImageUrl(dto.imageUrl());

        if (dto.dlcId() != null) {
            Dlc dlc = dlcRepository.findById(dto.dlcId())
                    .orElseThrow(() -> new ResourceNotFoundException("DLC não encontrada com ID: " + dto.dlcId()));
            entity.setDlc(dlc);
        } else {
            entity.setDlc(null);
        }
    }
}
