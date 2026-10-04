package br.com.armrdev.apideadbydaylight.dto;

import br.com.armrdev.apideadbydaylight.entity.Item;
import br.com.armrdev.apideadbydaylight.entity.enums.ItemType;
import br.com.armrdev.apideadbydaylight.entity.enums.Rarity;

import java.util.UUID;

public record ItemResponseDTO(
        UUID id,
        String name,
        ItemType itemType,
        Rarity rarity,
        Integer charges,
        String description,
        String imageUrl
) {
    public static ItemResponseDTO fromEntity(Item item) {
        return new ItemResponseDTO(
                item.getId(),
                item.getName(),
                item.getItemType(),
                item.getRarity(),
                item.getCharges(),
                item.getDescription(),
                item.getImageUrl()
        );
    }
}
