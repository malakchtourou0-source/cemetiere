package com.gestionCimetieres.Security;

import com.gestionCimetieres.Entites.Agent;
import com.gestionCimetieres.Repository.AgentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
@RequiredArgsConstructor
public class AgentDetailsService implements UserDetailsService {

    private final AgentRepository agentRepository;

    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        Agent agent = agentRepository.findByLogin(login)
                .orElseThrow(() -> new UsernameNotFoundException("Agent introuvable : " + login));

        return new org.springframework.security.core.userdetails.User(
                agent.getLogin(),
                agent.getMotDePasse(),
                Collections.emptyList() 
        );
    }
}
