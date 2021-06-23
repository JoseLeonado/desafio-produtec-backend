package com.jlcb.desafioprodutecbackend.service;

import java.util.List;
import java.util.Optional;

import com.jlcb.desafioprodutecbackend.model.Empresa;

public interface EmpresaService {
	
	Empresa salvar(Empresa empresa);
	
	void validarCnpj(String cnpj);
	
	Empresa atualizar(Empresa empresa);
	
	void deletar(Empresa empresa);

	List<Empresa> listar();
	
	Optional<Empresa> obterEmpresaPorId(Long id);
}

