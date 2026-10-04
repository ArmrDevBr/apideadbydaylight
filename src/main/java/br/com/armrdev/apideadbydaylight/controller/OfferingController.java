package br.com.armrdev.apideadbydaylight.controller;

import br.com.armrdev.apideadbydaylight.dto.OfferingRequestDTO;
import br.com.armrdev.apideadbydaylight.dto.OfferingResponseDTO;
import br.com.armrdev.apideadbydaylight.entity.enums.Rarity;
import br.com.armrdev.apideadbydaylight.entity.enums.Role;
import br.com.armrdev.apideadbydaylight.service.OfferingService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/offerings")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class OfferingController {

    private final OfferingService service;

    @GetMapping
    public ResponseEntity<List<OfferingResponseDTO>> findAll(
            @RequestParam(required = false) Role role,
            @RequestParam(required = false) Rarity rarity,
            @RequestParam(required = false) Boolean secret,
            @RequestParam(required = false) UUID dlcId,
            @RequestParam(required = false) String name) {
        if (role != null) {
            return ResponseEntity.ok(service.findByRole(role));
        }
        if (rarity != null) {
            return ResponseEntity.ok(service.findByRarity(rarity));
        }
        if (secret != null) {
            return ResponseEntity.ok(service.findBySecret(secret));
        }
        if (dlcId != null) {
            return ResponseEntity.ok(service.findByDlcId(dlcId));
        }
        if (name != null && !name.isBlank()) {
            return ResponseEntity.ok(service.findByName(name));
        }
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<OfferingResponseDTO> findById(@PathVariable UUID id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @PostMapping
    public ResponseEntity<OfferingResponseDTO> create(@Valid @RequestBody OfferingRequestDTO dto) {
        OfferingResponseDTO created = service.create(dto);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(created.id())
                .toUri();
        return ResponseEntity.created(uri).body(created);
    }

    @PutMapping("/{id}")
    public ResponseEntity<OfferingResponseDTO> update(@PathVariable UUID id, @Valid @RequestBody OfferingRequestDTO dto) {
        return ResponseEntity.ok(service.update(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
