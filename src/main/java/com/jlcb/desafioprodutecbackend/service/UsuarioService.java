package com.jlcb.desafioprodutecbackend.service;

import java.util.List;
import java.util.Optional;

import com.jlcb.desafioprodutecbackend.model.Usuario;

public interface UsuarioService {

	Usuario autenticar(String email, String senha);
	
	Usuario salvar(Usuario usuario);
	
	void validarEmail(String email);
	
	Usuario atualizar(Usuario usuario);
	
	void deletar(Usuario usuario);
	
	List<Usuario> buscar(Usuario usuario);
	
	Optional<Usuario> buscarUsuarioPorId(Long id);
}

