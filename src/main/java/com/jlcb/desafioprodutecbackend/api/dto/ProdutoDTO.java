package com.jlcb.desafioprodutecbackend.api.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProdutoDTO {
	
	private Long id;

	@NotBlank(message = "Informe o nome do gerente!")
	private String nome;

	@NotBlank(message = "Informe o email do gerente!")
	@Email(message = "Informe um email válido!")
	private String email;

	@NotBlank(message = "Informe a senha do gerente!")
	private String senha;
	
	private Long usuarioLogadoId;
	
	private Long empresaId;
	
	private String empresaNome;
	
}
