package br.com.armrdev.apideadbydaylight.entity;

import br.com.armrdev.apideadbydaylight.entity.enums.Gender;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Data
@Entity
@Table(name = "tb_survivor")
public class Survivor {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @NotBlank(message = "O nome não pode ser vazio!")
    @Column(nullable = false)
    private String name;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    private String birthday;

    @Column(columnDefinition = "TEXT")
    private String lore;

    private String height;
    private String weight;
    private Boolean licensed;
    private String imageUrl;

    // Vantagens exclusivas do sobrevivente (mappedBy aponta para o campo 'survivor' em Perk)
    @OneToMany(mappedBy = "survivor")
    private List<Perk> perks = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "dlc_id")
    private Dlc dlc;
}
