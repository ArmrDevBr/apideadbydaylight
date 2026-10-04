package br.com.armrdev.apideadbydaylight.dto;

import br.com.armrdev.apideadbydaylight.entity.RealmDBD;

import java.util.List;
import java.util.UUID;

public record RealmDBDResponseDTO(
        UUID id,
        String name,
        UUID dlcId,
        String dlcName,
        List<MapSummaryDTO> maps
) {
    public record MapSummaryDTO(UUID id, String title, String imageUrl) {}

    public static RealmDBDResponseDTO fromEntity(RealmDBD realm) {
        return new RealmDBDResponseDTO(
                realm.getId(),
                realm.getName(),
                realm.getDlc() != null ? realm.getDlc().getId() : null,
                realm.getDlc() != null ? realm.getDlc().getName() : null,
                realm.getMaps() != null
                        ? realm.getMaps().stream()
                                .map(m -> new MapSummaryDTO(m.getId(), m.getTitle(), m.getImageUrl()))
                                .toList()
                        : List.of()
        );
    }
}
