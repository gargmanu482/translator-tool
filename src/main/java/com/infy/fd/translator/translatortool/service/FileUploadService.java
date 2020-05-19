package com.infy.fd.translator.translatortool.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.infy.fd.translator.translatortool.controller.TranslatorController;

/* NOT USED -- CAN BE DELETED*/

@Service
public class FileUploadService {

	/*
	 * @Value("${fileupload-destination}") 
	 * private String fileDestination;
	 */
	private String fileDestination=System.getProperty("user.home");
	private Logger logger = LoggerFactory.getLogger(TranslatorController.class);

	public void store(MultipartFile file) {
	        try {

	            // Get the file and save it somewhere
	            byte[] bytes = file.getBytes();
	            Path path = Paths.get(fileDestination + file.getOriginalFilename());
	            Files.write(path, bytes);
	        } catch (IOException e) {
	            e.printStackTrace();
	        }

		}

}