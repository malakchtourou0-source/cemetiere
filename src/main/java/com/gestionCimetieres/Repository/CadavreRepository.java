package com.gestionCimetieres.Repository;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import com.gestionCimetieres.Entites.Cadavre;
import com.gestionCimetieres.Entites.Nationalite;

public interface CadavreRepository extends JpaRepository<Cadavre, Long> {
    List<Cadavre> findByNationalite(Nationalite nationalite);
}