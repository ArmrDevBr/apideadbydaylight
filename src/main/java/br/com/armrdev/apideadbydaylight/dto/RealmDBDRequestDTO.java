package br.com.armrdev.apideadbydaylight.dto;

import jakarta.validation.constraints.NotBlank;

import java.util.UUID;

public record RealmDBDRequestDTO(
        @NotBlank(message = "O nome do reino é obrigatório!")
        String name,

        // ID da DLC associada (opcional)
        UUID dlcId
) {}
