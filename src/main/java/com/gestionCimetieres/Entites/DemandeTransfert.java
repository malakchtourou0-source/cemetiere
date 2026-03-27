package com.gestionCimetieres.Entites;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "demande_transfert")
@Data @NoArgsConstructor @AllArgsConstructor
public class DemandeTransfert {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private LocalDate date;

    @Column(name = "lieu_recuperation")
    private String lieuRecuperation;

    @Column(name = "nombre_cadavres", nullable = false)
    private int nombreCadavres = 1;

    @Column(nullable = false)
    private String statut = "جارٍ"; // جارٍ | منجز | معلّق

    @Column(name = "numero_bureau_ordre")
    private String numeroBureauOrdre;

    @Column(name = "date_bureau_ordre")
    private LocalDate dateBureauOrdre;

    @Column(name = "numero_archive")
    private String numeroArchive;

    @ManyToOne
    @JoinColumn(name = "zone_id")
    private Zone zone;

    @ManyToOne
    @JoinColumn(name = "agent_id")
    private Agent agent;

    @ManyToOne
    @JoinColumn(name = "force_securite_id")
    private ForceSecurite forceSecurite;

    @ManyToMany
    @JoinTable(
        name = "demande_transfert_cadavres",
        joinColumns = @JoinColumn(name = "demande_transfert_id"),
        inverseJoinColumns = @JoinColumn(name = "cadavre_id")
    )
    private List<Cadavre> cadavres = new ArrayList<>();
}
