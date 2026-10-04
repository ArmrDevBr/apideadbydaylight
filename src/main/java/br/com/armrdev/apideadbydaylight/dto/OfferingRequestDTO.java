package br.com.armrdev.apideadbydaylight.dto;

import br.com.armrdev.apideadbydaylight.entity.enums.Rarity;
import br.com.armrdev.apideadbydaylight.entity.enums.Role;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record OfferingRequestDTO(
        @NotBlank(message = "O nome da oferenda é obrigatório!")
        String name,

        @NotNull(message = "A raridade é obrigatória!")
        Rarity rarity,

        @NotNull(message = "O papel (SURVIVOR, KILLER ou ALL) é obrigatório!")
        Role role,

        String description,
        Boolean secret,
        String iconUrl,

        // ID da DLC associada (opcional)
        UUID dlcId
) {}
