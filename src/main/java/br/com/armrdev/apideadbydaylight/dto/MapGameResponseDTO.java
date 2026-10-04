package br.com.armrdev.apideadbydaylight.dto;

import br.com.armrdev.apideadbydaylight.entity.MapGame;

import java.util.UUID;

public record MapGameResponseDTO(
        UUID id,
        String title,
        String description,
        String imageUrl,
        UUID realmId,
        String realmName,
        UUID dlcId,
        String dlcName
) {
    public static MapGameResponseDTO fromEntity(MapGame map) {
        return new MapGameResponseDTO(
                map.getId(),
                map.getTitle(),
                map.getDescription(),
                map.getImageUrl(),
                map.getRealm() != null ? map.getRealm().getId() : null,
                map.getRealm() != null ? map.getRealm().getName() : null,
                map.getDlc() != null ? map.getDlc().getId() : null,
                map.getDlc() != null ? map.getDlc().getName() : null
        );
    }
}
