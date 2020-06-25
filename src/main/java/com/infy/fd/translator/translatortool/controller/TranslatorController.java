package com.infy.fd.translator.translatortool.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.infy.fd.translator.translatortool.model.Login;
import com.infy.fd.translator.translatortool.repositoy.LoginRepository;
import com.infy.fd.translator.translatortool.service.FileUploadService;

@RestController
public class TranslatorController {

	private Logger logger = LoggerFactory.getLogger(TranslatorController.class);
	
	@Autowired
	LoginRepository loginrepo;

	@Autowired
	FileUploadService fileUploadService;

	@GetMapping("/uploadPage")
	public ModelAndView getPage(HttpServletRequest request, HttpServletResponse response) throws Exception {
		logger.info("Inside Login Controller");
		ModelAndView model = new ModelAndView("Home");
		//model.addObject("msg", "hello world");
		return model;
	}
	
	
	@PostMapping("/login")
	public ResponseEntity<String> validateValues(@RequestParam("name") String name, @RequestParam("password") String password){
		Login login =null;
		try {
			login=loginrepo.findByName(name);
			if(login.getPassword().equals(password)) {
				return new ResponseEntity<String>(HttpStatus.OK);}
			else
				return new ResponseEntity<String>(HttpStatus.BAD_REQUEST);
		}
		catch(Exception e) {
			return new ResponseEntity<String>(HttpStatus.BAD_REQUEST);
		}
	}
	
	 @GetMapping("/home") 
	 public ModelAndView getMessage(HttpServletRequest request, HttpServletResponse response) throws Exception {
	  logger.info("Inside Controller"); 
	  ModelAndView model = new ModelAndView("Login"); 
	  model.addObject("msg", "hello world"); 
	  return model; 
	  }
	  
	 
	
	@GetMapping("/test")
	public ModelAndView getTestMessage(HttpServletRequest request, HttpServletResponse response) throws Exception {
		logger.info("Inside Test Controller");
		ModelAndView model = new ModelAndView("Test");
		// model.addObject("msg", "hello world");
		return model;
	}

	@GetMapping("/fetch")
	public ModelAndView getData(HttpServletRequest request, HttpServletResponse response) throws Exception {
		logger.info("Inside Test Controller");
		ModelAndView model = new ModelAndView("Mapping");
		// model.addObject("msg", "hello world");
		return model;
	}
	
	
	/* NOT USED -- CAN BE DELETED*/
	@PostMapping("/fileUpload")
	public String fileUpload(@RequestParam("uploadfile") MultipartFile file) throws Exception {
		try {
			logger.info("File Name:: " + file.getOriginalFilename());
			logger.info("File Name:: " + System.getProperty("user.home"));
			String result = "";
			fileUploadService.store(file);
			result = "File "+ file.getOriginalFilename()+" uploaded successfully!!";
			return result;
		} catch (Exception e) {
			throw new Exception("File Upload Failed");
		}
	}

}
