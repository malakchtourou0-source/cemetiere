package com.gestionCimetieres.dto;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginResponse {
	
 private String token; 
 //donnée agent
 private String login;
 private String nom;
 private String prenom;
 private String fonction;
}

