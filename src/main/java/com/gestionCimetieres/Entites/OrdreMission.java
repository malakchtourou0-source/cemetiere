package com.gestionCimetieres.Entites;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Table(name = "ordre_mission")
@Data @NoArgsConstructor @AllArgsConstructor
public class OrdreMission {
	 @Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    private Long id;

	    @Column(nullable = false, unique = true)
	    private int numero;

	    @Column(name = "date_depart")
	    private LocalDate dateDepart;

	    @Column(name = "date_retour")
	    private LocalDate dateRetour;

	    @Column(name = "heure_depart")
	    private LocalTime heureDepart;

	    @Column(name = "heure_retour")
	    private LocalTime heureRetour;

	    private String vehicule;

	    @Column(name = "lieu_depart")
	    private String lieuDepart;

	    @Column(name = "lieu_arrivee")
	    private String lieuArrivee;

	    @ManyToOne
	    @JoinColumn(name = "equipe_id")
	    private Equipe equipe;

	    @OneToOne
	    @JoinColumn(name = "demande_transfert_id", nullable = false)
	    private DemandeTransfert demandeTransfert;
}
