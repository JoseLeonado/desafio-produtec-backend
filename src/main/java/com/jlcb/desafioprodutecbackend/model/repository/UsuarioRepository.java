package com.jlcb.desafioprodutecbackend.model.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.jlcb.desafioprodutecbackend.model.Empresa;
import com.jlcb.desafioprodutecbackend.model.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
	
	boolean existsByEmail(String email);
	
	Optional<Usuario> findByEmail(String email);
		
	@Query("SELECT u FROM Usuario u WHERE u.empresa = ?1 AND u.id <> ?2")
	List<Usuario> findByEmpresaAndUsuario(Empresa empresa, Long id);
	
	@Query("SELECT u FROM Usuario u WHERE u.id <> ?1")
	List<Usuario> findByAll(Long id);
}
