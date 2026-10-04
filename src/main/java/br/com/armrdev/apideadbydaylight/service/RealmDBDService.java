package br.com.armrdev.apideadbydaylight.service;

import br.com.armrdev.apideadbydaylight.dto.RealmDBDRequestDTO;
import br.com.armrdev.apideadbydaylight.dto.RealmDBDResponseDTO;
import br.com.armrdev.apideadbydaylight.entity.Dlc;
import br.com.armrdev.apideadbydaylight.entity.RealmDBD;
import br.com.armrdev.apideadbydaylight.exception.ResourceNotFoundException;
import br.com.armrdev.apideadbydaylight.repository.DlcRepository;
import br.com.armrdev.apideadbydaylight.repository.RealmDBDRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RealmDBDService {

    private final RealmDBDRepository realmRepository;
    private final DlcRepository dlcRepository;

    @Transactional(readOnly = true)
    public List<RealmDBDResponseDTO> findAll() {
        return realmRepository.findAll()
                .stream()
                .map(RealmDBDResponseDTO::fromEntity)
                .toList();
    }

    @Transactional(readOnly = true)
    public RealmDBDResponseDTO findById(UUID id) {
        RealmDBD entity = realmRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Reino não encontrado com ID: " + id));
        return RealmDBDResponseDTO.fromEntity(entity);
    }

    @Transactional
    public RealmDBDResponseDTO create(RealmDBDRequestDTO dto) {
        RealmDBD entity = new RealmDBD();
        copyDtoToEntity(dto, entity);
        RealmDBD saved = realmRepository.save(entity);
        return RealmDBDResponseDTO.fromEntity(saved);
    }

    @Transactional
    public RealmDBDResponseDTO update(UUID id, RealmDBDRequestDTO dto) {
        RealmDBD entity = realmRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Reino não encontrado com ID: " + id));
        copyDtoToEntity(dto, entity);
        RealmDBD updated = realmRepository.save(entity);
        return RealmDBDResponseDTO.fromEntity(updated);
    }

    @Transactional
    public void delete(UUID id) {
        if (!realmRepository.existsById(id)) {
            throw new ResourceNotFoundException("Reino não encontrado com ID: " + id);
        }
        realmRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public List<RealmDBDResponseDTO> findByName(String name) {
        return realmRepository.findByNameContainingIgnoreCase(name)
                .stream()
                .map(RealmDBDResponseDTO::fromEntity)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<RealmDBDResponseDTO> findByDlcId(UUID dlcId) {
        return realmRepository.findByDlcId(dlcId)
                .stream()
                .map(RealmDBDResponseDTO::fromEntity)
                .toList();
    }

    private void copyDtoToEntity(RealmDBDRequestDTO dto, RealmDBD entity) {
        entity.setName(dto.name());

        if (dto.dlcId() != null) {
            Dlc dlc = dlcRepository.findById(dto.dlcId())
                    .orElseThrow(() -> new ResourceNotFoundException("DLC não encontrada com ID: " + dto.dlcId()));
            entity.setDlc(dlc);
        } else {
            entity.setDlc(null);
        }
    }
}
