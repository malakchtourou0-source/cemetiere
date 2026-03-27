package com.gestionCimetieres.Entites;

import jakarta.persistence.*;


import lombok.*;

@Entity
@Table(name = "force_securite")
@Data @NoArgsConstructor @AllArgsConstructor
public class ForceSecurite {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nom_unite", nullable = false)
    private String nomUnite;

    private String tel;

    @Column(name = "type_force")
    private String typeForce; 
}
