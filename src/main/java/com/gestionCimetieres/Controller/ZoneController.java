package com.gestionCimetieres.Controller;

import com.gestionCimetieres.Entites.Zone;
import com.gestionCimetieres.Repository.ZoneRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import java.util.List;


@RestController
@RequestMapping("/api/zones")
@CrossOrigin(origins = "http://localhost:4200")
@RequiredArgsConstructor
public class ZoneController {

    private final ZoneRepository repository;

    // GET /api/zones
    @GetMapping
    public List<Zone> listerToutes() {
        return repository.findAll();
    }
}