package com.gestionCimetieres.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gestionCimetieres.Entites.Agent;


public interface AgentRepository extends JpaRepository<Agent, Long> {
    Optional<Agent> findByLogin(String login);
}