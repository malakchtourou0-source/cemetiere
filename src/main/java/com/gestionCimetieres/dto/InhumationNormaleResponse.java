package com.gestionCimetieres.dto;


import lombok.Data;
import java.time.LocalDate;

import com.gestionCimetieres.Entites.TypeMotif;

@Data
public class InhumationNormaleResponse {
    private Long      cadavreId;
    private String    typeCadavre;
    private String    nomComplet;
    private LocalDate dateDeces;
    private String    placeDeces;
    private String    nationalite;
    private String    causeDecesLibelle;
    private int       tombeNumero;
    private String    tombeTaille;
    private String    cimetiereNom;
    private Long      permisId;
    private int       numPermis;
    private LocalDate dateDelivrance;
    private String    agentNom;
    private Long      quittanceId;
    private String    numeroQuittance;
    private TypeMotif    motifQuittance;
    }