package br.com.armrdev.apideadbydaylight.service;

import br.com.armrdev.apideadbydaylight.dto.MapGameRequestDTO;
import br.com.armrdev.apideadbydaylight.dto.MapGameResponseDTO;
import br.com.armrdev.apideadbydaylight.entity.Dlc;
import br.com.armrdev.apideadbydaylight.entity.MapGame;
import br.com.armrdev.apideadbydaylight.entity.RealmDBD;
import br.com.armrdev.apideadbydaylight.exception.ResourceNotFoundException;
import br.com.armrdev.apideadbydaylight.repository.DlcRepository;
import br.com.armrdev.apideadbydaylight.repository.MapGameRepository;
import br.com.armrdev.apideadbydaylight.repository.RealmDBDRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class MapGameService {

    private final MapGameRepository mapRepository;
    private final RealmDBDRepository realmRepository;
    private final DlcRepository dlcRepository;

    @Transactional(readOnly = true)
    public List<MapGameResponseDTO> findAll() {
        return mapRepository.findAll()
                .stream()
                .map(MapGameResponseDTO::fromEntity)
                .toList();
    }

    @Transactional(readOnly = true)
    public MapGameResponseDTO findById(UUID id) {
        MapGame entity = mapRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Mapa não encontrado com ID: " + id));
        return MapGameResponseDTO.fromEntity(entity);
    }

    @Transactional
    public MapGameResponseDTO create(MapGameRequestDTO dto) {
        MapGame entity = new MapGame();
        copyDtoToEntity(dto, entity);
        MapGame saved = mapRepository.save(entity);
        return MapGameResponseDTO.fromEntity(saved);
    }

    @Transactional
    public MapGameResponseDTO update(UUID id, MapGameRequestDTO dto) {
        MapGame entity = mapRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Mapa não encontrado com ID: " + id));
        copyDtoToEntity(dto, entity);
        MapGame updated = mapRepository.save(entity);
        return MapGameResponseDTO.fromEntity(updated);
    }

    @Transactional
    public void delete(UUID id) {
        if (!mapRepository.existsById(id)) {
            throw new ResourceNotFoundException("Mapa não encontrado com ID: " + id);
        }
        mapRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public List<MapGameResponseDTO> findByRealmId(UUID realmId) {
        return mapRepository.findByRealmId(realmId)
                .stream()
                .map(MapGameResponseDTO::fromEntity)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<MapGameResponseDTO> findByDlcId(UUID dlcId) {
        return mapRepository.findByDlcId(dlcId)
                .stream()
                .map(MapGameResponseDTO::fromEntity)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<MapGameResponseDTO> findByTitle(String title) {
        return mapRepository.findByTitleContainingIgnoreCase(title)
                .stream()
                .map(MapGameResponseDTO::fromEntity)
                .toList();
    }

    private void copyDtoToEntity(MapGameRequestDTO dto, MapGame entity) {
        entity.setTitle(dto.title());
        entity.setDescription(dto.description());
        entity.setImageUrl(dto.imageUrl());

        RealmDBD realm = realmRepository.findById(dto.realmId())
                .orElseThrow(() -> new ResourceNotFoundException("Reino não encontrado com ID: " + dto.realmId()));
        entity.setRealm(realm);

        if (dto.dlcId() != null) {
            Dlc dlc = dlcRepository.findById(dto.dlcId())
                    .orElseThrow(() -> new ResourceNotFoundException("DLC não encontrada com ID: " + dto.dlcId()));
            entity.setDlc(dlc);
        } else {
            entity.setDlc(null);
        }
    }
}
