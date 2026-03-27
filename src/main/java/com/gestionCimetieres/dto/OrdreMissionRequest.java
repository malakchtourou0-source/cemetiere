package com.gestionCimetieres.dto;


import lombok.AllArgsConstructor;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrdreMissionRequest {

    
    private String    lieuDepart;
    private LocalTime heureDepart;
    private String    lieuArrivee;    
    private LocalTime heureRetour;
    private LocalDate dateDepart;
    private LocalDate dateRetour;
    private String    Vehicule;
    private LocalDate          equipeDate;
    private List<MembreDTO>    membres;
    
    @Data
    public static class MembreDTO {
        private String nom;
        private String cin;
    }
}