package br.com.armrdev.apideadbydaylight.controller;

import br.com.armrdev.apideadbydaylight.dto.PerkRequestDTO;
import br.com.armrdev.apideadbydaylight.dto.PerkResponseDTO;
import br.com.armrdev.apideadbydaylight.entity.enums.Role;
import br.com.armrdev.apideadbydaylight.service.PerkService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/perks")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class PerkController {

    private final PerkService service;

    @GetMapping
    public ResponseEntity<List<PerkResponseDTO>> findAll(
            @RequestParam(required = false) Role role,
            @RequestParam(required = false) UUID survivorId,
            @RequestParam(required = false) UUID killerId,
            @RequestParam(required = false) String name) {
        if (role != null) {
            return ResponseEntity.ok(service.findByRole(role));
        }
        if (survivorId != null) {
            return ResponseEntity.ok(service.findBySurvivorId(survivorId));
        }
        if (killerId != null) {
            return ResponseEntity.ok(service.findByKillerId(killerId));
        }
        if (name != null && !name.isBlank()) {
            return ResponseEntity.ok(service.findByName(name));
        }
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping("/general")
    public ResponseEntity<List<PerkResponseDTO>> findGeneralPerks(@RequestParam Role role) {
        return ResponseEntity.ok(service.findGeneralPerks(role));
    }

    @GetMapping("/{id}")
    public ResponseEntity<PerkResponseDTO> findById(@PathVariable UUID id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @PostMapping
    public ResponseEntity<PerkResponseDTO> create(@Valid @RequestBody PerkRequestDTO dto) {
        PerkResponseDTO created = service.create(dto);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(created.id())
                .toUri();
        return ResponseEntity.created(uri).body(created);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PerkResponseDTO> update(@PathVariable UUID id, @Valid @RequestBody PerkRequestDTO dto) {
        return ResponseEntity.ok(service.update(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
