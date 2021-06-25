package com.jlcb.desafioprodutecbackend.api.resource.exception;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ErroCampoRequisicao implements Serializable {
	
	private static final long serialVersionUID = 1L;

	private String campo;
	private String erro;
}
