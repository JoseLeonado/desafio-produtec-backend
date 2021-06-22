package com.jlcb.desafioprodutecbackend.api.resource;

import java.util.List;

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
	
	@GetMapping
	public ResponseEntity<?> listar(Long idUsuario) {
		
				
		Usuario usuarioEncontrado = usuarioService.buscarUsuarioPorId(idUsuario).orElseThrow(); 
		
		if (usuarioEncontrado == null) {
			return ResponseEntity.badRequest().body("Não foi possível realizar a consulta. Usuário não encontrado para o id informado");
		} 
		
		List<Usuario> usuarios = usuarioService.listar(usuarioEncontrado);
		
		return ResponseEntity.ok(usuarios);
	}
	
	@PostMapping
	public ResponseEntity<?> salvar(@Valid @RequestBody UsuarioDTO dto) {
		
		Usuario usuario = converterDtoParaUsuario(dto);
		
		try {
			
			Usuario usuarioSalvo = usuarioService.salvar(usuario);
			
			return new ResponseEntity<>(usuarioSalvo, HttpStatus.CREATED);
			
		} catch (RegraNegocioException e) {
			return  ResponseEntity.badRequest().body(e.getMessage());
		}
	}
	
//	@GetMapping
//	public ResponseEntity<?> buscar(
//			@RequestParam(value = "nome", required = false) String nome,
//			@RequestParam(value = "email", required = false) String email,
//			@RequestParam("usuario") Long idUsuario
//			) {
//		
//		Usuario usuarioFiltro = new Usuario();
//		usuarioFiltro.setNome(nome);
//		usuarioFiltro.setEmail(email);
//		
//		Optional<Usuario> usuarioEncontrado = usuarioService.buscarUsuarioPorId(idUsuario); 
//		
//		if (!usuarioEncontrado.isPresent()) {
//			return ResponseEntity.badRequest().body("Não foi possível realizar a consulta. Usuário não encontrado para o id informado");
//		} else {
//			usuarioFiltro.setEmpresa(usuarioFiltro.getEmpresa());
//		}
//		
//		List<Usuario> usuarios = usuarioService.buscar(usuarioFiltro);
//		
//		return ResponseEntity.ok(usuarios);
//	}
	
	@PutMapping("{id}") 
	public ResponseEntity<?> atualizar(@PathVariable("id") Long id, @Valid @RequestBody UsuarioDTO dto) {
		
		return usuarioService.buscarUsuarioPorId(id).map(usuarioEncontrado -> { 
			
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
		
		if (dto.getPerfil() != null) {
			usuario.setPerfil(Perfil.valueOf(dto.getPerfil()));
		}
		
		if (dto.getEmpresa() != null) {
			Empresa empresa = empresaService.obterEmpresaPorId(dto.getEmpresa()).orElseThrow(() -> new RegraNegocioException("Empresa não encontrada	 para o id informado"));
			usuario.setEmpresa(empresa);
		}
				
		return usuario;
	}
}
