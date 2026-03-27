package com.gestionCimetieres.Service;

import com.gestionCimetieres.Entites.Agent;
import com.gestionCimetieres.Repository.AgentRepository;
import com.gestionCimetieres.dto.LoginRequest;
import com.gestionCimetieres.dto.LoginResponse;
import com.gestionCimetieres.Security.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService implements IAuthService {

   private final AuthenticationManager authManager;
   private final AgentRepository       agentRepository;
   private final JwtUtil               jwtUtil;
   private final PasswordEncoder passwordEncoder; 


    @Override
    public LoginResponse login(LoginRequest req) {
    	
        /*System.out.println("=== Tentative login : " + req.getLogin());
        System.out.println("=== Mot de passe reçu : " + req.getMotDePasse());*/

        Agent agent = agentRepository.findByLogin(req.getLogin())
                .orElseThrow(() -> new RuntimeException("Login ou mot de passe incorrect"));
        
        
        if (!passwordEncoder.matches(req.getMotDePasse(), agent.getMotDePasse()))
            throw new RuntimeException("Login ou mot de passe incorrect");

        String token = jwtUtil.genererToken(agent.getLogin());

        return new LoginResponse(token, agent.getLogin(), agent.getNom(),
                                 agent.getPrenom(), agent.getFonction());
    }
}
