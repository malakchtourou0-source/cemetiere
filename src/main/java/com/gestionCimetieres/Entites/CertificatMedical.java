package com.gestionCimetieres.Entites;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Entity
@Table(name = "certificat_medical")
@Inheritance(strategy = InheritanceType.JOINED)
@Data @NoArgsConstructor @AllArgsConstructor
public abstract class CertificatMedical {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "date_certificat")
    private LocalDate dateCertificat;

    @Column(name = "date_operation")
    private LocalDate dateOperation;

    @Column(name = "medecin_responsable")
    private String medecinResponsable;

    @Column(name = "personne_concernee")
    private String personneConcernee;

    private String signature;

    @Column(name = "numero_quittance")
    private String numeroQuittance;

    @Column(name = "date_quittance")
    private LocalDate dateQuittance;

    @OneToOne
    @JoinColumn(name = "organe_id")
    private Organe organe;

    @OneToOne
    @JoinColumn(name = "embryon_id")
    private Embryon embryon;
}