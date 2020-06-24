package com.infy.fd.translator.translatortool.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name = "mappingRules")
public class MappingRules {
	
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="id")
	private Integer id;
	
	@Column(name = "fieldName")
	private String fieldName;
	
	@Column(name = "fieldTag")
	private String fieldTag;
	
	@Column(name = "identificationID")
	private String identificationId;
	
	@Column(name = "description")
	private String description;

	public String getFieldName() {
		return fieldName;
	}

	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}

	public String getFieldTag() {
		return fieldTag;
	}

	public void setFieldTag(String fieldTag) {
		this.fieldTag = fieldTag;
	}

	public String getIdentification() {
		return identificationId;
	}

	public void setIdentification(String identification) {
		this.identificationId = String.valueOf(identification);
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	
	
}
