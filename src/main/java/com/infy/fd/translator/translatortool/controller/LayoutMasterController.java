package com.infy.fd.translator.translatortool.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.infy.fd.translator.translatortool.model.LayoutMaster;

@RestController
public class LayoutMasterController {
	
	@PostMapping(value = "/master/")
	public ResponseEntity<String> saveDetails(@RequestParam("client") String client,@RequestParam("inputName") String input,
			@RequestParam("outputName") String output, @RequestParam("mapValues") String mapVal){
		LayoutMaster layout=new LayoutMaster();
		layout.setClientName(client);
		layout.setInputLayoutName(input);
		layout.setOutputLayoutName(output);
		layout.setMappingLsit(mapVal);
		
		System.out.println(layout.getClientName()+" "+layout.getInputLayoutName()+" "+layout.getOutputLayoutName()+" "+layout.getMappingLsit());
	
		return new ResponseEntity<String>("Values inserted",HttpStatus.OK);
	}

}
