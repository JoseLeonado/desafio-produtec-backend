package com.jlcb.desafioprodutecbackend.api.resource;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jlcb.desafioprodutecbackend.api.dto.AutenticaUsuarioDTO;
import com.jlcb.desafioprodutecbackend.api.dto.UsuarioDTO;
import com.jlcb.desafioprodutecbackend.exception.AutenticacoException;
import com.jlcb.desafioprodutecbackend.exception.RegraNegocioException;
import com.jlcb.desafioprodutecbackend.model.Usuario;
import com.jlcb.desafioprodutecbackend.service.UsuarioService;

@RestController
@RequestMapping("/usuarios")
public class UsuarioResource {
	
	@Autowired
	private UsuarioService usuarioService;
	
	@PostMapping("/autenticar")
	public ResponseEntity<?> autenticar(@RequestBody AutenticaUsuarioDTO dto) {
		
		try {
			
			Usuario usuarioAutenticado = usuarioService.autenticar(dto.getEmail(), dto.getSenha());
			
			return ResponseEntity.ok(usuarioAutenticado);
			
		} catch (AutenticacoException e) {
			return  ResponseEntity.badRequest().body(e.getMessage());
		}
	}

	@PostMapping
	public ResponseEntity<?> salvar(@RequestBody UsuarioDTO dto) {
		
		Usuario usuario = converterDtoParaUsuario(dto);
		
		try {
			
			Usuario usuarioSalvo = usuarioService.salvar(usuario);
			
			return new ResponseEntity<>(usuarioSalvo, HttpStatus.CREATED);
			
		} catch (RegraNegocioException e) {
			return  ResponseEntity.badRequest().body(e.getMessage());
		}
	}
	
	@PutMapping("{id}") /* Editar */
	public ResponseEntity<?> atualizar(@PathVariable("id") Long id, @Valid @RequestBody UsuarioDTO dto) {
		
		return usuarioService.buscarUsuarioPorId(id).map(usuarioEncontrado -> { /* Caso encontre um lançamento pelo id, então iremos atualiza o mesmo */
			
			try {
				
				Usuario usuario =converterDtoParaUsuario(dto);
				usuario.setId(usuarioEncontrado.getId());
				usuarioService.atualizar(usuario);
				
				return ResponseEntity.ok(usuario);
			} catch (RegraNegocioException e) {
				return ResponseEntity.badRequest().body(e.getMessage());
			}
		}).orElseGet(() -> new ResponseEntity<>("Usuário não encontrado.", HttpStatus.BAD_REQUEST)); 
	}
	
	@DeleteMapping("{id}")
	public ResponseEntity<?> deletar(@PathVariable("id") Long id) {
		
		return usuarioService.buscarUsuarioPorId(id).map(usuarioEncontrado -> {
			
			usuarioService.deletar(usuarioEncontrado);
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}).orElseGet(() -> new ResponseEntity<>("Usuário não encontrado.", HttpStatus.BAD_REQUEST));
		
	}
	
	
	private Usuario converterDtoParaUsuario(UsuarioDTO dto) {
		
		Usuario usuario = new Usuario();
		usuario.setId(dto.getId());
		usuario.setNome(dto.getNome());
		usuario.setEmail(dto.getEmail());
		usuario.setSenha(dto.getSenha());
				
		return usuario;
	}
}
