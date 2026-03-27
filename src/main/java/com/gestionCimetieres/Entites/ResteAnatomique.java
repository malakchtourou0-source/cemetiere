package com.gestionCimetieres.Entites;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "reste_anatomique")
@PrimaryKeyJoinColumn(name = "id")
@Data @EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
public abstract class ResteAnatomique extends ElementInhumable {

    @Enumerated(EnumType.STRING)
    @Column(name = "type_reste", nullable = false)
    private TypeReste typeReste; 
}