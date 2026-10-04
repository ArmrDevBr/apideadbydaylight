package br.com.armrdev.apideadbydaylight.service;

import br.com.armrdev.apideadbydaylight.dto.PerkRequestDTO;
import br.com.armrdev.apideadbydaylight.dto.PerkResponseDTO;
import br.com.armrdev.apideadbydaylight.entity.Killer;
import br.com.armrdev.apideadbydaylight.entity.Perk;
import br.com.armrdev.apideadbydaylight.entity.StatusEffect;
import br.com.armrdev.apideadbydaylight.entity.Survivor;
import br.com.armrdev.apideadbydaylight.entity.enums.Role;
import br.com.armrdev.apideadbydaylight.exception.ResourceNotFoundException;
import br.com.armrdev.apideadbydaylight.repository.KillerRepository;
import br.com.armrdev.apideadbydaylight.repository.PerkRepository;
import br.com.armrdev.apideadbydaylight.repository.StatusEffectRepository;
import br.com.armrdev.apideadbydaylight.repository.SurvivorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PerkService {

    private final PerkRepository perkRepository;
    private final SurvivorRepository survivorRepository;
    private final KillerRepository killerRepository;
    private final StatusEffectRepository statusEffectRepository;

    @Transactional(readOnly = true)
    public List<PerkResponseDTO> findAll() {
        return perkRepository.findAll()
                .stream()
                .map(PerkResponseDTO::fromEntity)
                .toList();
    }

    @Transactional(readOnly = true)
    public PerkResponseDTO findById(UUID id) {
        Perk entity = perkRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Perk não encontrada com ID: " + id));
        return PerkResponseDTO.fromEntity(entity);
    }

    @Transactional
    public PerkResponseDTO create(PerkRequestDTO dto) {
        Perk entity = new Perk();
        copyDtoToEntity(dto, entity);
        Perk saved = perkRepository.save(entity);
        return PerkResponseDTO.fromEntity(saved);
    }

    @Transactional
    public PerkResponseDTO update(UUID id, PerkRequestDTO dto) {
        Perk entity = perkRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Perk não encontrada com ID: " + id));
        copyDtoToEntity(dto, entity);
        Perk updated = perkRepository.save(entity);
        return PerkResponseDTO.fromEntity(updated);
    }

    @Transactional
    public void delete(UUID id) {
        if (!perkRepository.existsById(id)) {
            throw new ResourceNotFoundException("Perk não encontrada com ID: " + id);
        }
        perkRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public List<PerkResponseDTO> findByRole(Role role) {
        return perkRepository.findByRole(role)
                .stream()
                .map(PerkResponseDTO::fromEntity)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<PerkResponseDTO> findBySurvivorId(UUID survivorId) {
        return perkRepository.findBySurvivorId(survivorId)
                .stream()
                .map(PerkResponseDTO::fromEntity)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<PerkResponseDTO> findByKillerId(UUID killerId) {
        return perkRepository.findByKillerId(killerId)
                .stream()
                .map(PerkResponseDTO::fromEntity)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<PerkResponseDTO> findGeneralPerks(Role role) {
        if (role == Role.SURVIVOR) {
            return perkRepository.findByRoleAndSurvivorIsNull(Role.SURVIVOR)
                    .stream()
                    .map(PerkResponseDTO::fromEntity)
                    .toList();
        } else {
            return perkRepository.findByRoleAndKillerIsNull(Role.KILLER)
                    .stream()
                    .map(PerkResponseDTO::fromEntity)
                    .toList();
        }
    }

    @Transactional(readOnly = true)
    public List<PerkResponseDTO> findByName(String name) {
        return perkRepository.findByNameContainingIgnoreCase(name)
                .stream()
                .map(PerkResponseDTO::fromEntity)
                .toList();
    }

    private void copyDtoToEntity(PerkRequestDTO dto, Perk entity) {
        entity.setName(dto.name());
        entity.setRole(dto.role());
        entity.setDescription(dto.description());
        entity.setIconUrl(dto.iconUrl());

        // Dono Sobrevivente
        if (dto.survivorId() != null) {
            Survivor survivor = survivorRepository.findById(dto.survivorId())
                    .orElseThrow(() -> new ResourceNotFoundException("Sobrevivente não encontrado com ID: " + dto.survivorId()));
            entity.setSurvivor(survivor);
        } else {
            entity.setSurvivor(null);
        }

        // Dono Assassino
        if (dto.killerId() != null) {
            Killer killer = killerRepository.findById(dto.killerId())
                    .orElseThrow(() -> new ResourceNotFoundException("Assassino não encontrado com ID: " + dto.killerId()));
            entity.setKiller(killer);
        } else {
            entity.setKiller(null);
        }

        // Efeitos de Status associados
        if (dto.statusEffectIds() != null && !dto.statusEffectIds().isEmpty()) {
            List<StatusEffect> effects = statusEffectRepository.findAllById(dto.statusEffectIds());
            entity.setStatusEffects(effects);
        } else {
            entity.setStatusEffects(new ArrayList<>());
        }
    }
}
