package com.gestionCimetieres.Controller;
import com.gestionCimetieres.Entites.ForceSecurite;
import com.gestionCimetieres.Entites.Zone;
import com.gestionCimetieres.Service.IDemandeTransfertService;
import com.gestionCimetieres.dto.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/demandes-transfert")
@CrossOrigin(origins = "http://localhost:4200")
@RequiredArgsConstructor
public class DemandeTransfertController {

    private final IDemandeTransfertService service;

    //listes de référence

    // GET /api/demandes-transfert/forces
    @GetMapping("/forces")
    public ResponseEntity<List<ForceSecurite>> listerForces() {
        return ResponseEntity.ok(service.listerForces());
    }

    // GET /api/demandes-transfert/zones
    @GetMapping("/zones")
    public ResponseEntity<List<Zone>> listerZones() {
        return ResponseEntity.ok(service.listerZones());
    }

    // crud

    // POST /api/demandes-transfert
    @PostMapping
    public ResponseEntity<DemandeTransfertResponse> creer(
            @RequestBody DemandeTransfertRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.creer(request));
    }

    // GET /api/demandes-transfert
    @GetMapping
    public ResponseEntity<List<DemandeTransfertResponse>> listerToutes() {
        return ResponseEntity.ok(service.listerToutes());
    }

    // GET /api/demandes-transfert?statut=en coursٍ
    @GetMapping(params = "statut")
    public ResponseEntity<List<DemandeTransfertResponse>> listerParStatut(
            @RequestParam String statut) {
        return ResponseEntity.ok(service.listerParStatut(statut));
    }

    // GET /api/demandes-transfert/{id}
    @GetMapping("/{id}")
    public ResponseEntity<DemandeTransfertResponse> consulter(@PathVariable Long id) {
        return ResponseEntity.ok(service.consulter(id));
    }

    // PATCH /api/demandes-transfert/{id}/statut?valeur=complet
    @PatchMapping("/{id}/statut")
    public ResponseEntity<DemandeTransfertResponse> mettreAJourStatut(
            @PathVariable Long id, @RequestParam String valeur) {
        return ResponseEntity.ok(service.mettreAJourStatut(id, valeur));
    }

    // DELETE /api/demandes-transfert/{id}
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> supprimer(@PathVariable Long id) {
        service.supprimer(id);
        return ResponseEntity.noContent().build();
    }

    //ordre mission
    // POST /api/demandes-transfert/{id}/ordre-mission
    // nouvel rdre de mission 
    @PostMapping("/{id}/ordre-mission")
    public ResponseEntity<DemandeTransfertResponse> emettreOrdreMission(
            @PathVariable Long id,
            @RequestBody OrdreMissionRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(service.emettreOrdreMission(id, request));
    }

    // PUT /api/demandes-transfert/{id}/ordre-mission
    //modifier un ordre deja émis
    @PutMapping("/{id}/ordre-mission")
    public ResponseEntity<DemandeTransfertResponse> completerOrdreMission(
            @PathVariable Long id,
            @RequestBody OrdreMissionRequest request) {
        return ResponseEntity.ok(service.completerOrdreMission(id, request));
    }
}
