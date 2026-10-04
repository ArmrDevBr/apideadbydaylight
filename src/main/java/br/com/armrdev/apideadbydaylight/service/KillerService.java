package br.com.armrdev.apideadbydaylight.service;

import br.com.armrdev.apideadbydaylight.dto.KillerRequestDTO;
import br.com.armrdev.apideadbydaylight.dto.KillerResponseDTO;
import br.com.armrdev.apideadbydaylight.entity.Dlc;
import br.com.armrdev.apideadbydaylight.entity.Killer;
import br.com.armrdev.apideadbydaylight.entity.enums.Difficult;
import br.com.armrdev.apideadbydaylight.exception.ResourceNotFoundException;
import br.com.armrdev.apideadbydaylight.repository.DlcRepository;
import br.com.armrdev.apideadbydaylight.repository.KillerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class KillerService {

    private final KillerRepository killerRepository;
    private final DlcRepository dlcRepository;

    @Transactional(readOnly = true)
    public List<KillerResponseDTO> findAll() {
        return killerRepository.findAll()
                .stream()
                .map(KillerResponseDTO::fromEntity)
                .toList();
    }

    @Transactional(readOnly = true)
    public KillerResponseDTO findById(UUID id) {
        Killer entity = killerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Assassino não encontrado com ID: " + id));
        return KillerResponseDTO.fromEntity(entity);
    }

    @Transactional
    public KillerResponseDTO create(KillerRequestDTO dto) {
        Killer entity = new Killer();
        copyDtoToEntity(dto, entity);
        Killer saved = killerRepository.save(entity);
        return KillerResponseDTO.fromEntity(saved);
    }

    @Transactional
    public KillerResponseDTO update(UUID id, KillerRequestDTO dto) {
        Killer entity = killerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Assassino não encontrado com ID: " + id));
        copyDtoToEntity(dto, entity);
        Killer updated = killerRepository.save(entity);
        return KillerResponseDTO.fromEntity(updated);
    }

    @Transactional
    public void delete(UUID id) {
        if (!killerRepository.existsById(id)) {
            throw new ResourceNotFoundException("Assassino não encontrado com ID: " + id);
        }
        killerRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public List<KillerResponseDTO> findByTitle(String title) {
        return killerRepository.findByTitleContainingIgnoreCase(title)
                .stream()
                .map(KillerResponseDTO::fromEntity)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<KillerResponseDTO> findByDifficultPlay(Difficult difficult) {
        return killerRepository.findByDifficultPlay(difficult)
                .stream()
                .map(KillerResponseDTO::fromEntity)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<KillerResponseDTO> findByLicensed(Boolean licensed) {
        return killerRepository.findByLicensed(licensed)
                .stream()
                .map(KillerResponseDTO::fromEntity)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<KillerResponseDTO> findByDlcId(UUID dlcId) {
        return killerRepository.findByDlcId(dlcId)
                .stream()
                .map(KillerResponseDTO::fromEntity)
                .toList();
    }

    private void copyDtoToEntity(KillerRequestDTO dto, Killer entity) {
        entity.setName(dto.name());
        entity.setTitle(dto.title());
        entity.setLore(dto.lore());
        entity.setNamePower(dto.namePower());
        entity.setDescriptionPower(dto.descriptionPower());
        entity.setWeaponPrimary(dto.weaponPrimary());
        entity.setSpeed(dto.speed());
        entity.setTerrorRadius(dto.terrorRadius());
        entity.setHeight(dto.height());
        entity.setDifficultPlay(dto.difficultPlay());
        entity.setLicensed(dto.licensed());
        entity.setImageUrl(dto.imageUrl());
        entity.setPowerIconUrl(dto.powerIconUrl());

        if (dto.dlcId() != null) {
            Dlc dlc = dlcRepository.findById(dto.dlcId())
                    .orElseThrow(() -> new ResourceNotFoundException("DLC não encontrada com ID: " + dto.dlcId()));
            entity.setDlc(dlc);
        } else {
            entity.setDlc(null);
        }
    }
}
