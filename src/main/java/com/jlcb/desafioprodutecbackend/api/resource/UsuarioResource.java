package com.jlcb.desafioprodutecbackend.api.resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jlcb.desafioprodutecbackend.api.dto.UsuarioDTO;
import com.jlcb.desafioprodutecbackend.exception.RegraNegocioException;
import com.jlcb.desafioprodutecbackend.model.Usuario;
import com.jlcb.desafioprodutecbackend.model.enums.TipoStatus;
import com.jlcb.desafioprodutecbackend.service.UsuarioService;

@RestController
@RequestMapping("/usuarios")
public class UsuarioResource {
	
	@Autowired
	private UsuarioService usuarioService;

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
	
	private Usuario converterDtoParaUsuario(UsuarioDTO dto) {
		
		Usuario usuario = new Usuario();
		usuario.setId(dto.getId());
		usuario.setNome(dto.getNome());
		usuario.setEmail(dto.getEmail());
		usuario.setSenha(dto.getSenha());
				
		if (dto.getSenha() != null) {
			usuario.setStatus(TipoStatus.valueOf(dto.getStatus()));
		}
		
		return usuario;
	}
}
