package com.jlcb.desafioprodutecbackend.api.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AutenticaUsuarioDTO {
	
	private String email;
	private String senha;
}
