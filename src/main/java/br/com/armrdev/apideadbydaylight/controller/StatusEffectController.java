package br.com.armrdev.apideadbydaylight.controller;

import br.com.armrdev.apideadbydaylight.dto.StatusEffectRequestDTO;
import br.com.armrdev.apideadbydaylight.dto.StatusEffectResponseDTO;
import br.com.armrdev.apideadbydaylight.entity.enums.Role;
import br.com.armrdev.apideadbydaylight.entity.enums.StatusType;
import br.com.armrdev.apideadbydaylight.service.StatusEffectService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/status-effects")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class StatusEffectController {

    private final StatusEffectService service;

    @GetMapping
    public ResponseEntity<List<StatusEffectResponseDTO>> findAll(
            @RequestParam(required = false) StatusType type,
            @RequestParam(required = false) Role role) {
        if (type != null) {
            return ResponseEntity.ok(service.findByType(type));
        }
        if (role != null) {
            return ResponseEntity.ok(service.findByAffectedRole(role));
        }
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<StatusEffectResponseDTO> findById(@PathVariable UUID id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @PostMapping
    public ResponseEntity<StatusEffectResponseDTO> create(@Valid @RequestBody StatusEffectRequestDTO dto) {
        StatusEffectResponseDTO created = service.create(dto);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(created.id())
                .toUri();
        return ResponseEntity.created(uri).body(created);
    }

    @PutMapping("/{id}")
    public ResponseEntity<StatusEffectResponseDTO> update(@PathVariable UUID id, @Valid @RequestBody StatusEffectRequestDTO dto) {
        return ResponseEntity.ok(service.update(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
