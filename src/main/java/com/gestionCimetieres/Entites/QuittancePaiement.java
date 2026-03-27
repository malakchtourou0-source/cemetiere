package com.gestionCimetieres.Entites;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;

@Entity
@Table(name = "quittance_paiement")
@Data @NoArgsConstructor @AllArgsConstructor
public class QuittancePaiement {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private LocalDate date;

    @Column(nullable = false, unique = true)
    private String numero;

    @Column(name = "personne_payeur")
    private String personnePayeur;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TypeMotif motif;

    @ManyToOne
    @JoinColumn(name = "permis_inhumation_id")
    private PermisInhumation permisInhumation;

    @ManyToOne
    @JoinColumn(name = "demande_enlevement_id")
    private DemandeEnlevement demandeEnlevement;

    @ManyToOne
    @JoinColumn(name = "certificat_medical_id")
    private CertificatMedical certificatMedical;

    @ManyToOne
    @JoinColumn(name = "agent_id")
    private Agent agent;
}