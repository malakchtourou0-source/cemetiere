package com.gestionCimetieres.Entites;


import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;

@Entity
@Table(name = "demande_enlevement")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DemandeEnlevement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "date_operation", nullable = false)
    private LocalDate dateOperation;

    @Column(name = "nom_responsable")
    private String nomResponsable;

    @Column(nullable = false)
    private String statut = "جارٍ"; // جارٍ | منجز | معلّق

    @ManyToOne
    @JoinColumn(name = "agent_id")
    private Agent agent;

    @ManyToOne
    @JoinColumn(name = "etablissement_id")
    private Etablissement etablissement;

    // ✅ Placenta hérite maintenant de ElementInhumable directement
    @OneToOne
    @JoinColumn(name = "placenta_id", nullable = false)
    private Placenta placenta;

    // ✅ Relation vers QuittancePaiement (remplace numeroQuittance String)
    @OneToOne(mappedBy = "demandeEnlevement", cascade = CascadeType.ALL)
    private QuittancePaiement quittance;
}