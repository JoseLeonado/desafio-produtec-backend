package com.jlcb.desafioprodutecbackend.api.dto;

import javax.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UsuarioDTO {
	
	private Long id;
	
	@NotBlank(message = "Informe o nome do usuário.")
	private String nome;
	
	@NotBlank(message = "Informe o email do usuário.")
	private String email;
	
	@NotBlank(message = "Informe a senha do usuário.")
	private String senha;
	private Long idEmpresa;
}
