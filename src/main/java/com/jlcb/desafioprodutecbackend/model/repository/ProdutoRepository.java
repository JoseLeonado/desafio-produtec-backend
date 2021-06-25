package com.jlcb.desafioprodutecbackend.model.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.jlcb.desafioprodutecbackend.model.Empresa;
import com.jlcb.desafioprodutecbackend.model.Produto;

public interface ProdutoRepository extends JpaRepository<Produto, Long> {
		
	@Query("SELECT p FROM Produto p WHERE p.empresa = ?1")
	List<Produto> findByEmpresa(Empresa empresa);
}
