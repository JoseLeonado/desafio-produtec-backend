package com.jlcb.desafioprodutecbackend.service.exception;

public class FileException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public FileException(String msg) {
		super(msg);
	}
	
	public FileException(String msg, Throwable causa) {
		super(msg, causa);
	}
}
