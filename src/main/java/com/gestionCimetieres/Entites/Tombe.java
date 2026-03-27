package com.gestionCimetieres.Entites;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "tombe")
@Data @NoArgsConstructor @AllArgsConstructor
public class Tombe {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private int numero;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TailleTombe taille;

    @Column(nullable = false)
    private Boolean occupe = false;

    @ManyToOne
    @JoinColumn(name = "cimetiere_id", nullable = false)
    private Cimetiere cimetiere;
}