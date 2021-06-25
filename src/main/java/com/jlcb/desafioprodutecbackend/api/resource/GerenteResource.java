package com.jlcb.desafioprodutecbackend.api.resource;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
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
	
	@GetMapping("{id}")
	public ResponseEntity<?> obterGerentePorId(@PathVariable("id") Long id) {
		return gerenteService.obterGerentePorId(id)
					.map(gerente -> new ResponseEntity<>(converterGerenteParaDto(gerente), HttpStatus.OK))
					.orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
	}
		
	@PostMapping
	public ResponseEntity<?> salvar(@Valid @RequestBody GerenteDTO dto) {
		
		Gerente gerente = converterDtoParaGerente(dto);
		
		try {
			
			Gerente gerenteSalvo = gerenteService.salvar(gerente);
			
			return new ResponseEntity<>(gerenteSalvo, HttpStatus.CREATED);
			
		} catch (RegraNegocioException e) {
			return  ResponseEntity.badRequest().body(e.getMessage());
		}
	}
	
	@PutMapping("{id}") 
	public ResponseEntity<?> atualizar(@PathVariable("id") Long id, @Valid @RequestBody GerenteDTO dto) {
		
		Optional<Gerente> gerenteEncontrado = gerenteService.obterGerentePorId(id);
		
		if (gerenteEncontrado.isPresent()) {
			
			Gerente gerente = converterDtoParaGerente(dto);
			gerente.setId(gerenteEncontrado.get().getId());
			
			gerenteService.atualizar(gerente);
			
			return ResponseEntity.ok(gerente);
		} else {
			return new ResponseEntity<>("Gerente não encontrado", HttpStatus.BAD_REQUEST);
		}
	}
	
	@DeleteMapping("{id}")
	public ResponseEntity<?> deletar (@PathVariable("id") Long id) {
		
		return gerenteService.obterGerentePorId(id).map(lancamentoEncontrado -> { 
			
			gerenteService.deletar(lancamentoEncontrado);
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}).orElseGet(() -> new ResponseEntity<>("Gerente não encontrado.", HttpStatus.BAD_REQUEST));
	}
	
	private GerenteDTO converterGerenteParaDto(Gerente gerente) {
		
		GerenteDTO dto = new GerenteDTO();
		dto.setId(gerente.getId());
		dto.setNome(gerente.getNome());
		dto.setEmail(gerente.getEmail());
		dto.setSenha(gerente.getSenha());
		
		return dto;
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
