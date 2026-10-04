package br.com.armrdev.apideadbydaylight.controller;

import br.com.armrdev.apideadbydaylight.dto.AddonRequestDTO;
import br.com.armrdev.apideadbydaylight.dto.AddonResponseDTO;
import br.com.armrdev.apideadbydaylight.entity.enums.ItemType;
import br.com.armrdev.apideadbydaylight.entity.enums.Rarity;
import br.com.armrdev.apideadbydaylight.service.AddonService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/addons")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class AddonController {

    private final AddonService service;

    @GetMapping
    public ResponseEntity<List<AddonResponseDTO>> findAll(
            @RequestParam(required = false) UUID killerId,
            @RequestParam(required = false) ItemType itemType,
            @RequestParam(required = false) Rarity rarity,
            @RequestParam(required = false) String name) {
        if (killerId != null) {
            return ResponseEntity.ok(service.findByKillerId(killerId));
        }
        if (itemType != null) {
            return ResponseEntity.ok(service.findByTargetItemType(itemType));
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
    public ResponseEntity<AddonResponseDTO> findById(@PathVariable UUID id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @PostMapping
    public ResponseEntity<AddonResponseDTO> create(@Valid @RequestBody AddonRequestDTO dto) {
        AddonResponseDTO created = service.create(dto);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(created.id())
                .toUri();
        return ResponseEntity.created(uri).body(created);
    }

    @PutMapping("/{id}")
    public ResponseEntity<AddonResponseDTO> update(@PathVariable UUID id, @Valid @RequestBody AddonRequestDTO dto) {
        return ResponseEntity.ok(service.update(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
