package br.com.armrdev.apideadbydaylight.entity;

import br.com.armrdev.apideadbydaylight.entity.enums.Rarity;
import br.com.armrdev.apideadbydaylight.entity.enums.Role;
import jakarta.persistence.*;
import lombok.Data;

import java.util.UUID;

@Data
@Entity
@Table(name = "tb_offering")
public class Offering {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false)
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Rarity rarity;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role; // SURVIVOR, KILLER ou ALL

    @Column(columnDefinition = "TEXT")
    private String description;

    private Boolean secret = false; // Se a oferenda fica virada de costas no carregamento

    private String iconUrl;

    @ManyToOne
    @JoinColumn(name = "dlc_id", nullable = true)
    private Dlc dlc;
}
