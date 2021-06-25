package com.jlcb.desafioprodutecbackend.api.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProdutoDTO {
	
	@NotBlank(message = "Informe o nome do produto!")
	private String nome;
	
	@NotBlank(message = "Informe a descrição do produto!")
	private String descricao;	
	
	@NotBlank(message = "Informe o status do produto!")
	private String status;
	
	@NotNull(message = "Informe a tabela de preço do produto!")
	private Long precoId;
	
	private Long id;
	
	private Long usuarioLogadoId;
	
	private Long colecaoId;
	
	private Long empresaId;
	
}
