package com.gestionCimetieres.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DemandeTransfertRequest {

    private LocalDate date;
    private String    lieuRecuperation;
    private int       nombreCadavres;

    private String    numeroBureauOrdre;
    private LocalDate dateBureauOrdre;
    private String    numeroArchive;

    private Long      agentId;
    private Long      forceSecuriteId;
    private Long      zoneId;

    //en cas plusieurs cadavres
    private List<Long> cadavreIds;
}