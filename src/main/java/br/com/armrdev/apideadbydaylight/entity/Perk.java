package br.com.armrdev.apideadbydaylight.entity;

import br.com.armrdev.apideadbydaylight.entity.enums.Role;
import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Data
@Entity
@Table(name = "tb_perk")
public class Perk {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false)
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role; // SURVIVOR ou KILLER

    @Column(columnDefinition = "TEXT")
    private String description;

    private String iconUrl;

    // Se for vantagem ensinável/exclusiva de Sobrevivente (ex: Sprint Burst -> Meg)
    @ManyToOne
    @JoinColumn(name = "survivor_id", nullable = true)
    private Survivor survivor;

    // Se for vantagem ensinável/exclusiva de Assassino (ex: Barbecue & Chilli -> Canibal)
    @ManyToOne
    @JoinColumn(name = "killer_id", nullable = true)
    private Killer killer;

    // Efeitos de estado que essa perk aplica ou remove (ex: Exaustão, Exposto)
    @ManyToMany
    @JoinTable(
        name = "tb_perk_status_effects",
        joinColumns = @JoinColumn(name = "perk_id"),
        inverseJoinColumns = @JoinColumn(name = "status_effect_id")
    )
    private List<StatusEffect> statusEffects = new ArrayList<>();
}