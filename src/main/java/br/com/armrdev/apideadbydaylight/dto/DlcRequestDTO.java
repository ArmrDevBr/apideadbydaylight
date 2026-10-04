package br.com.armrdev.apideadbydaylight.dto;

import jakarta.validation.constraints.NotBlank;

import java.time.LocalDate;

public record DlcRequestDTO(
        @NotBlank(message = "O nome da DLC/Capítulo é obrigatório!")
        String name,

        String description,
        String imageUrl,
        LocalDate releaseDate
) {}
