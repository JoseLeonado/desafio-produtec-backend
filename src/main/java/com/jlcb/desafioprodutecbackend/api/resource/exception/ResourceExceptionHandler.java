package com.jlcb.desafioprodutecbackend.api.resource.exception;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.s3.model.AmazonS3Exception;
import com.jlcb.desafioprodutecbackend.service.exception.FileException;

@ControllerAdvice
public class ResourceExceptionHandler {
		
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<ErroPadrao> methodArgumentNotValidException(MethodArgumentNotValidException e, HttpServletRequest request){
		
		ErroRequisicao erro = new ErroRequisicao(HttpStatus.UNPROCESSABLE_ENTITY.value(), "A requisição possui campos inválidos", converter(request.getMethod()), request.getRequestURI());
		
		for(FieldError filedError : e.getBindingResult().getFieldErrors()) {
			erro.addErro(filedError.getField(), filedError.getDefaultMessage());
		}
		return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(erro);
	}
	
	@ExceptionHandler(FileException.class)
	public ResponseEntity<ErroPadrao> file(FileException e, HttpServletRequest request){
		
		ErroPadrao erro = new ErroPadrao(HttpStatus.BAD_REQUEST.value(), "Erro de arquivo", converter(request.getMethod()), request.getRequestURI());
		
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(erro);
	}
	
	@ExceptionHandler(AmazonServiceException .class)
	public ResponseEntity<ErroPadrao> amazonService(AmazonServiceException  e, HttpServletRequest request){
		
		HttpStatus codigo = HttpStatus.valueOf(e.getErrorCode());
		ErroPadrao erro = new ErroPadrao(codigo.value(), "Erro Amazon Service", converter(request.getMethod()), request.getRequestURI());
		
		return ResponseEntity.status(codigo).body(erro);
	}
	
	@ExceptionHandler(AmazonClientException.class)
	public ResponseEntity<ErroPadrao> amazonCliente(AmazonClientException e, HttpServletRequest request){
		
		ErroPadrao erro = new ErroPadrao(HttpStatus.BAD_REQUEST.value(), "Erro Amazon Client", converter(request.getMethod()), request.getRequestURI());
		
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(erro);
	}
	
	@ExceptionHandler(AmazonS3Exception .class)
	public ResponseEntity<ErroPadrao> amazonS3(AmazonS3Exception  e, HttpServletRequest request){
		
		ErroPadrao erro = new ErroPadrao(HttpStatus.BAD_REQUEST.value(), "Erro S3", converter(request.getMethod()), request.getRequestURI());

		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(erro);
	}
	
	private String converter(String string) {
		return string.substring(0,1).toUpperCase() + string.substring(1).toLowerCase();
	}
}
