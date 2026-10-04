package br.com.armrdev.apideadbydaylight.dto;

import br.com.armrdev.apideadbydaylight.entity.Offering;
import br.com.armrdev.apideadbydaylight.entity.enums.Rarity;
import br.com.armrdev.apideadbydaylight.entity.enums.Role;

import java.util.UUID;

public record OfferingResponseDTO(
        UUID id,
        String name,
        Rarity rarity,
        Role role,
        String description,
        Boolean secret,
        String iconUrl,
        UUID dlcId,
        String dlcName
) {
    public static OfferingResponseDTO fromEntity(Offering offering) {
        return new OfferingResponseDTO(
                offering.getId(),
                offering.getName(),
                offering.getRarity(),
                offering.getRole(),
                offering.getDescription(),
                offering.getSecret(),
                offering.getIconUrl(),
                offering.getDlc() != null ? offering.getDlc().getId() : null,
                offering.getDlc() != null ? offering.getDlc().getName() : null
        );
    }
}
