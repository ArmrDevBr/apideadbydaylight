package br.com.armrdev.apideadbydaylight.dto;

import br.com.armrdev.apideadbydaylight.entity.Survivor;
import br.com.armrdev.apideadbydaylight.entity.enums.Gender;

import java.util.List;
import java.util.UUID;

/**
 * DTO para SAÍDA de dados (GET).
 * Retorna os dados formatados e evita loops infinitos de serialização JSON.
 */
public record SurvivorResponseDTO(
        UUID id,
        String name,
        Gender gender,
        String birthday,
        String lore,
        String height,
        String weight,
        Boolean licensed,
        String imageUrl,
        UUID dlcId,
        String dlcName,
        List<String> perkNames
) {
    /**
     * Método utilitário para converter uma entidade Survivor diretamente para este DTO.
     */
    public static SurvivorResponseDTO fromEntity(Survivor survivor) {
        return new SurvivorResponseDTO(
                survivor.getId(),
                survivor.getName(),
                survivor.getGender(),
                survivor.getBirthday(),
                survivor.getLore(),
                survivor.getHeight(),
                survivor.getWeight(),
                survivor.getLicensed(),
                survivor.getImageUrl(),
                survivor.getDlc() != null ? survivor.getDlc().getId() : null,
                survivor.getDlc() != null ? survivor.getDlc().getName() : "Jogo Base",
                survivor.getPerks() != null
                        ? survivor.getPerks().stream().map(perk -> perk.getName()).toList()
                        : List.of()
        );
    }
}
