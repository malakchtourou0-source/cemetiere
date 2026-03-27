package com.gestionCimetieres.Controller;
import com.gestionCimetieres.Entites.Cimetiere;
import com.gestionCimetieres.Entites.Tombe;
import com.gestionCimetieres.Service.IInhumationNormaleService;
import com.gestionCimetieres.dto.InhumationNormaleRequest;
import com.gestionCimetieres.dto.InhumationNormaleResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/inhumations-normales")
@CrossOrigin(origins = "http://localhost:4200")
@RequiredArgsConstructor
public class InhumationNormaleController {

    private final IInhumationNormaleService service;

    // GET /api/inhumations-normales/cimetieres
    @GetMapping("/cimetieres")
    public ResponseEntity<List<Cimetiere>> listerCimetieres() {
        return ResponseEntity.ok(service.listerCimetieres());
    }

    // GET /api/inhumations-normales/cimetieres/{id}/tombes
    @GetMapping("/cimetieres/{id}/tombes")
    public ResponseEntity<List<Tombe>> listerTombes(@PathVariable Long id) {
        return ResponseEntity.ok(service.listerTombesDisponibles(id));
    }

    // POST /api/inhumations-normales
    @PostMapping
    public ResponseEntity<InhumationNormaleResponse> enregistrer(
            @RequestBody InhumationNormaleRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.enregistrer(request));
    }

    // GET /api/inhumations-normales/{id}
    @GetMapping("/{id}")
    public ResponseEntity<InhumationNormaleResponse> consulter(@PathVariable Long id) {
        return ResponseEntity.ok(service.consulter(id));
    }
}

