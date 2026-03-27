package com.gestionCimetieres.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DemandeTransfertResponse {
	
	
	//demande de transfert
    private Long      id;
    private LocalDate date;
    private String    lieuRecuperation;
    private int       nombreCadavres;
    private String    statut;               
    private String    numeroBureauOrdre;
    private LocalDate dateBureauOrdre;
    private String    numeroArchive;

    private String    agentNom;
    private String    agentPrenom;
    private String    forceSecuriteNom;
    private String    zoneNom;
    
    
    private List<String> cadavresNoms;      
// générer ordre de mission
    private Long oredreMissionId;
    private int   ordreMissionNumero;
    private Boolean   ordreMissionEmis;
}