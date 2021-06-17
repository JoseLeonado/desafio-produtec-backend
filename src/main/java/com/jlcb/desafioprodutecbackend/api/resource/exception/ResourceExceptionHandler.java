package com.jlcb.desafioprodutecbackend.api.resource.exception;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class ResourceExceptionHandler extends ResponseEntityExceptionHandler {


	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {

		List<AtributoErroMensagem> errors = getErros(ex);
		RequisicaoErroResposta errorResponse = getErroResposta(status, errors);

		return new ResponseEntity<>(errorResponse, status);
	}

	private RequisicaoErroResposta getErroResposta(HttpStatus status, List<AtributoErroMensagem> errors) {
		return new RequisicaoErroResposta("A requisição possui campos inválidos", status.value(),
				status.getReasonPhrase(), errors);
	}

	private List<AtributoErroMensagem> getErros(MethodArgumentNotValidException ex) {
		return ex.getBindingResult().getFieldErrors().stream().map(error -> new AtributoErroMensagem(error.getField(),
				error.getRejectedValue(), error.getDefaultMessage())).collect(Collectors.toList());
	}
}
