package br.com.armrdev.apideadbydaylight.dto;

import br.com.armrdev.apideadbydaylight.entity.Killer;
import br.com.armrdev.apideadbydaylight.entity.enums.Difficult;
import br.com.armrdev.apideadbydaylight.entity.enums.Height;
import br.com.armrdev.apideadbydaylight.entity.enums.Rarity;

import java.util.List;
import java.util.UUID;

public record KillerResponseDTO(
    UUID id,
    String name,
    String title,
    String lore,
    String namePower,
    String descriptionPower,
    String weaponPrimary,
    String speed,
    String terrorRadius,
    Height height,
    Difficult difficultPlay,
    Boolean licensed,
    String imageUrl,
    String powerIconUrl,
    UUID dlcId,
    String dlcName,
    // Lista resumida das perks exclusivas deste assassino
    List<PerkSummaryDTO> perks,
    // Lista resumida dos add-ons deste assassino
    List<AddonSummaryDTO> addons
) {
        // Sub-DTO para não trazer a entidade Perk inteira (evita loop e excesso de dados)
        public record PerkSummaryDTO(UUID id, String name, String iconUrl) {}
        // Sub-DTO com o essencial do Add-on (id, nome, raridade e ícone)
        public record AddonSummaryDTO(UUID id, String name, Rarity rarity, String iconUrl) {}

        /**
         * Converte a Entidade Killer para o DTO com suas coleções
         */
        public static KillerResponseDTO fromEntity(Killer killer) {
            return new KillerResponseDTO(
                    killer.getId(),
                    killer.getName(),
                    killer.getTitle(),
                    killer.getLore(),
                    killer.getNamePower(),
                    killer.getDescriptionPower(),
                    killer.getWeaponPrimary(),
                    killer.getSpeed(),
                    killer.getTerrorRadius(),
                    killer.getHeight(),
                    killer.getDifficultPlay(),
                    killer.getLicensed(),
                    killer.getImageUrl(),
                    killer.getPowerIconUrl(),
                    killer.getDlc() != null ? killer.getDlc().getId() : null,
                    killer.getDlc() != null ? killer.getDlc().getName() : "Jogo Base",

                    // Mapeia as Perks para o resumo
                    killer.getPerks() != null
                            ? killer.getPerks().stream()
                            .map(
                                    p -> new PerkSummaryDTO(
                                            p.getId(),
                                            p.getName(),
                                            p.getIconUrl()
                                    )
                            ).toList()
                            : List.of(),
                    // Mapeia os Addons para o resumo
                    killer.getAddons() != null
                            ? killer.getAddons().stream()
                            .map(
                                    a -> new AddonSummaryDTO(
                                            a.getId(),
                                            a.getName(),
                                            a.getRarity(),
                                            a.getIconUrl()
                                    )
                            ).toList()
                            : List.of()
            );
        }
    }
