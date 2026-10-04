package br.com.armrdev.apideadbydaylight.dto;

import br.com.armrdev.apideadbydaylight.entity.StatusEffect;
import br.com.armrdev.apideadbydaylight.entity.enums.Role;
import br.com.armrdev.apideadbydaylight.entity.enums.StatusType;

import java.util.UUID;

public record StatusEffectResponseDTO(
        UUID id,
        String name,
        StatusType type,
        Role affectedRole,
        String description,
        String iconUrl
) {
    public static StatusEffectResponseDTO fromEntity(StatusEffect statusEffect) {
        return new StatusEffectResponseDTO(
                statusEffect.getId(),
                statusEffect.getName(),
                statusEffect.getType(),
                statusEffect.getAffectedRole(),
                statusEffect.getDescription(),
                statusEffect.getIconUrl()
        );
    }
}
