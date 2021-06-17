package com.jlcb.desafioprodutecbackend.api.dto;

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
	private String nome;
	private String email;
	private String senha;
	private Long idEmpresa;
	private String status;
}
