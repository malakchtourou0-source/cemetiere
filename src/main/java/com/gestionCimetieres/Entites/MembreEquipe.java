package com.gestionCimetieres.Entites;


import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "membre_equipe")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MembreEquipe {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nom;

    private String cin;

    @ManyToOne
    @JoinColumn(name = "equipe_id", nullable = false)
    private Equipe equipe;
}
