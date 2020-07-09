package com.infy.fd.translator.translatortool.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "layoutMaster")
public class LayoutMaster {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "client_Id", unique = true, nullable = false)
	private Integer clientId;
	
	@Column(name = "clientName")
	private String clientName;
	
	@Column(name = "inputLayoutName")
	private String inputLayoutName;
	
	@Column(name = "outputLayoutName")
	private String outputLayoutName;
	
	@OneToMany(cascade = CascadeType.ALL)
	@JoinTable(name = "Layout_Master_Mapping", 
	joinColumns = @JoinColumn(name = "client_Id"), 
	inverseJoinColumns = @JoinColumn(name = "mappingId", unique = true))
	private List<Mapping> mappingList;


	public List<Mapping> getMappingList() {
		return mappingList;
	}

	public void setMappingList(List<Mapping> mappingList) {
		this.mappingList = mappingList;
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
