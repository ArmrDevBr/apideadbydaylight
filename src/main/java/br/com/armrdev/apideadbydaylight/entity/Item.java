package br.com.armrdev.apideadbydaylight.entity;

import br.com.armrdev.apideadbydaylight.entity.enums.ItemType;
import br.com.armrdev.apideadbydaylight.entity.enums.Rarity;
import jakarta.persistence.*;
import lombok.Data;

import java.util.UUID;

@Data
@Entity
@Table(name = "tb_item")
public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false)
    private String name; // Ex: "Kit Médico de Emergência", "Lanterna Utilitária"

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ItemType itemType; // Ex: MED_KIT, FLASHLIGHT

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Rarity rarity; // Ex: COMUM, RARO, EVENTO

    private Integer charges; // Cargas / Durabilidade (ex: 16, 24, 32)

    @Column(columnDefinition = "TEXT")
    private String description;

    private String imageUrl;
}
