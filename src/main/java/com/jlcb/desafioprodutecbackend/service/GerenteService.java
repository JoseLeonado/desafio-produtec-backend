package com.jlcb.desafioprodutecbackend.service;

import java.util.List;
import java.util.Optional;

import com.jlcb.desafioprodutecbackend.model.Gerente;

public interface GerenteService {
		
	Gerente salvar(Gerente gerente);
	
	void validarEmail(String email);
	
	Optional<Gerente> obterGerentePorId(Long id);

	void deletar(Gerente gerente);
		
	Gerente atualizar(Gerente gerente);
	
//	void deletar(Gerente gerente);
	
	List<Gerente> listar();	
}

