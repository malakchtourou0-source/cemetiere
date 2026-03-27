package com.gestionCimetieres;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
public class GestionCimetieresApplication {

	public static void main(String[] args) {
		SpringApplication.run(GestionCimetieresApplication.class, args);
	
	}
		
		@Bean
	    CommandLineRunner genererHash() {
	        return args -> {
	            BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
	            System.out.println("=== HASH admin123 : " + encoder.encode("admin123"));
	            System.out.println("=== HASH admin    : " + encoder.encode("admin"));
	        };
	    
	}

	}
