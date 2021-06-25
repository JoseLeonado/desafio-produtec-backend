package com.jlcb.desafioprodutecbackend.api.resource.exception;

import java.util.ArrayList;
import java.util.List;

public class ErroRequisicao extends ErroPadrao {
	private static final long serialVersionUID = 1L;
	
	private List<ErroCampoRequisicao> erros = new ArrayList<>();

	public ErroRequisicao(Integer status, String error, String metodo, String caminho) {
		super(status, error, metodo, caminho);
	}

	public List<ErroCampoRequisicao> getErrors() {
		return erros;
	}

	public void addErro(String fieldName, String message) {
		erros.add(new ErroCampoRequisicao(fieldName, message));
	}
}