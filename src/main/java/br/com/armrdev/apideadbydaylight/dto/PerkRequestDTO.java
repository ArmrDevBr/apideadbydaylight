package br.com.armrdev.apideadbydaylight.dto;

import br.com.armrdev.apideadbydaylight.entity.enums.Role;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.List;
import java.util.UUID;

public record PerkRequestDTO(
        @NotBlank(message = "O nome da perk é obrigatório!")
        String name,

        @NotNull(message = "O papel (SURVIVOR ou KILLER) é obrigatório!")
        Role role,

        String description,
        String iconUrl,

        // Dono da perk (se for exclusiva de algum Sobrevivente ou Assassino)
        UUID survivorId,
        UUID killerId,

        // IDs dos efeitos de status vinculados (opcional)
        List<UUID> statusEffectIds
) {}
