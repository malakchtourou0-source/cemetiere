package com.gestionCimetieres.Controller;

import com.gestionCimetieres.Entites.ForceSecurite;
import com.gestionCimetieres.Repository.ForceSecuriteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import java.util.List;


@RestController
@RequestMapping("/api/forces-securite")
@CrossOrigin(origins = "http://localhost:4200")
@RequiredArgsConstructor
public class ForceSecuriteController {

    private final ForceSecuriteRepository repository;

    // GET /api/forces-securite
    @GetMapping
    public List<ForceSecurite> listerToutes() {
        return repository.findAll();
    }
}
