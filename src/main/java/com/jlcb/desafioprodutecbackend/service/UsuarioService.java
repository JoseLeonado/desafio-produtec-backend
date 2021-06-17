package com.jlcb.desafioprodutecbackend.service;

import com.jlcb.desafioprodutecbackend.model.Usuario;

public interface UsuarioService {

	Usuario autenticar(String email, String senha);
	
	Usuario salvar(Usuario usuario);
	
	void validarEmail(String email);
}

