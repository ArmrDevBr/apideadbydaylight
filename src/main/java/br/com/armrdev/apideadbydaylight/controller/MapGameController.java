package br.com.armrdev.apideadbydaylight.controller;

import br.com.armrdev.apideadbydaylight.dto.MapGameRequestDTO;
import br.com.armrdev.apideadbydaylight.dto.MapGameResponseDTO;
import br.com.armrdev.apideadbydaylight.service.MapGameService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/maps")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class MapGameController {

    private final MapGameService service;

    @GetMapping
    public ResponseEntity<List<MapGameResponseDTO>> findAll(
            @RequestParam(required = false) UUID realmId,
            @RequestParam(required = false) UUID dlcId,
            @RequestParam(required = false) String title) {
        if (realmId != null) {
            return ResponseEntity.ok(service.findByRealmId(realmId));
        }
        if (dlcId != null) {
            return ResponseEntity.ok(service.findByDlcId(dlcId));
        }
        if (title != null && !title.isBlank()) {
            return ResponseEntity.ok(service.findByTitle(title));
        }
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<MapGameResponseDTO> findById(@PathVariable UUID id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @PostMapping
    public ResponseEntity<MapGameResponseDTO> create(@Valid @RequestBody MapGameRequestDTO dto) {
        MapGameResponseDTO created = service.create(dto);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(created.id())
                .toUri();
        return ResponseEntity.created(uri).body(created);
    }

    @PutMapping("/{id}")
    public ResponseEntity<MapGameResponseDTO> update(@PathVariable UUID id, @Valid @RequestBody MapGameRequestDTO dto) {
        return ResponseEntity.ok(service.update(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
