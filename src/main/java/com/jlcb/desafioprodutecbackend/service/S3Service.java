package com.jlcb.desafioprodutecbackend.service;

import java.io.InputStream;
import java.net.URI;

import org.springframework.web.multipart.MultipartFile;

public interface S3Service {
	
	public URI uploadFoto(MultipartFile multipartFile);
	
	public URI uploadFoto(String nomDoArquivo, InputStream inputStream, String tipoDoArquivo);
}
