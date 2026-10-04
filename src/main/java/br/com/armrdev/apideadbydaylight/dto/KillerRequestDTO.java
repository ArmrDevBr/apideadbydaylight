package br.com.armrdev.apideadbydaylight.dto;

import br.com.armrdev.apideadbydaylight.entity.enums.Difficult;
import br.com.armrdev.apideadbydaylight.entity.enums.Height;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record KillerRequestDTO(
        @NotBlank(message = "O nome é obrigatório!")
        String name,

        @NotBlank(message = "O título é obrigatório! (Ex: O Caçador)")
        String title,

        String lore,
        String namePower,
        String descriptionPower,
        String weaponPrimary,
        String speed,
        String terrorRadius,

        @NotNull(message = "A altura é obrigatória!")
        Height height,
        @NotNull(message = "A dificuldade é obrigatória!")
        Difficult difficultPlay,
        @NotNull(message = "Informe se o killer é licenciado!")

        Boolean licensed,
        String imageUrl,
        String powerIconUrl,
        UUID dlcId

) {

}
