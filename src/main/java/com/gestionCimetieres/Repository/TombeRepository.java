package com.gestionCimetieres.Repository;

import com.gestionCimetieres.Entites.Tombe;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface TombeRepository extends JpaRepository<Tombe, Long> {

    // Tombes disponibles (non occupées) dans un cimetière
    List<Tombe> findByCimetiereIdAndOccupeFalse(Long cimetiereId);

    // Toutes les tombes d'un cimetière
    List<Tombe> findByCimetiereId(Long cimetiereId);
}