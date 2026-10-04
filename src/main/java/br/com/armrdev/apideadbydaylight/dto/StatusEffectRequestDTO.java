package br.com.armrdev.apideadbydaylight.dto;

import br.com.armrdev.apideadbydaylight.entity.enums.Role;
import br.com.armrdev.apideadbydaylight.entity.enums.StatusType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record StatusEffectRequestDTO(
        @NotBlank(message = "O nome do efeito de estado é obrigatório!")
        String name,

        @NotNull(message = "O tipo do efeito (BUFF ou DEBUFF) é obrigatório!")
        StatusType type,

        @NotNull(message = "O lado afetado (SURVIVOR, KILLER ou ALL) é obrigatório!")
        Role affectedRole,

        String description,
        String iconUrl
) {}
