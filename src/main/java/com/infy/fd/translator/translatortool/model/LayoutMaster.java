package com.infy.fd.translator.translatortool.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "layoutMaster")
public class LayoutMaster {
	
	@Id
	@Column(name = "clientName")
	private String clientName;
	
	@Column(name = "inputLayoutName")
	private String inputLayoutName;
	
	@Column(name = "outputLayoutName")
	private String outputLayoutName;
	
	@Column(name = "mappingLsit")
	private String mappingLsit;


	public String getMappingLsit() {
		return mappingLsit;
	}

	public void setMappingLsit(String mappingLsit) {
		this.mappingLsit = mappingLsit;
	}

	public String getClientName() {
		return clientName;
	}

	public void setClientName(String clientName) {
		this.clientName = clientName;
	}

	public String getInputLayoutName() {
		return inputLayoutName;
	}

	public void setInputLayoutName(String inputLayoutName) {
		this.inputLayoutName = inputLayoutName;
	}

	public String getOutputLayoutName() {
		return outputLayoutName;
	}

	public void setOutputLayoutName(String outputLayoutName) {
		this.outputLayoutName = outputLayoutName;
	}

	
	
	

}
