package com.gestionCimetieres.Entites;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Table(name = "permis_inhumation")
@Data @NoArgsConstructor @AllArgsConstructor
public class PermisInhumation {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "num_permis", nullable = false, unique = true)
    private int numPermis;

    @Column(name = "date_delivrance", nullable = false)
    private LocalDate dateDelivrance;

    @Column(name = "heure_deces")
    private LocalTime heureDeces;

    @Column(name = "nom_defunt")
    private String nomDefunt;

    @Column(name = "nom_pere")
    private String nomPere;

    @Column(name = "nom_mere")
    private String nomMere;

    @Column(name = "place_deces")
    private String placeDeces;

    @ManyToOne
    @JoinColumn(name = "cimetiere_id")
    private Cimetiere cimetiere;

    @OneToOne
    @JoinColumn(name = "element_inhumable_id", nullable = false)
    private ElementInhumable element;
}