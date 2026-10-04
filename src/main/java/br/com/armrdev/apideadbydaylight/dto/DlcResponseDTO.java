package br.com.armrdev.apideadbydaylight.dto;

import br.com.armrdev.apideadbydaylight.entity.Dlc;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public record DlcResponseDTO(
        UUID id,
        String name,
        String description,
        String imageUrl,
        LocalDate releaseDate,
        List<CharacterSummaryDTO> survivors,
        List<CharacterSummaryDTO> killers,
        List<MapSummaryDTO> maps,
        List<OfferingSummaryDTO> offerings
) {
    public record CharacterSummaryDTO(UUID id, String name, String imageUrl) {}
    public record MapSummaryDTO(UUID id, String title, String imageUrl) {}
    public record OfferingSummaryDTO(UUID id, String name, String iconUrl) {}

    public static DlcResponseDTO fromEntity(Dlc dlc) {
        return new DlcResponseDTO(
                dlc.getId(),
                dlc.getName(),
                dlc.getDescription(),
                dlc.getImageUrl(),
                dlc.getReleaseDate(),
                dlc.getSurvivors() != null
                        ? dlc.getSurvivors().stream()
                                .map(s -> new CharacterSummaryDTO(s.getId(), s.getName(), s.getImageUrl()))
                                .toList()
                        : List.of(),
                dlc.getKillers() != null
                        ? dlc.getKillers().stream()
                                .map(k -> new CharacterSummaryDTO(k.getId(), k.getTitle() != null ? k.getTitle() : k.getName(), k.getImageUrl()))
                                .toList()
                        : List.of(),
                dlc.getMaps() != null
                        ? dlc.getMaps().stream()
                                .map(m -> new MapSummaryDTO(m.getId(), m.getTitle(), m.getImageUrl()))
                                .toList()
                        : List.of(),
                dlc.getOfferings() != null
                        ? dlc.getOfferings().stream()
                                .map(o -> new OfferingSummaryDTO(o.getId(), o.getName(), o.getIconUrl()))
                                .toList()
                        : List.of()
        );
    }
}
