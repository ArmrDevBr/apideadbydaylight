package br.com.armrdev.apideadbydaylight.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.UUID;

@Data
@Entity
@Table(name = "tb_map_game")
public class MapGame {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false)
    private String title; // Ex: "Torre de Carvão", "Ala Leste da R.P.D."

    @Column(columnDefinition = "TEXT")
    private String description;

    private String imageUrl;

    @ManyToOne
    @JoinColumn(name = "realm_id")
    private RealmDBD realm;

    @ManyToOne
    @JoinColumn(name = "dlc_id")
    private Dlc dlc;
}