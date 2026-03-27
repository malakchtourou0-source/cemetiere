package com.gestionCimetieres.Repository;

import com.gestionCimetieres.Entites.OrdreMission;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


@Repository
public interface OrdreMissionRepository extends JpaRepository<OrdreMission, Long> {
	
    Optional<OrdreMission> findByDemandeTransfertId(Long demandeTransfertId);

    Optional<OrdreMission> findByNumero(int numero);
    //générer numéro ordre auto
    @Query("SELECT COALESCE(MAX(o.numero), 1000) + 1 FROM OrdreMission o")
    int genererProchainNumero();
}
