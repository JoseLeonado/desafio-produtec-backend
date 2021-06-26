package com.jlcb.desafioprodutecbackend.api.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProdutoDTO {
	
	private String nome;
	
	private String descricao;	
	
	private String status;
	
//	@NotNull(message = "Informe a tabela de preço do produto!")
	private Long precoId;
	
	private Long id;
	
	private Long usuarioLogadoId;
	
	private Long colecaoId;
	
	private Long empresaId;
}
