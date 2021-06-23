package com.jlcb.desafioprodutecbackend.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jlcb.desafioprodutecbackend.model.Empresa;

public interface EmpresaRepository extends JpaRepository<Empresa, Long> {
	
	boolean existsByCnpj(String cnpj);

}
