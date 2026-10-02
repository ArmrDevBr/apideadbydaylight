package br.com.armrdev.apideadbydaylight.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Data
@Entity
@Table(name = "tb_dlc")
public class Dlc {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false)
    private String name;

    @Column(columnDefinition = "TEXT")
    private String description;

    private String imageUrl;

    private LocalDate releaseDate;

    // Sobreviventes lançados neste capítulo/DLC
    @OneToMany(mappedBy = "dlc")
    private List<Survivor> survivors = new ArrayList<>();

    // Assassinos lançados neste capítulo/DLC
    @OneToMany(mappedBy = "dlc")
    private List<Killer> killers = new ArrayList<>();

    // Mapas introduzidos nesta DLC
    @OneToMany(mappedBy = "dlc")
    private List<MapGame> maps = new ArrayList<>();

    // Oferendas introduzidas nesta DLC
    @OneToMany(mappedBy = "dlc")
    private List<Offering> offerings = new ArrayList<>();
}