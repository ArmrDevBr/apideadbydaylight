package br.com.armrdev.apideadbydaylight.dto;

import br.com.armrdev.apideadbydaylight.entity.enums.Gender;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

/**
 * DTO para ENTRADA de dados (POST / PUT).
 * Contém validações e recebe apenas os IDs das entidades relacionadas.
 */
public record SurvivorRequestDTO(

        @NotBlank(message = "O nome é obrigatório!")
        String name,

        @NotNull(message = "O gênero é obrigatório!")
        Gender gender,

        String birthday,
        String lore,
        String height,
        String weight,

        @NotNull(message = "Informe se o personagem é licenciado!")
        Boolean licensed,

        String imageUrl,

        // ID da DLC vinculada (pode ser nulo caso seja do jogo base)
        UUID dlcId
) {}
