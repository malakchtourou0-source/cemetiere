package com.gestionCimetieres.dto;

import lombok.Data;
import java.time.LocalDate;
import java.time.LocalTime;

import com.gestionCimetieres.Entites.TypeMotif;

@Data
public class InhumationNormaleRequest {

    // Défunt
    private String    typeCadavre;
    private String    nom;
    private String    prenom;
    private int       age;
    private String    cin;
    private String    numeroHebergement;
    private String    numeroMedicoLegal;
    private LocalDate dateSortieServiceMedical;
    private LocalDate dateDeces;
    private String    placeDeces;
    private String    nationalite;
    private Long      causeDecesId;

    // Tombe
    private Long      tombeId;
    private Long      cimetiereId;

    // Permis
    private LocalDate dateDelivrance;
    private LocalTime heureDeces;
    private String    nomDefunt;
    private String    nomPere;
    private String    nomMere;

    private String    numeroQuittance;
    private String    personnePayeur;
    private TypeMotif motif;           

    private Long      agentId;
}