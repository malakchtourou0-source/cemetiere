package com.gestionCimetieres.Entites;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "cause_deces")
@Data @NoArgsConstructor @AllArgsConstructor
public class CauseDeces {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String libelle;
}