package com.gestionCimetieres.dto;


import com.gestionCimetieres.Entites.TypeMotif;
import lombok.Data;
import java.time.LocalDate;

@Data
public class QuittancePaiementRequest {
    private LocalDate  date;
    private String     numero;
    private String     personnePayeur;
    private TypeMotif  motif;
    private Long       agentId;
    private Long       permisInhumationId;
    private Long       demandeEnlevementId;
    private Long       certificatMedicalId;
}