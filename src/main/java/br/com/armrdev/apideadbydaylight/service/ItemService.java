package br.com.armrdev.apideadbydaylight.service;

import br.com.armrdev.apideadbydaylight.dto.ItemRequestDTO;
import br.com.armrdev.apideadbydaylight.dto.ItemResponseDTO;
import br.com.armrdev.apideadbydaylight.entity.Item;
import br.com.armrdev.apideadbydaylight.entity.enums.ItemType;
import br.com.armrdev.apideadbydaylight.entity.enums.Rarity;
import br.com.armrdev.apideadbydaylight.exception.ResourceNotFoundException;
import br.com.armrdev.apideadbydaylight.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ItemService {

    private final ItemRepository repository;

    @Transactional(readOnly = true)
    public List<ItemResponseDTO> findAll() {
        return repository.findAll()
                .stream()
                .map(ItemResponseDTO::fromEntity)
                .toList();
    }

    @Transactional(readOnly = true)
    public ItemResponseDTO findById(UUID id) {
        Item entity = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Item não encontrado com ID: " + id));
        return ItemResponseDTO.fromEntity(entity);
    }

    @Transactional
    public ItemResponseDTO create(ItemRequestDTO dto) {
        Item entity = new Item();
        copyDtoToEntity(dto, entity);
        Item saved = repository.save(entity);
        return ItemResponseDTO.fromEntity(saved);
    }

    @Transactional
    public ItemResponseDTO update(UUID id, ItemRequestDTO dto) {
        Item entity = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Item não encontrado com ID: " + id));
        copyDtoToEntity(dto, entity);
        Item updated = repository.save(entity);
        return ItemResponseDTO.fromEntity(updated);
    }

    @Transactional
    public void delete(UUID id) {
        if (!repository.existsById(id)) {
            throw new ResourceNotFoundException("Item não encontrado com ID: " + id);
        }
        repository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public List<ItemResponseDTO> findByItemType(ItemType itemType) {
        return repository.findByItemType(itemType)
                .stream()
                .map(ItemResponseDTO::fromEntity)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<ItemResponseDTO> findByRarity(Rarity rarity) {
        return repository.findByRarity(rarity)
                .stream()
                .map(ItemResponseDTO::fromEntity)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<ItemResponseDTO> findByName(String name) {
        return repository.findByNameContainingIgnoreCase(name)
                .stream()
                .map(ItemResponseDTO::fromEntity)
                .toList();
    }

    private void copyDtoToEntity(ItemRequestDTO dto, Item entity) {
        entity.setName(dto.name());
        entity.setItemType(dto.itemType());
        entity.setRarity(dto.rarity());
        entity.setCharges(dto.charges());
        entity.setDescription(dto.description());
        entity.setImageUrl(dto.imageUrl());
    }
}
