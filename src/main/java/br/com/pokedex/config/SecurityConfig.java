package br.com.pokedex.config;

import java.util.HashSet;
import java.util.List;

import org.eclipse.microprofile.jwt.Claims;

import io.smallrye.jwt.build.Jwt;

public class SecurityConfig {
	
	public static String generateToken() {
		return Jwt.issuer("").upn("jdoe@quarkus.io")
		  .groups(new HashSet<>(List.of("User", "Admin")))
		  .claim(Claims.birthdate.name(), "2023-09-19")
		  .sign();
	}
}
