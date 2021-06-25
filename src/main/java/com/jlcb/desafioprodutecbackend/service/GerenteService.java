package com.jlcb.desafioprodutecbackend.service;

import java.util.List;
import java.util.Optional;

import com.jlcb.desafioprodutecbackend.api.dto.GerenteDTO;
import com.jlcb.desafioprodutecbackend.model.Gerente;

public interface GerenteService {
	
	List<Gerente> listar();	
		
	Gerente salvar(Gerente gerente);
	
	void validarEmail(String email);
	
	Optional<Gerente> obterGerentePorId(Long id);

	void deletar(Gerente gerente);
		
	Gerente atualizar(Gerente gerente);
	
	GerenteDTO converterGerenteParaDto(Gerente gerente);
	
	Gerente converterDtoParaGerente(GerenteDTO dto);
}

