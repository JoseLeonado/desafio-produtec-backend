package com.jlcb.desafioprodutecbackend.service.impl;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jlcb.desafioprodutecbackend.api.dto.GerenteDTO;
import com.jlcb.desafioprodutecbackend.exception.RegraNegocioException;
import com.jlcb.desafioprodutecbackend.model.Gerente;
import com.jlcb.desafioprodutecbackend.model.enums.Perfil;
import com.jlcb.desafioprodutecbackend.model.repository.GerenteRepository;
import com.jlcb.desafioprodutecbackend.model.repository.UsuarioRepository;
import com.jlcb.desafioprodutecbackend.service.GerenteService;

@Service
public class GerenteServiceImpl implements GerenteService {
	
	@Autowired
	private GerenteRepository gerenteRepository;
	
	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@Override
	public List<Gerente> listar() {
		return gerenteRepository.findAll();
	}
	
	@Override
	public Gerente salvar(Gerente gerente) {

		validarEmail(gerente.getEmail());
		
		return gerenteRepository.save(gerente);
	}
	
	@Override
	@Transactional
	public Gerente atualizar(Gerente gerente) {
		
		Objects.requireNonNull(gerente.getId()); 
		
		return gerenteRepository.save(gerente);
	}
	
	@Override
	public void validarEmail(String email) {
		
		boolean isExiste = usuarioRepository.existsByEmail(email);
		
		if (isExiste) {
			throw new RegraNegocioException("Já existe um gerente cadastrado com esse email. Sendo assim, informe outro email!");
		}
	}
	
	@Override
	public void deletar(Gerente gerente) {

		Objects.requireNonNull(gerente);

		gerenteRepository.delete(gerente);
	}

	
	@Override
	public Optional<Gerente> obterGerentePorId(Long id) {
		return gerenteRepository.findById(id);
	}
	
	public GerenteDTO converterGerenteParaDto(Gerente gerente) {
		
		GerenteDTO dto = new GerenteDTO();
		dto.setId(gerente.getId());
		dto.setNome(gerente.getNome());
		dto.setEmail(gerente.getEmail());
		dto.setSenha(gerente.getSenha());
		
		return dto;
	}
	
	public Gerente converterDtoParaGerente(GerenteDTO dto) {
		
		Gerente gerente = new Gerente();
		gerente.setNome(dto.getNome());
		gerente.setEmail(dto.getEmail());
		gerente.setSenha(dto.getSenha());
		gerente.setEmpresa(null);
		gerente.setPerfil(Perfil.GERENTE);
		
		return gerente;
	}
}
