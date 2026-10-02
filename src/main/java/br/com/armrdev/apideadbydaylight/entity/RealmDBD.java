package br.com.armrdev.apideadbydaylight.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Data
@Entity
@Table(name = "tb_realm")
public class RealmDBD {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false)
    private String name; // Ex: "Propriedade MacMillan", "Autohaven Wreckers", "Raccoon City"

    // Um Reino possui vários Mapas
    @OneToMany(mappedBy = "realm")
    private List<MapGame> maps = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "dlc_id", nullable = true)
    private Dlc dlc;
}