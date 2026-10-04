package br.com.armrdev.apideadbydaylight.entity;

import br.com.armrdev.apideadbydaylight.entity.enums.ItemType;
import br.com.armrdev.apideadbydaylight.entity.enums.Rarity;
import jakarta.persistence.*;
import lombok.Data;

import java.util.UUID;

@Data
@Entity
@Table(name = "tb_addon")
public class Addon {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false)
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Rarity rarity;

    @Column(columnDefinition = "TEXT")
    private String description;

    private String iconUrl;

    // Se for complemento de poder de Assassino (ex: Nurse, Blight, Trapper)
    @ManyToOne
    @JoinColumn(name = "killer_id", nullable = true)
    private Killer killer;

    // Se for complemento para um tipo de item de sobrevivente (ex: MED_KIT, TOOLBOX, FLASHLIGHT)
    @Enumerated(EnumType.STRING)
    private ItemType targetItemType;
}
