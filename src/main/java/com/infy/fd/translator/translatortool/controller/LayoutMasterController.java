package com.infy.fd.translator.translatortool.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.infy.fd.translator.translatortool.model.LayoutMaster;
import com.infy.fd.translator.translatortool.repositoy.LayoutMasterRepository;
import com.infy.fd.translator.translatortool.service.DataInsertionService;

@RestController
public class LayoutMasterController {
	
	@Autowired
	private LayoutMasterRepository layoutRepo;
	
	
	
	@PostMapping(value = "/master/")
	public ResponseEntity<String> saveDetails(@RequestParam("client") String client,@RequestParam("inputName") String input,
			@RequestParam("outputName") String output, @RequestParam("mapValues") String mapVal){
		LayoutMaster layout=new LayoutMaster();
		layout.setClientName(client);
		layout.setInputLayoutName(input);
		layout.setOutputLayoutName(output);
		layout.setMappingLsit(mapVal);
		layoutRepo.save(layout);
		
		LayoutMaster layoutData = layoutRepo.getOne(client);
		System.out.println(layoutData.getClientName()+" "+layoutData.getInputLayoutName()+" "+layoutData.getOutputLayoutName());
	
		return new ResponseEntity<String>("Values inserted",HttpStatus.OK);
	}

}
