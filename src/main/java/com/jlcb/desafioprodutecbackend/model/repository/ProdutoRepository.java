package com.jlcb.desafioprodutecbackend.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jlcb.desafioprodutecbackend.model.Produto;

public interface ProdutoRepository extends JpaRepository<Produto, Long> {

}
