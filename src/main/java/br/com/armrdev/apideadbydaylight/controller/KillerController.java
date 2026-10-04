package br.com.armrdev.apideadbydaylight.controller;

import br.com.armrdev.apideadbydaylight.dto.KillerRequestDTO;
import br.com.armrdev.apideadbydaylight.dto.KillerResponseDTO;
import br.com.armrdev.apideadbydaylight.entity.enums.Difficult;
import br.com.armrdev.apideadbydaylight.service.KillerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/killers")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class KillerController {

    private final KillerService service;

    @GetMapping
    public ResponseEntity<List<KillerResponseDTO>> findAll(
            @RequestParam(required = false) String title,
            @RequestParam(required = false) Difficult difficult,
            @RequestParam(required = false) Boolean licensed,
            @RequestParam(required = false) UUID dlcId) {
        if (title != null && !title.isBlank()) {
            return ResponseEntity.ok(service.findByTitle(title));
        }
        if (difficult != null) {
            return ResponseEntity.ok(service.findByDifficultPlay(difficult));
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
    public ResponseEntity<KillerResponseDTO> findById(@PathVariable UUID id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @PostMapping
    public ResponseEntity<KillerResponseDTO> create(@Valid @RequestBody KillerRequestDTO dto) {
        KillerResponseDTO created = service.create(dto);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(created.id())
                .toUri();
        return ResponseEntity.created(uri).body(created);
    }

    @PutMapping("/{id}")
    public ResponseEntity<KillerResponseDTO> update(@PathVariable UUID id, @Valid @RequestBody KillerRequestDTO dto) {
        return ResponseEntity.ok(service.update(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
