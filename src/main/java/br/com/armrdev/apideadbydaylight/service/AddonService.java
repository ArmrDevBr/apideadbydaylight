package br.com.armrdev.apideadbydaylight.service;

import br.com.armrdev.apideadbydaylight.dto.AddonRequestDTO;
import br.com.armrdev.apideadbydaylight.dto.AddonResponseDTO;
import br.com.armrdev.apideadbydaylight.entity.Addon;
import br.com.armrdev.apideadbydaylight.entity.Killer;
import br.com.armrdev.apideadbydaylight.entity.enums.ItemType;
import br.com.armrdev.apideadbydaylight.entity.enums.Rarity;
import br.com.armrdev.apideadbydaylight.exception.ResourceNotFoundException;
import br.com.armrdev.apideadbydaylight.repository.AddonRepository;
import br.com.armrdev.apideadbydaylight.repository.KillerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AddonService {

    private final AddonRepository addonRepository;
    private final KillerRepository killerRepository;

    @Transactional(readOnly = true)
    public List<AddonResponseDTO> findAll() {
        return addonRepository.findAll()
                .stream()
                .map(AddonResponseDTO::fromEntity)
                .toList();
    }

    @Transactional(readOnly = true)
    public AddonResponseDTO findById(UUID id) {
        Addon entity = addonRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Add-on não encontrado com ID: " + id));
        return AddonResponseDTO.fromEntity(entity);
    }

    @Transactional
    public AddonResponseDTO create(AddonRequestDTO dto) {
        Addon entity = new Addon();
        copyDtoToEntity(dto, entity);
        Addon saved = addonRepository.save(entity);
        return AddonResponseDTO.fromEntity(saved);
    }

    @Transactional
    public AddonResponseDTO update(UUID id, AddonRequestDTO dto) {
        Addon entity = addonRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Add-on não encontrado com ID: " + id));
        copyDtoToEntity(dto, entity);
        Addon updated = addonRepository.save(entity);
        return AddonResponseDTO.fromEntity(updated);
    }

    @Transactional
    public void delete(UUID id) {
        if (!addonRepository.existsById(id)) {
            throw new ResourceNotFoundException("Add-on não encontrado com ID: " + id);
        }
        addonRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public List<AddonResponseDTO> findByKillerId(UUID killerId) {
        return addonRepository.findByKillerId(killerId)
                .stream()
                .map(AddonResponseDTO::fromEntity)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<AddonResponseDTO> findByTargetItemType(ItemType targetItemType) {
        return addonRepository.findByTargetItemType(targetItemType)
                .stream()
                .map(AddonResponseDTO::fromEntity)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<AddonResponseDTO> findByRarity(Rarity rarity) {
        return addonRepository.findByRarity(rarity)
                .stream()
                .map(AddonResponseDTO::fromEntity)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<AddonResponseDTO> findByName(String name) {
        return addonRepository.findByNameContainingIgnoreCase(name)
                .stream()
                .map(AddonResponseDTO::fromEntity)
                .toList();
    }

    private void copyDtoToEntity(AddonRequestDTO dto, Addon entity) {
        entity.setName(dto.name());
        entity.setRarity(dto.rarity());
        entity.setDescription(dto.description());
        entity.setIconUrl(dto.iconUrl());
        entity.setTargetItemType(dto.targetItemType());

        if (dto.killerId() != null) {
            Killer killer = killerRepository.findById(dto.killerId())
                    .orElseThrow(() -> new ResourceNotFoundException("Assassino não encontrado com ID: " + dto.killerId()));
            entity.setKiller(killer);
        } else {
            entity.setKiller(null);
        }
    }
}
