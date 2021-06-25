package com.jlcb.desafioprodutecbackend.api.resource.exception;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ErroPadrao implements Serializable {
	
	private static final long serialVersionUID = 1L;

	private Integer status;
	private String error;
	private String metodo;
	private String caminho;
}