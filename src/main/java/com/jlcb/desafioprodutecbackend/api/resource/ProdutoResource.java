package com.jlcb.desafioprodutecbackend.api.resource;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.jlcb.desafioprodutecbackend.api.dto.ProdutoDTO;
import com.jlcb.desafioprodutecbackend.api.dto.UsuarioDTO;
import com.jlcb.desafioprodutecbackend.exception.RegraNegocioException;
import com.jlcb.desafioprodutecbackend.model.Produto;
import com.jlcb.desafioprodutecbackend.model.Usuario;
import com.jlcb.desafioprodutecbackend.service.ProdutoService;
import com.jlcb.desafioprodutecbackend.service.S3Service;
import com.jlcb.desafioprodutecbackend.service.UsuarioService;

@RestController
@CrossOrigin
@RequestMapping("/produtos")
public class ProdutoResource {
	
	@Autowired
	private ProdutoService produtoService;
	
	@Autowired
	private UsuarioService usuarioService;
	
	@Autowired
	private S3Service s3Service;
		
	@PostMapping("/da-empresa")
	public ResponseEntity<?> listar(@RequestBody UsuarioDTO dto) {
				
		Usuario usuarioEncontrado = usuarioService.obterUsuarioPorId(dto.getId()).orElseThrow(); 
		
		if (usuarioEncontrado == null) {
			return ResponseEntity.badRequest().body("Não foi possível realizar a listagem. Usuário não encontrado para o id informado!");
		} 
		
		List<Produto> produtos = produtoService.listar(usuarioEncontrado);
		
		return ResponseEntity.ok(produtos);
	}
	
	@GetMapping("{id}")
	public ResponseEntity<?> obterUsuarioPorId(@PathVariable("id") Long id) {
		return produtoService.obterProdutoPorId(id)
					.map(produto -> new ResponseEntity<>(produtoService.converterProdutoParaDto(produto), HttpStatus.OK))
					.orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
	}
	
	@PostMapping
	public ResponseEntity<?> salvar(@RequestParam(name = "file") MultipartFile file,  @ModelAttribute ProdutoDTO dto) {
		
		
		try {
			
			Produto produtoSalvo = produtoService.converterDtoParaProduto(dto);
			URI uri = s3Service.uploadFoto(file);
			produtoSalvo.setFotoUrl(uri.toString());
			produtoService.salvar(produtoSalvo);
			
			return new ResponseEntity<>(produtoSalvo, HttpStatus.CREATED);
			
		} catch (RegraNegocioException e) {
			return  ResponseEntity.badRequest().body(e.getMessage());
		}
	}
	
	@PutMapping("{id}") 
	public ResponseEntity<?> atualizar(@PathVariable("id") Long id, @Valid @RequestBody ProdutoDTO dto) {
		
		Optional<Produto> produtoEncontrado = produtoService.obterProdutoPorId(id);
		
		if (produtoEncontrado.isPresent()) {
			
			Produto produto = produtoService.converterDtoParaProduto(dto);
			produto.setId(id);
			
			produtoService.atualizar(produto);
			
			return ResponseEntity.ok(produto);
		} else {
			return new ResponseEntity<>("Produto não encontrado!", HttpStatus.BAD_REQUEST);
		}
	}
	
	@DeleteMapping("{id}")
	public ResponseEntity<?> deletar (@PathVariable("id") Long id) {
		
		return produtoService.obterProdutoPorId(id).map(produtoEncontrado -> { 
			
			produtoService.deletar(produtoEncontrado);
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}).orElseGet(() -> new ResponseEntity<>("Produto não encontrado!", HttpStatus.BAD_REQUEST));
	}
}
