package com.gestionCimetieres.Service;

import com.gestionCimetieres.Entites.ForceSecurite;
import com.gestionCimetieres.Entites.Zone;
import com.gestionCimetieres.dto.DemandeTransfertRequest;
import com.gestionCimetieres.dto.DemandeTransfertResponse;
import com.gestionCimetieres.dto.OrdreMissionRequest;
import java.util.List;

public interface IDemandeTransfertService {

    // CRUD
    DemandeTransfertResponse       creer(DemandeTransfertRequest request);
    List<DemandeTransfertResponse> listerToutes();
    List<DemandeTransfertResponse> listerParStatut(String statut);
    DemandeTransfertResponse       consulter(Long id);
    DemandeTransfertResponse       mettreAJourStatut(Long id, String statut);
    void                           supprimer(Long id);

    // emettre un ordre de mission depuis une demande existante
    DemandeTransfertResponse       emettreOrdreMission(Long demandeId, OrdreMissionRequest request);

    // compléter,modifier un ordre de mission déjà émis
    DemandeTransfertResponse       completerOrdreMission(Long demandeId, OrdreMissionRequest request);

    // pour les formulaires angular
    List<ForceSecurite>            listerForces();
    List<Zone>                     listerZones();
}