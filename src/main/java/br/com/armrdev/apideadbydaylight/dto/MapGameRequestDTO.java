package br.com.armrdev.apideadbydaylight.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record MapGameRequestDTO(
        @NotBlank(message = "O título do mapa é obrigatório!")
        String title,

        String description,
        String imageUrl,

        @NotNull(message = "O ID do reino (realmId) é obrigatório!")
        UUID realmId,

        // ID da DLC associada (opcional)
        UUID dlcId
) {}
