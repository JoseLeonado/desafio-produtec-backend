package com.jlcb.desafioprodutecbackend.service;

import java.awt.image.BufferedImage;
import java.io.InputStream;

import org.springframework.web.multipart.MultipartFile;

public interface ImagemService {

	public BufferedImage getImageJpg(MultipartFile uploadedFile);
	
	public BufferedImage pngParaJpg(BufferedImage imagem);
		
	public InputStream getInputStream(BufferedImage imagem, String extensao);
}
