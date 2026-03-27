package com.gestionCimetieres.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gestionCimetieres.Entites.MembreEquipe;

public interface MembreEquipeRepository extends JpaRepository<MembreEquipe, Long> {
    List<MembreEquipe> findByEquipeId(Long equipeId);
}
