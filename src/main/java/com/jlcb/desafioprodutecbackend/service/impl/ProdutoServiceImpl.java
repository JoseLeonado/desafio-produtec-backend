package com.jlcb.desafioprodutecbackend.service.impl;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.jlcb.desafioprodutecbackend.model.Empresa;
import com.jlcb.desafioprodutecbackend.model.Produto;
import com.jlcb.desafioprodutecbackend.model.Usuario;
import com.jlcb.desafioprodutecbackend.model.enums.Perfil;
import com.jlcb.desafioprodutecbackend.model.enums.Status;
import com.jlcb.desafioprodutecbackend.model.repository.EmpresaRepository;
import com.jlcb.desafioprodutecbackend.model.repository.ProdutoRepository;
import com.jlcb.desafioprodutecbackend.service.ProdutoService;
import com.jlcb.desafioprodutecbackend.service.S3Service;

@Service
public class ProdutoServiceImpl implements ProdutoService {
	
	@Autowired
	private ProdutoRepository produtoRepository;
		
	@Autowired
	private EmpresaRepository empresaRepository;
	
	@Autowired
	private S3Service s3Service;
	
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
	public URI uploadImagem(MultipartFile multipartFile) {
		return s3Service.uploadFoto(multipartFile);
	}
}
