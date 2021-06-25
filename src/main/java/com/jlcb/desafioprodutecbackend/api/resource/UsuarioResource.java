package com.jlcb.desafioprodutecbackend.api.resource;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jlcb.desafioprodutecbackend.api.dto.LoginDTO;
import com.jlcb.desafioprodutecbackend.api.dto.UsuarioDTO;
import com.jlcb.desafioprodutecbackend.exception.AutenticacoException;
import com.jlcb.desafioprodutecbackend.exception.RegraNegocioException;
import com.jlcb.desafioprodutecbackend.model.Empresa;
import com.jlcb.desafioprodutecbackend.model.Usuario;
import com.jlcb.desafioprodutecbackend.model.enums.Perfil;
import com.jlcb.desafioprodutecbackend.service.EmpresaService;
import com.jlcb.desafioprodutecbackend.service.UsuarioService;

@RestController
@CrossOrigin
@RequestMapping("/usuarios")
public class UsuarioResource {
	
	@Autowired
	private UsuarioService usuarioService;
	
	@Autowired
	private EmpresaService empresaService;
	
	@PostMapping("/autenticar")
	public ResponseEntity<?> autenticar(@RequestBody LoginDTO dto) {
		
		try {
			
			Usuario usuarioAutenticado = usuarioService.autenticar(dto.getEmail(), dto.getSenha());
			
			return ResponseEntity.ok(usuarioAutenticado);
			
		} catch (AutenticacoException e) {
			return  ResponseEntity.badRequest().body(e.getMessage());
		}
	}
	
	@PostMapping("/da-empresa")
	public ResponseEntity<?> listar(@RequestBody UsuarioDTO dto) {
				
		Usuario usuarioEncontrado = usuarioService.obterUsuarioPorId(dto.getId()).orElseThrow(); 
		
		if (usuarioEncontrado == null) {
			return ResponseEntity.badRequest().body("Não foi possível realizar a listagem. Usuário não encontrado para o id informado");
		} 
		
		List<Usuario> usuarios = usuarioService.listar(usuarioEncontrado);
		
		return ResponseEntity.ok(usuarios);
	}
	
	@GetMapping("{id}")
	public ResponseEntity<?> obterUsuarioPorId(@PathVariable("id") Long id) {
		return usuarioService.obterUsuarioPorId(id)
					.map(usuario -> new ResponseEntity<>(usuarioService.converterUsuarioParaDto(usuario), HttpStatus.OK))
					.orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
	}
	
	@PostMapping
	public ResponseEntity<?> salvar(@Valid @RequestBody UsuarioDTO dto) {
		
		Usuario usuario = usuarioService.converterDtoParaUsuario(dto);
		
		try {
			
			Usuario usuarioSalvo = usuarioService.salvar(usuario);
			
			return new ResponseEntity<>(usuarioSalvo, HttpStatus.CREATED);
			
		} catch (RegraNegocioException e) {
			return  ResponseEntity.badRequest().body(e.getMessage());
		}
	}
	
	@PutMapping("{id}") 
	public ResponseEntity<?> atualizar(@PathVariable("id") Long id, @Valid @RequestBody UsuarioDTO dto) {
		
		Optional<Usuario> usuarioEncontrado = usuarioService.obterUsuarioPorId(id);
		
		if (usuarioEncontrado.isPresent()) {
			
			Usuario usuario = usuarioService.converterDtoParaUsuario(dto);
			usuario.setId(usuarioEncontrado.get().getId());
			
			usuarioService.atualizar(usuario);
			
			return ResponseEntity.ok(usuario);
		} else {
			return new ResponseEntity<>("Usuario não encontrado!", HttpStatus.BAD_REQUEST);
		}
	}
	
	@DeleteMapping("{id}")
	public ResponseEntity<?> deletar (@PathVariable("id") Long id) {
		
		return usuarioService.obterUsuarioPorId(id).map(usuarioEncontrado -> { 
			
			usuarioService.deletar(usuarioEncontrado);
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}).orElseGet(() -> new ResponseEntity<>("Usuário não encontrado.", HttpStatus.BAD_REQUEST));
	}
}
