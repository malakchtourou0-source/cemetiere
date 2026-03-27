package com.gestionCimetieres.Entites;
import jakarta.persistence.*;
import lombok.*;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "cimetiere")
@Data @NoArgsConstructor @AllArgsConstructor
public class Cimetiere {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nom;

    private String adresse;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TypeCimetiere type; 

    @OneToMany(mappedBy = "cimetiere", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Tombe> tombes;
}
