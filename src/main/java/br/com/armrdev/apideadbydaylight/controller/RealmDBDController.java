package br.com.armrdev.apideadbydaylight.controller;

import br.com.armrdev.apideadbydaylight.dto.RealmDBDRequestDTO;
import br.com.armrdev.apideadbydaylight.dto.RealmDBDResponseDTO;
import br.com.armrdev.apideadbydaylight.service.RealmDBDService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/realms")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class RealmDBDController {

    private final RealmDBDService service;

    @GetMapping
    public ResponseEntity<List<RealmDBDResponseDTO>> findAll(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) UUID dlcId) {
        if (name != null && !name.isBlank()) {
            return ResponseEntity.ok(service.findByName(name));
        }
        if (dlcId != null) {
            return ResponseEntity.ok(service.findByDlcId(dlcId));
        }
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<RealmDBDResponseDTO> findById(@PathVariable UUID id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @PostMapping
    public ResponseEntity<RealmDBDResponseDTO> create(@Valid @RequestBody RealmDBDRequestDTO dto) {
        RealmDBDResponseDTO created = service.create(dto);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(created.id())
                .toUri();
        return ResponseEntity.created(uri).body(created);
    }

    @PutMapping("/{id}")
    public ResponseEntity<RealmDBDResponseDTO> update(@PathVariable UUID id, @Valid @RequestBody RealmDBDRequestDTO dto) {
        return ResponseEntity.ok(service.update(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
