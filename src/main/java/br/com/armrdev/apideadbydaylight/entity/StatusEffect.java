package br.com.armrdev.apideadbydaylight.entity;

import br.com.armrdev.apideadbydaylight.entity.enums.Role;
import br.com.armrdev.apideadbydaylight.entity.enums.StatusType;
import jakarta.persistence.*;
import lombok.Data;

import java.util.UUID;

@Data
@Entity
@Table(name = "tb_status_effect")
public class StatusEffect {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false, unique = true)
    private String name; // Ex: "Exposto", "Exausto", "Indetectável", "Dilacerado"

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StatusType type; // BUFF ou DEBUFF

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role affectedRole; // SURVIVOR, KILLER ou ALL

    @Column(columnDefinition = "TEXT")
    private String description;

    private String iconUrl;
}
