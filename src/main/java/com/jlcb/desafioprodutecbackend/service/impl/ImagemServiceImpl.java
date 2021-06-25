package com.jlcb.desafioprodutecbackend.service.impl;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;

import org.apache.commons.io.FilenameUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.jlcb.desafioprodutecbackend.service.ImagemService;
import com.jlcb.desafioprodutecbackend.service.exception.FileException;

@Service
public class ImagemServiceImpl implements ImagemService {

	@Override
	public BufferedImage getImageJpg(MultipartFile uploadedImagem) {

		String extensaoDoArquivo = FilenameUtils.getExtension(uploadedImagem.getOriginalFilename());
		
			if (!extensaoDoArquivo.equals("png") && !extensaoDoArquivo.equals("jpg")) {
				throw new FileException("Somente imagens PNG e JPG são permitidas!");
			}
			
			try {
				
				BufferedImage imagem = ImageIO.read(uploadedImagem.getInputStream());
				
				if (extensaoDoArquivo.equals("png")) {
					imagem = pngParaJpg(imagem);
				}
				
				return imagem;
			} catch (IOException e) {
				throw new FileException("Erro ao ler imagem");
			}
		
	}

	@Override
	public BufferedImage pngParaJpg(BufferedImage imagem) {
		
		BufferedImage imagemJpg = new BufferedImage(imagem.getWidth(), imagem.getHeight(), BufferedImage.TYPE_INT_RGB);
		imagemJpg.createGraphics().drawImage(imagem, 0, 0, Color.WHITE, null);
		
		return imagemJpg;
	}
	
	@Override
	public InputStream getInputStream(BufferedImage imagem, String extensao) {
		
		try {
			
			ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
			ImageIO.write(imagem, extensao, outputStream);
			
			return new ByteArrayInputStream(outputStream.toByteArray());
		} catch (Exception e) {
			throw new FileException("Erro ao ler imagem");
		}
	}
}
