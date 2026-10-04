package br.com.armrdev.apideadbydaylight.dto;

import br.com.armrdev.apideadbydaylight.entity.enums.ItemType;
import br.com.armrdev.apideadbydaylight.entity.enums.Rarity;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

public record ItemRequestDTO(
        @NotBlank(message = "O nome do item é obrigatório!")
        String name,

        @NotNull(message = "O tipo do item (MED_KIT, TOOLBOX, FLASHLIGHT, etc.) é obrigatório!")
        ItemType itemType,

        @NotNull(message = "A raridade é obrigatória!")
        Rarity rarity,

        @PositiveOrZero(message = "A quantidade de cargas não pode ser negativa!")
        Integer charges,

        String description,
        String imageUrl
) {}
