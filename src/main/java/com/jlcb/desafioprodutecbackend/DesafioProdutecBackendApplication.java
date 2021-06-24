package com.jlcb.desafioprodutecbackend;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.jlcb.desafioprodutecbackend.model.Gerente;
import com.jlcb.desafioprodutecbackend.model.enums.Perfil;
import com.jlcb.desafioprodutecbackend.model.repository.GerenteRepository;

@SpringBootApplication
@EnableWebMvc
public class DesafioProdutecBackendApplication implements WebMvcConfigurer, CommandLineRunner {
	
	@Override
	public void addCorsMappings(CorsRegistry registry) {
		registry.addMapping("/**").allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS");
	}
	
	public static void main(String[] args) {
		SpringApplication.run(DesafioProdutecBackendApplication.class, args);
	}
	
	@Autowired
	private GerenteRepository gerenteRepository;
	
	@Override
	public void run(String... args) throws Exception {
		
		
		Gerente gerente1 = new Gerente(null, "Gerente 1", "gerente@gmail.com", "123", Perfil.GERENTE);
		gerenteRepository.saveAll(Arrays.asList(gerente1));
	}
}
