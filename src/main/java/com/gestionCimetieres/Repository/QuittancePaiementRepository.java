package com.gestionCimetieres.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gestionCimetieres.Entites.QuittancePaiement;

public interface QuittancePaiementRepository extends JpaRepository<QuittancePaiement, Long> {
	
	Optional<QuittancePaiement> findByNumero(String numero);
    Optional<QuittancePaiement> findByPermisInhumationId(Long permisId);
    Optional<QuittancePaiement> findByDemandeEnlevementId(Long demandeId);
}
