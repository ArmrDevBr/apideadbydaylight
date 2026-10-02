package br.com.armrdev.apideadbydaylight.entity;

import br.com.armrdev.apideadbydaylight.entity.enums.Difficult;
import br.com.armrdev.apideadbydaylight.entity.enums.Height;
import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Data
@Entity
@Table(name = "tb_killer")
public class Killer {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false)
    private String name;

    private String title;

    @Column(columnDefinition = "TEXT")
    private String lore;

    private String namePower;

    @Column(columnDefinition = "TEXT")
    private String descriptionPower;

    private String weaponPrimary;
    private String speed;
    private String terrorRadius;

    @Enumerated(EnumType.STRING)
    private Height height;

    @Enumerated(EnumType.STRING)
    private Difficult difficultPlay;

    private Boolean licensed;
    private String imageUrl;
    private String powerIconUrl;

    // Vantagens exclusivas do assassino
    @OneToMany(mappedBy = "killer")
    private List<Perk> perks = new ArrayList<>();

    // Complementos exclusivos do poder do assassino
    @OneToMany(mappedBy = "killer")
    private List<Addon> addons = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "dlc_id")
    private Dlc dlc;
}
