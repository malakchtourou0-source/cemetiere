package com.gestionCimetieres.Entites;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "cadavre_connu")
@PrimaryKeyJoinColumn(name = "id")
@Data @EqualsAndHashCode(callSuper = true)
@NoArgsConstructor @AllArgsConstructor
public class CadavreConnu extends Cadavre {

    @Column(nullable = false)
    private String nom;

    @Column(nullable = false)
    private String prenom;

    private int age;
    private String cin;
}