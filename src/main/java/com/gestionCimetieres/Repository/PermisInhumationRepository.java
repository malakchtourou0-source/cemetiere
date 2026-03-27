package com.gestionCimetieres.Repository;
import com.gestionCimetieres.Entites.PermisInhumation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.Optional;

public interface PermisInhumationRepository extends JpaRepository<PermisInhumation, Long> {

    Optional<PermisInhumation> findByNumPermis(int numPermis);

    // Générer le prochain numéro de permis
    @Query("SELECT COALESCE(MAX(p.numPermis), 5000) + 1 FROM PermisInhumation p")
    int genererProchainNumero();
}