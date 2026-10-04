package br.com.armrdev.apideadbydaylight.dto;

import br.com.armrdev.apideadbydaylight.entity.Perk;
import br.com.armrdev.apideadbydaylight.entity.enums.Role;

import java.util.List;
import java.util.UUID;

public record PerkResponseDTO(
        UUID id,
        String name,
        Role role,
        String description,
        String iconUrl,
        UUID ownerId,
        String ownerName,
        List<StatusEffectSummaryDTO> statusEffects
) {
    public record StatusEffectSummaryDTO(UUID id, String name, String iconUrl) {}

    public static PerkResponseDTO fromEntity(Perk perk) {
        UUID ownerId = null;
        String ownerName = "Geral / Sem Dono";

        if (perk.getSurvivor() != null) {
            ownerId = perk.getSurvivor().getId();
            ownerName = perk.getSurvivor().getName();
        } else if (perk.getKiller() != null) {
            ownerId = perk.getKiller().getId();
            ownerName = perk.getKiller().getTitle() != null ? perk.getKiller().getTitle() : perk.getKiller().getName();
        }

        return new PerkResponseDTO(
                perk.getId(),
                perk.getName(),
                perk.getRole(),
                perk.getDescription(),
                perk.getIconUrl(),
                ownerId,
                ownerName,
                perk.getStatusEffects() != null
                        ? perk.getStatusEffects().stream()
                                .map(se -> new StatusEffectSummaryDTO(se.getId(), se.getName(), se.getIconUrl()))
                                .toList()
                        : List.of()
        );
    }
}
