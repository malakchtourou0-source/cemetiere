package com.gestionCimetieres.Repository;

import com.gestionCimetieres.Entites.DemandeTransfert;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;


public interface DemandeTransfertRepository extends JpaRepository<DemandeTransfert, Long> {

    // Rechercher par statut 
    List<DemandeTransfert> findByStatut(String statut);

    // Rechercher par agent
    List<DemandeTransfert> findByAgentId(Long agentId);

    // Rechercher par force de sécurité
    List<DemandeTransfert> findByForceSecuriteId(Long forceSecuriteId);

    // Rechercher par zone
    List<DemandeTransfert> findByZoneId(Long zoneId);

    // Rechercher par période
    List<DemandeTransfert> findByDateBetween(LocalDate debut, LocalDate fin);

    // Rechercher par statut et période
    List<DemandeTransfert> findByStatutAndDateBetween(String statut, LocalDate debut, LocalDate fin);

    // Compter par statut (rapport)
    long countByStatut(String statut);

    // Compter par période (rapport)
    @Query("SELECT COUNT(d) FROM DemandeTransfert d WHERE d.date BETWEEN :debut AND :fin")
    long countByPeriode(@Param("debut") LocalDate debut, @Param("fin") LocalDate fin);
}
