package br.com.armrdev.apideadbydaylight.controller;

import br.com.armrdev.apideadbydaylight.dto.ItemRequestDTO;
import br.com.armrdev.apideadbydaylight.dto.ItemResponseDTO;
import br.com.armrdev.apideadbydaylight.entity.enums.ItemType;
import br.com.armrdev.apideadbydaylight.entity.enums.Rarity;
import br.com.armrdev.apideadbydaylight.service.ItemService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/items")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class ItemController {

    private final ItemService service;

    @GetMapping
    public ResponseEntity<List<ItemResponseDTO>> findAll(
            @RequestParam(required = false) ItemType type,
            @RequestParam(required = false) Rarity rarity,
            @RequestParam(required = false) String name) {
        if (type != null) {
            return ResponseEntity.ok(service.findByItemType(type));
        }
        if (rarity != null) {
            return ResponseEntity.ok(service.findByRarity(rarity));
        }
        if (name != null && !name.isBlank()) {
            return ResponseEntity.ok(service.findByName(name));
        }
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ItemResponseDTO> findById(@PathVariable UUID id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @PostMapping
    public ResponseEntity<ItemResponseDTO> create(@Valid @RequestBody ItemRequestDTO dto) {
        ItemResponseDTO created = service.create(dto);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(created.id())
                .toUri();
        return ResponseEntity.created(uri).body(created);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ItemResponseDTO> update(@PathVariable UUID id, @Valid @RequestBody ItemRequestDTO dto) {
        return ResponseEntity.ok(service.update(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
