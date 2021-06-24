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

import com.jlcb.desafioprodutecbackend.api.dto.EmpresaDTO;
import com.jlcb.desafioprodutecbackend.exception.RegraNegocioException;
import com.jlcb.desafioprodutecbackend.model.Empresa;
import com.jlcb.desafioprodutecbackend.service.EmpresaService;

@RestController
@RequestMapping("/empresas")
public class EmpresaResource {
	
	@Autowired
	private EmpresaService empresaService;
	
	@GetMapping
	public ResponseEntity<?> listar() {
		
		List<Empresa> empresas = empresaService.listar();
		
		return ResponseEntity.ok(empresas);
	}
	
	@GetMapping("{id}")
	public ResponseEntity<?> obterEmpresaPorId(@PathVariable("id") Long id) {
		return empresaService.obterEmpresaPorId(id)
					.map(empresa -> new ResponseEntity<>(converterEmpresaParaDto(empresa), HttpStatus.OK))
					.orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
	}
		
	@PostMapping
	public ResponseEntity<?> salvar(@Valid @RequestBody EmpresaDTO dto) {
		
		Empresa empresa = converterDtoParaEmpresa(dto);
		
		try {
			
			Empresa empresaSalva = empresaService.salvar(empresa);

			return new ResponseEntity<>(empresaSalva, HttpStatus.CREATED);
			
		} catch (RegraNegocioException e) {
			return  ResponseEntity.badRequest().body(e.getMessage());
		}
	}
	
	@PutMapping("{id}") 
	public ResponseEntity<?> atualizar(@PathVariable("id") Long id, @Valid @RequestBody EmpresaDTO dto) {
		
		Optional<Empresa> empresaEncontrada = empresaService.obterEmpresaPorId(id);
		
		if (empresaEncontrada.isPresent()) {
			
			Empresa empresa = converterDtoParaEmpresa(dto);
			empresa.setId(empresaEncontrada.get().getId());
			
			empresaService.atualizar(empresa);
			
			return ResponseEntity.ok(empresa);
		} else {
			return new ResponseEntity<>("Empresa não encontrada.", HttpStatus.BAD_REQUEST);
		}
	}
	
	@DeleteMapping("{id}")
	public ResponseEntity<?> deletar (@PathVariable("id") Long id) {
		
		return empresaService.obterEmpresaPorId(id).map(empresaEncontrada -> { 
			
			empresaService.deletar(empresaEncontrada);
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}).orElseGet(() -> new ResponseEntity<>("Empresa não encontrado.", HttpStatus.BAD_REQUEST));
	}
	
	private EmpresaDTO converterEmpresaParaDto(Empresa empresa) {
		
		EmpresaDTO dto = new EmpresaDTO();
		dto.setId(empresa.getId());
		dto.setCnpj(empresa.getCnpj());
		dto.setRazaoSocial(empresa.getRazaoSocial());
		dto.setNomeFantasia(empresa.getNomeFantasia());
		
		return dto;
	}
		
	private Empresa converterDtoParaEmpresa(EmpresaDTO dto) {
		
		Empresa empresa = new Empresa();
		empresa.setCnpj(dto.getCnpj());
		empresa.setRazaoSocial(dto.getRazaoSocial());
		empresa.setNomeFantasia(dto.getNomeFantasia());
	
		return empresa;
	}
}
