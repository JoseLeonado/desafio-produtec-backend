package com.jlcb.desafioprodutecbackend.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jlcb.desafioprodutecbackend.model.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

	boolean existsByEmail(String email);
}
