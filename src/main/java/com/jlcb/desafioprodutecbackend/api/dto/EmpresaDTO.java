package com.jlcb.desafioprodutecbackend.api.dto;

import javax.validation.constraints.NotBlank;

import org.hibernate.validator.constraints.br.CNPJ;

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
	
	@CNPJ(message = "Número do registro de contribuinte corporativo brasileiro (CNPJ) inválido")
	@NotBlank(message = "Informe o CNPJ da empresa!")
	private String cnpj;
	
	@NotBlank(message = "Informe a Razão social da empresa!")
	private String razaoSocial;
	
	@NotBlank(message = "Informe o Nome fantasia da empresa!")
	private String nomeFantasia;
}
