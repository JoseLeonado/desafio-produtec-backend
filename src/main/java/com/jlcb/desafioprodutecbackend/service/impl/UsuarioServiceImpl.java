package com.jlcb.desafioprodutecbackend.service.impl;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.ExampleMatcher.StringMatcher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jlcb.desafioprodutecbackend.exception.AutenticacoException;
import com.jlcb.desafioprodutecbackend.exception.RegraNegocioException;
import com.jlcb.desafioprodutecbackend.model.Usuario;
import com.jlcb.desafioprodutecbackend.model.repository.UsuarioRepository;
import com.jlcb.desafioprodutecbackend.service.UsuarioService;

@Service
public class UsuarioServiceImpl implements UsuarioService {
	
	@Autowired
	private UsuarioRepository usuarioRepository;

	@Override
	public Usuario autenticar(String email, String senha) {
		
		Optional<Usuario> usuario = usuarioRepository.findByEmail(email);
		
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
		
		return usuarioRepository.save(usuario);
	}

	@Override
	public void validarEmail(String email) {
		
		boolean isExiste = usuarioRepository.existsByEmail(email);
		
		if (isExiste) {
			throw new RegraNegocioException("Já existe um usuário cadastrado com esse email.");
		}
	}

	@Override
	@Transactional
	public Usuario atualizar(Usuario usuario) {
		
		Objects.requireNonNull(usuario.getId()); 
		
		return usuarioRepository.save(usuario);
	}

	@Override
	public void deletar(Usuario usuario) {
	
		Objects.requireNonNull(usuario.getId()); 
		
		usuarioRepository.delete(usuario);
	}

	@Override
	@Transactional
	public List<Usuario> buscar(Usuario usuario) {

		Example<Usuario> example = Example.of(usuario, 
				ExampleMatcher.matching()
				.withIgnoreCase() 
				.withStringMatcher(StringMatcher.CONTAINING)); 
		
		return usuarioRepository.findAll(example);
	}

	@Override
	public Optional<Usuario> buscarUsuarioPorId(Long id) {
		return usuarioRepository.findById(id);
	}
}
