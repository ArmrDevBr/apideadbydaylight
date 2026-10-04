package br.com.armrdev.apideadbydaylight.service;

import br.com.armrdev.apideadbydaylight.dto.OfferingRequestDTO;
import br.com.armrdev.apideadbydaylight.dto.OfferingResponseDTO;
import br.com.armrdev.apideadbydaylight.entity.Dlc;
import br.com.armrdev.apideadbydaylight.entity.Offering;
import br.com.armrdev.apideadbydaylight.entity.enums.Rarity;
import br.com.armrdev.apideadbydaylight.entity.enums.Role;
import br.com.armrdev.apideadbydaylight.exception.ResourceNotFoundException;
import br.com.armrdev.apideadbydaylight.repository.DlcRepository;
import br.com.armrdev.apideadbydaylight.repository.OfferingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OfferingService {

    private final OfferingRepository offeringRepository;
    private final DlcRepository dlcRepository;

    @Transactional(readOnly = true)
    public List<OfferingResponseDTO> findAll() {
        return offeringRepository.findAll()
                .stream()
                .map(OfferingResponseDTO::fromEntity)
                .toList();
    }

    @Transactional(readOnly = true)
    public OfferingResponseDTO findById(UUID id) {
        Offering entity = offeringRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Oferenda não encontrada com ID: " + id));
        return OfferingResponseDTO.fromEntity(entity);
    }

    @Transactional
    public OfferingResponseDTO create(OfferingRequestDTO dto) {
        Offering entity = new Offering();
        copyDtoToEntity(dto, entity);
        Offering saved = offeringRepository.save(entity);
        return OfferingResponseDTO.fromEntity(saved);
    }

    @Transactional
    public OfferingResponseDTO update(UUID id, OfferingRequestDTO dto) {
        Offering entity = offeringRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Oferenda não encontrada com ID: " + id));
        copyDtoToEntity(dto, entity);
        Offering updated = offeringRepository.save(entity);
        return OfferingResponseDTO.fromEntity(updated);
    }

    @Transactional
    public void delete(UUID id) {
        if (!offeringRepository.existsById(id)) {
            throw new ResourceNotFoundException("Oferenda não encontrada com ID: " + id);
        }
        offeringRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public List<OfferingResponseDTO> findByRole(Role role) {
        return offeringRepository.findByRole(role)
                .stream()
                .map(OfferingResponseDTO::fromEntity)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<OfferingResponseDTO> findByRarity(Rarity rarity) {
        return offeringRepository.findByRarity(rarity)
                .stream()
                .map(OfferingResponseDTO::fromEntity)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<OfferingResponseDTO> findBySecret(Boolean secret) {
        return offeringRepository.findBySecret(secret)
                .stream()
                .map(OfferingResponseDTO::fromEntity)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<OfferingResponseDTO> findByDlcId(UUID dlcId) {
        return offeringRepository.findByDlcId(dlcId)
                .stream()
                .map(OfferingResponseDTO::fromEntity)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<OfferingResponseDTO> findByName(String name) {
        return offeringRepository.findByNameContainingIgnoreCase(name)
                .stream()
                .map(OfferingResponseDTO::fromEntity)
                .toList();
    }

    private void copyDtoToEntity(OfferingRequestDTO dto, Offering entity) {
        entity.setName(dto.name());
        entity.setRarity(dto.rarity());
        entity.setRole(dto.role());
        entity.setDescription(dto.description());
        entity.setSecret(dto.secret() != null ? dto.secret() : false);
        entity.setIconUrl(dto.iconUrl());

        if (dto.dlcId() != null) {
            Dlc dlc = dlcRepository.findById(dto.dlcId())
                    .orElseThrow(() -> new ResourceNotFoundException("DLC não encontrada com ID: " + dto.dlcId()));
            entity.setDlc(dlc);
        } else {
            entity.setDlc(null);
        }
    }
}
