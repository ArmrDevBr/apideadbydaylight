package br.com.armrdev.apideadbydaylight.dto;

import br.com.armrdev.apideadbydaylight.entity.enums.ItemType;
import br.com.armrdev.apideadbydaylight.entity.enums.Rarity;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record AddonRequestDTO(
        @NotBlank(message = "O nome do add-on é obrigatório!")
        String name,

        @NotNull(message = "A raridade é obrigatória!")
        Rarity rarity,

        String description,
        String iconUrl,

        // Se for complemento de poder de assassino (ex: Nurse, Trapper)
        UUID killerId,

        // Se for complemento de tipo de item de sobrevivente (MED_KIT, TOOLBOX, FLASHLIGHT, etc.)
        ItemType targetItemType
) {}
