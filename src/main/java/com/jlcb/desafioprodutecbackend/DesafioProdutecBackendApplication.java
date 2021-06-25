package com.jlcb.desafioprodutecbackend;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.jlcb.desafioprodutecbackend.model.Empresa;
import com.jlcb.desafioprodutecbackend.model.Gerente;
import com.jlcb.desafioprodutecbackend.model.Usuario;
import com.jlcb.desafioprodutecbackend.model.enums.Perfil;
import com.jlcb.desafioprodutecbackend.model.repository.EmpresaRepository;
import com.jlcb.desafioprodutecbackend.model.repository.GerenteRepository;
import com.jlcb.desafioprodutecbackend.model.repository.UsuarioRepository;

@SpringBootApplication
@EnableWebMvc
public class DesafioProdutecBackendApplication implements WebMvcConfigurer, CommandLineRunner {
		
	public static void main(String[] args) {
		SpringApplication.run(DesafioProdutecBackendApplication.class, args);
	}
	
	@Autowired
	private GerenteRepository gerenteRepository;
	
	@Autowired
	private EmpresaRepository empresaRepository;
	
	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@Override
	public void run(String... args) throws Exception {
				
		Gerente gerente1 = new Gerente(null, "Gerente 1", "gerente@gmail.com", "123", Perfil.GERENTE);
		gerenteRepository.saveAll(Arrays.asList(gerente1));
		
		Empresa empresa1 = new Empresa(null, "80.076.593/0001-09", "Empresa 1", "Empresa 1");
		Empresa empresa2 = new Empresa(null, "80.350.055/0001-60", "Empresa 2", "Empresa 2");
		empresaRepository.saveAll(Arrays.asList(empresa1, empresa2));
		
		Usuario usuario_1_da_empresa_1 = new Usuario(null, "Usuario 1 da empresa 1", "usuario_1_da_empresa_1@gmail.com", "123", Perfil.USUARIO, empresaRepository.getById(1L));
		Usuario usuario_2_da_empresa_1 = new Usuario(null, "Usuario 2 da empresa 1", "usuario_2_da_empresa_1@gmail.com", "123", Perfil.USUARIO, empresaRepository.getById(1L));
		Usuario usuario_3_da_empresa_1 = new Usuario(null, "Usuario 3 da empresa 1", "usuario_3_da_empresa_1@gmail.com", "123", Perfil.USUARIO, empresaRepository.getById(1L));
		Usuario usuario_4_da_empresa_1 = new Usuario(null, "Usuario 4 da empresa 1", "usuario_4_da_empresa_1@gmail.com", "123", Perfil.USUARIO, empresaRepository.getById(1L));
		Usuario usuario_5_da_empresa_1 = new Usuario(null, "Usuario 5 da empresa 1", "usuario_5_da_empresa_1@gmail.com", "123", Perfil.USUARIO, empresaRepository.getById(1L));
		
		Usuario usuario_1_da_empresa_2 = new Usuario(null, "Usuario 1 da empresa 2", "usuario_1_da_empresa_2@gmail.com", "123", Perfil.USUARIO, empresaRepository.getById(2L));
		Usuario usuario_2_da_empresa_2 = new Usuario(null, "Usuario 2 da empresa 2", "usuario_2_da_empresa_2@gmail.com", "123", Perfil.USUARIO, empresaRepository.getById(2L));
		usuarioRepository.saveAll(Arrays.asList(usuario_1_da_empresa_1, usuario_2_da_empresa_1, usuario_3_da_empresa_1, usuario_4_da_empresa_1, usuario_5_da_empresa_1, usuario_1_da_empresa_2, usuario_2_da_empresa_2));
	}
}
