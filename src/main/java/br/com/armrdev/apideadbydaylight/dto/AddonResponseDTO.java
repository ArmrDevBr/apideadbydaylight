package br.com.armrdev.apideadbydaylight.dto;

import br.com.armrdev.apideadbydaylight.entity.Addon;
import br.com.armrdev.apideadbydaylight.entity.enums.ItemType;
import br.com.armrdev.apideadbydaylight.entity.enums.Rarity;

import java.util.UUID;

public record AddonResponseDTO(
        UUID id,
        String name,
        Rarity rarity,
        String description,
        String iconUrl,
        UUID killerId,
        String killerName,
        ItemType targetItemType
) {
    public static AddonResponseDTO fromEntity(Addon addon) {
        return new AddonResponseDTO(
                addon.getId(),
                addon.getName(),
                addon.getRarity(),
                addon.getDescription(),
                addon.getIconUrl(),
                addon.getKiller() != null ? addon.getKiller().getId() : null,
                addon.getKiller() != null ? (addon.getKiller().getTitle() != null ? addon.getKiller().getTitle() : addon.getKiller().getName()) : null,
                addon.getTargetItemType()
        );
    }

    // Compatibilidade com método 'from'
    public static AddonResponseDTO from(Addon addon) {
        return fromEntity(addon);
    }
}
