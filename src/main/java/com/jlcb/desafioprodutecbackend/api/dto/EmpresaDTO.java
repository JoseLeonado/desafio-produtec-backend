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
public class EmpresaDTO {
	
	private Long id;
	
	@NotBlank(message = "Informe o cnpj da empresa!")
	private String cnpj;
	
	@NotBlank(message = "Informe a razão social da empresa!")
	private String razaoSocial;
	
	@NotBlank(message = "Informe o nome fantasia da empresa!")
	private String nomeFantasia;
}
