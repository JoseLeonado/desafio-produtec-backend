package com.jlcb.desafioprodutecbackend.service.impl;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jlcb.desafioprodutecbackend.api.dto.EmpresaDTO;
import com.jlcb.desafioprodutecbackend.exception.RegraNegocioException;
import com.jlcb.desafioprodutecbackend.model.Empresa;
import com.jlcb.desafioprodutecbackend.model.repository.EmpresaRepository;
import com.jlcb.desafioprodutecbackend.service.EmpresaService;

@Service
public class EmpresaServiceImpl implements EmpresaService {

	@Autowired
	private EmpresaRepository empresaRepository;
	
	@Override
	public List<Empresa> listar() {
		return empresaRepository.findAll();
	}
	
	@Override
	@Transactional
	public Empresa salvar(Empresa empresa) {
		
		validarCnpj(empresa.getCnpj());
		
		return empresaRepository.save(empresa);
	}
	
	@Override
	public void validarCnpj(String cnpj) {
		
		boolean isCnpjExiste = empresaRepository.existsByCnpj(cnpj);
		
		if (isCnpjExiste) {
			throw new RegraNegocioException("Já existe uma empresa cadastrada com esse CNPJ. Sendo assim, informe outro CNPJ!");
		}
	}

	@Override
	@Transactional
	public Empresa atualizar(Empresa empresa) {
		
		Objects.requireNonNull(empresa.getId()); 
		
		return empresaRepository.save(empresa);
	}

	@Override
	public void deletar(Empresa empresa) {
	
		Objects.requireNonNull(empresa.getId()); 
		
		empresaRepository.delete(empresa);
	}

//	@Override
//	@Transactional
//	public List<Usuario> buscar(Usuario usuario) {
//
//		Example<Usuario> example = Example.of(usuario, 
//				ExampleMatcher.matching()
//				.withIgnoreCase() 
//				.withStringMatcher(StringMatcher.CONTAINING)); 
//		
//		return empresaRepository.findAll(example);
//	}
//
	@Override
	public Optional<Empresa> obterEmpresaPorId(Long id) {
		return empresaRepository.findById(id);
	}
	
	@Override
	public EmpresaDTO converterEmpresaParaDto(Empresa empresa) {
		
		EmpresaDTO dto = new EmpresaDTO();
		dto.setId(empresa.getId());
		dto.setCnpj(empresa.getCnpj());
		dto.setRazaoSocial(empresa.getRazaoSocial());
		dto.setNomeFantasia(empresa.getNomeFantasia());
		
		return dto;
	}
		
	@Override
	public Empresa converterDtoParaEmpresa(EmpresaDTO dto) {
		
		Empresa empresa = new Empresa();
		empresa.setCnpj(dto.getCnpj());
		empresa.setRazaoSocial(dto.getRazaoSocial());
		empresa.setNomeFantasia(dto.getNomeFantasia());
	
		return empresa;
	}
}
