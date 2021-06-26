package com.jlcb.desafioprodutecbackend.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jlcb.desafioprodutecbackend.api.dto.ProdutoDTO;
import com.jlcb.desafioprodutecbackend.exception.RegraNegocioException;
import com.jlcb.desafioprodutecbackend.model.Empresa;
import com.jlcb.desafioprodutecbackend.model.Produto;
import com.jlcb.desafioprodutecbackend.model.Usuario;
import com.jlcb.desafioprodutecbackend.model.enums.Perfil;
import com.jlcb.desafioprodutecbackend.model.enums.Status;
import com.jlcb.desafioprodutecbackend.model.repository.EmpresaRepository;
import com.jlcb.desafioprodutecbackend.model.repository.ProdutoRepository;
import com.jlcb.desafioprodutecbackend.service.EmpresaService;
import com.jlcb.desafioprodutecbackend.service.ProdutoService;
import com.jlcb.desafioprodutecbackend.service.UsuarioService;

@Service
public class ProdutoServiceImpl implements ProdutoService {
	
	@Autowired
	private ProdutoRepository produtoRepository;
		
	@Autowired
	private EmpresaRepository empresaRepository;
	
	@Autowired
	private UsuarioService usuarioService;
	
	@Autowired
	private EmpresaService empresaService;

	@Override
	public List<Produto> listar(Usuario usuario) {
		
		List<Produto> produtos = new ArrayList<>();

		if (usuario.getPerfil() == Perfil.ADMINISTRADOR || usuario.getPerfil() == Perfil.GERENTE) {
			produtos = produtoRepository.findAll();
		} else if (usuario.getPerfil() == Perfil.USUARIO) {
			
			Empresa empresa = empresaRepository.findById(usuario.getEmpresa().getId()).orElseThrow();
			
			produtos = produtoRepository.findByEmpresa(empresa);
		}
		
		return produtos;
	}

	@Override
	@Transactional
	public Produto salvar(Produto produto) {
		return produtoRepository.save(produto);
	}

	@Override
	@Transactional
	public Produto atualizar(Produto produto) {
		
		Objects.requireNonNull(produto.getId()); 
		
		return produtoRepository.save(produto);
	}

	@Override
	public void deletar(Produto produto) {
		
		Objects.requireNonNull(produto.getId()); 
		
		produtoRepository.delete(produto);
	}

//	@Override
//	public List<Produto> buscar(Produto produtoFiltros) {
//		// TODO Auto-generated method stub
//		return null;
//	}

	@Override
	public void atulizarStatus(Produto produto, Status status) {
		
		produto.setStatus(status);
		
		atualizar(produto);
	}

	@Override
	public Optional<Produto> obterProdutoPorId(Long id) {
		return produtoRepository.findById(id);
	}
	
	@Override
	public Produto converterDtoParaProduto(ProdutoDTO dto) {
		
		Produto produto = new Produto();
		produto.setNome(dto.getNome());
		produto.setDescricao(dto.getDescricao());
		produto.setStatus(Status.ATIVO);
		
		
//		Preco preco = precoService.obterPrecoPorId(dto.getPrecoId()).orElseThrow(() -> new RegraDeNegocioException("Preço não encontrado para o id informado!"));
//		produto.setPreco(preco);
		
//		Colecao colecao = colecaoService.obterColecaoPorId(dto.getColecaoId()).orElseThrow(() -> new RegraDeNegocioException("Coleção não encontrado para o id informado!"));
//		produto.setColecao(colecao);
		
		Optional<Usuario> usuarioLogado = usuarioService.obterUsuarioPorId(dto.getUsuarioLogadoId());
		
		if (usuarioLogado.isPresent()) {
			
			if (usuarioLogado.get().getPerfil() == Perfil.GERENTE) {
				
				Empresa empresa = empresaService.obterEmpresaPorId(dto.getEmpresaId()).orElseThrow(() -> new RegraNegocioException("Empresa não encontrado para o id informado!"));
				produto.setEmpresa(empresa);
			} else if (usuarioLogado.get().getPerfil() == Perfil.USUARIO) {
				produto.setEmpresa(usuarioLogado.get().getEmpresa());
			}
			
		}
		
		return produto;
	}
	
	@Override
	public ProdutoDTO converterProdutoParaDto(Produto produto) {
		
		ProdutoDTO dto = new ProdutoDTO();
		dto.setId(produto.getId());
		dto.setNome(produto.getNome());
		dto.setDescricao(produto.getDescricao());
		dto.setStatus(produto.getStatus().name());
		dto.setPrecoId(produto.getPreco().getId());
		dto.setColecaoId(produto.getColecao().getId());
		dto.setEmpresaId(produto.getEmpresa().getId());
		
		return dto;
	}
}
