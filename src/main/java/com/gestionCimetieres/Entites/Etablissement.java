package com.gestionCimetieres.Entites;

import jakarta.persistence.*;
import lombok.*;
@Entity
@Table(name = "etablissement")
@Inheritance(strategy = InheritanceType.JOINED)
@Data @NoArgsConstructor @AllArgsConstructor
public abstract class Etablissement {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nom;

    private String adresse;
    private String tel;
    private String fax;
}
