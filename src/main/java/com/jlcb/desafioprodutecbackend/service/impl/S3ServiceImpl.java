package com.jlcb.desafioprodutecbackend.service.impl;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.jlcb.desafioprodutecbackend.service.S3Service;

@Service
public class S3ServiceImpl implements S3Service {
	
	private Logger LOG = LoggerFactory.getLogger(S3ServiceImpl.class);

	@Value("${s3.bucket}")
	private String bucketNome;
	
	@Autowired
	private AmazonS3 s3Client;
	

	public URI uploadFoto(MultipartFile multipartFile) {
		
		try {

			String nomeDoArquivo = multipartFile.getOriginalFilename();
			InputStream inputStream = multipartFile.getInputStream();
			String tipoDoArquivo = multipartFile.getContentType();
			
			return uploadFoto(nomeDoArquivo, inputStream, tipoDoArquivo);
		} catch (IOException e) {
			throw new RuntimeException("Erro: " + e.getMessage());
		}
	}

	public URI uploadFoto(String nomDoArquivo, InputStream inputStream, String tipoDoArquivo) {

		try {

			ObjectMetadata objectMetadata = new ObjectMetadata();
			objectMetadata.setContentType(tipoDoArquivo);

			LOG.info("Iniciando upload");

			s3Client.putObject(new PutObjectRequest(bucketNome, nomDoArquivo, inputStream, objectMetadata));

			LOG.info("Upload finalizado");

			return s3Client.getUrl(bucketNome, nomDoArquivo).toURI();
		} catch (URISyntaxException e) {
			throw new RuntimeException("Erro ao converter URL para URI");
		}

	}
	
}
