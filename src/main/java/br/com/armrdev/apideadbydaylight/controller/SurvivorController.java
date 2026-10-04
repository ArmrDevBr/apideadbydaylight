package br.com.armrdev.apideadbydaylight.controller;

import br.com.armrdev.apideadbydaylight.dto.SurvivorRequestDTO;
import br.com.armrdev.apideadbydaylight.dto.SurvivorResponseDTO;
import br.com.armrdev.apideadbydaylight.service.SurvivorService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/survivors")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class SurvivorController {

    private final SurvivorService service;

    @GetMapping
    public ResponseEntity<List<SurvivorResponseDTO>> findAll(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) Boolean licensed,
            @RequestParam(required = false) UUID dlcId) {
        if (name != null && !name.isBlank()) {
            return ResponseEntity.ok(service.findByName(name));
        }
        if (licensed != null) {
            return ResponseEntity.ok(service.findByLicensed(licensed));
        }
        if (dlcId != null) {
            return ResponseEntity.ok(service.findByDlcId(dlcId));
        }
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<SurvivorResponseDTO> findById(@PathVariable UUID id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @PostMapping
    public ResponseEntity<SurvivorResponseDTO> create(@Valid @RequestBody SurvivorRequestDTO dto) {
        SurvivorResponseDTO created = service.create(dto);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(created.id())
                .toUri();
        return ResponseEntity.created(uri).body(created);
    }

    @PutMapping("/{id}")
    public ResponseEntity<SurvivorResponseDTO> update(@PathVariable UUID id, @Valid @RequestBody SurvivorRequestDTO dto) {
        return ResponseEntity.ok(service.update(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
