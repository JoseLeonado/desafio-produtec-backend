package com.jlcb.desafioprodutecbackend.service.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.jlcb.desafioprodutecbackend.exception.AutenticacoException;
import com.jlcb.desafioprodutecbackend.exception.RegraNegocioException;
import com.jlcb.desafioprodutecbackend.model.Usuario;
import com.jlcb.desafioprodutecbackend.model.repository.UsuarioRepository;
import com.jlcb.desafioprodutecbackend.service.UsuarioService;

public class UsuarioServiceImpl implements UsuarioService {
	
	@Autowired
	private UsuarioRepository repository;

	@Override
	public Usuario autenticar(String email, String senha) {
		
		Optional<Usuario> usuario = repository.findByEmail(email);
		
		if (!usuario.isPresent()) {
			throw new AutenticacoException("Usuário não encontrado para o email informado");
		}

		if (!usuario.get().getSenha().equals(senha)) {
			throw new AutenticacoException("Senha inválida");
		}
		
		return usuario.get();
	}

	@Override
	@Transactional
	public Usuario salvar(Usuario usuario) {

		validarEmail(usuario.getEmail());
		
		return repository.save(usuario);
	}

	@Override
	public void validarEmail(String email) {
		
		boolean isExiste = repository.existsByEmail(email);
		
		if (isExiste) {
			throw new RegraNegocioException("Já existe um usuário cadastrado com esse email.");
		}
	}
}
