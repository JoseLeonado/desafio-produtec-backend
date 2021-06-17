package com.jlcb.desafioprodutecbackend.service.impl;

import org.springframework.beans.factory.annotation.Autowired;

import com.jlcb.desafioprodutecbackend.exception.RegraNegocioException;
import com.jlcb.desafioprodutecbackend.model.Usuario;
import com.jlcb.desafioprodutecbackend.model.repository.UsuarioRepository;
import com.jlcb.desafioprodutecbackend.service.UsuarioService;

public class UsuarioServiceImpl implements UsuarioService {
	
	@Autowired
	private UsuarioRepository repository;

	@Override
	public Usuario autenticar(String email, String senha) {

		return null;
	}

	@Override
	public Usuario salvar(Usuario usuario) {

		return null;
	}

	@Override
	public void validarEmail(String email) {
		
		boolean isExiste = repository.existsByEmail(email);
		
		if (isExiste) {
			throw new RegraNegocioException("Já existe um usuário cadastrado com esse email.");
		}

	}
}
