package com.jlcb.desafioprodutecbackend.service;

import java.util.List;
import java.util.Optional;

import com.jlcb.desafioprodutecbackend.api.dto.ProdutoDTO;
import com.jlcb.desafioprodutecbackend.model.Produto;
import com.jlcb.desafioprodutecbackend.model.Usuario;
import com.jlcb.desafioprodutecbackend.model.enums.Status;

public interface ProdutoService {
	
	List<Produto> listar(Usuario usuario);	
	
	Produto salvar(Produto produto);

	Produto atualizar(Produto produto);

	void deletar(Produto produto);

//	List<Produto> buscar(Produto produtoFiltros);
	
	void atulizarStatus(Produto produto, Status status);
	
	Optional<Produto> obterProdutoPorId(Long id);
		
	Produto converterDtoParaProduto(ProdutoDTO dto);
	
	ProdutoDTO converterProdutoParaDto(Produto produto);
}
