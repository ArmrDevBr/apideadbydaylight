package br.com.armrdev.apideadbydaylight.controller;

import br.com.armrdev.apideadbydaylight.dto.DlcRequestDTO;
import br.com.armrdev.apideadbydaylight.dto.DlcResponseDTO;
import br.com.armrdev.apideadbydaylight.service.DlcService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/dlcs")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class DlcController {

    private final DlcService service;

    @GetMapping
    public ResponseEntity<List<DlcResponseDTO>> findAll(@RequestParam(required = false) String name) {
        if (name != null && !name.isBlank()) {
            return ResponseEntity.ok(service.findByName(name));
        }
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<DlcResponseDTO> findById(@PathVariable UUID id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @PostMapping
    public ResponseEntity<DlcResponseDTO> create(@Valid @RequestBody DlcRequestDTO dto) {
        DlcResponseDTO created = service.create(dto);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(created.id())
                .toUri();
        return ResponseEntity.created(uri).body(created);
    }

    @PutMapping("/{id}")
    public ResponseEntity<DlcResponseDTO> update(@PathVariable UUID id, @Valid @RequestBody DlcRequestDTO dto) {
        return ResponseEntity.ok(service.update(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
