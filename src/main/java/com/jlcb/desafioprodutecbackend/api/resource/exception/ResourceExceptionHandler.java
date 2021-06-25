package com.jlcb.desafioprodutecbackend.api.resource.exception;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ResourceExceptionHandler {
		
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<ErroPadrao> methodArgumentNotValidException(MethodArgumentNotValidException e, HttpServletRequest request){
		
		Validacao erro = new Validacao(HttpStatus.UNPROCESSABLE_ENTITY.value(), "A requisição possui campos inválidos", converter(request.getMethod()), request.getRequestURI());
		
		for(FieldError filedError : e.getBindingResult().getFieldErrors()) {
			erro.addErro(filedError.getField(), filedError.getDefaultMessage());
		}
		return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(erro);
	}
	
	private String converter(String string) {
		return string.substring(0,1).toUpperCase() + string.substring(1).toLowerCase();
	}
}
