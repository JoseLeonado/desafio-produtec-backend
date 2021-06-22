package com.jlcb.desafioprodutecbackend.api.resource;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jlcb.desafioprodutecbackend.api.dto.GerenteDTO;
import com.jlcb.desafioprodutecbackend.exception.RegraNegocioException;
import com.jlcb.desafioprodutecbackend.model.Gerente;
import com.jlcb.desafioprodutecbackend.model.enums.Perfil;
import com.jlcb.desafioprodutecbackend.service.GerenteService;

@RestController
@RequestMapping("/gerentes")
public class GerenteResource {
	
	@Autowired
	private GerenteService gerenteService;
		
	@GetMapping
	public ResponseEntity<?> listar() {
		
		List<Gerente> gerentes = gerenteService.listar();
		
		return ResponseEntity.ok(gerentes);
	}
	
	@PostMapping
	public ResponseEntity<?> salvar(@RequestBody GerenteDTO dto) {
		
		Gerente gerente = converterDtoParaGerente(dto);
		
		try {
			
			Gerente gerenteSalvo = gerenteService.salvar(gerente);
			
			return new ResponseEntity<>(gerenteSalvo, HttpStatus.CREATED);
			
		} catch (RegraNegocioException e) {
			return  ResponseEntity.badRequest().body(e.getMessage());
		}
	}
	
	@DeleteMapping("{id}")
	public ResponseEntity<?> deletar (@PathVariable("id") Long id) {
		
		return gerenteService.obterGerentePorId(id).map(lancamentoEncontrado -> { 
			
			gerenteService.deletar(lancamentoEncontrado);
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}).orElseGet(() -> new ResponseEntity<>("Lançamento não encontrado.", HttpStatus.BAD_REQUEST));
	}
	
	private Gerente converterDtoParaGerente(GerenteDTO dto) {
		
		Gerente gerente = new Gerente();
		gerente.setNome(dto.getNome());
		gerente.setEmail(dto.getEmail());
		gerente.setSenha(dto.getSenha());
		gerente.setEmpresa(null);
		gerente.setPerfil(Perfil.GERENTE);
		
		return gerente;
	}
}
