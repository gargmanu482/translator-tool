package com.infy.fd.translator.translatortool.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * @author Amit Srivastava
 *
 */
@Entity
@Table(name = "field")
public class Field {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "field_id", unique = true, nullable = false)
	private String fieldId;

	@ManyToOne(targetEntity = Layout.class, fetch = FetchType.LAZY)
	@JoinColumn(name = "layout_id")
	@JsonIgnore
	private Layout layout;

	
	 @Column(name = "name", nullable = false)
	 private String name;
	  
	
	@Column(name = "tag_name", nullable = false)
	private String tagName;

	
	  @Column(name = "identification")
	  private String identification;
	  
	  @Column(name = "length") 
	  private int length;
	 
	  @Column(name="minOccurs")
	  private String minOccurs;
	  
	  @Transient
	  private boolean isOutputfile;
	  
	  @Transient
	  private String fileExtension;

	public Field() {
	}

	public Field(String name, String tagName, String identification, String length, String minOccurs) {
		super();
		this.name = !name.equals(null) ? name : "";
		this.tagName = !tagName.equals(null) ? tagName : "";
		this.identification = !identification.equals(null) ? identification : "";
		this.length = !length.equals(null) ? Integer.getInteger(length) : 0;
		this.minOccurs = !minOccurs.equals(null)? minOccurs :"0";
	}

	public String getFieldId() {
		return fieldId;
	}

	public void setFieldId(String fieldId) {
		this.fieldId = fieldId;
	}

	public Layout getLayout() {
		return layout;
	}

	public void setLayout(Layout layout) {
		this.layout = layout;
	}

	public String getName(){ 
		return name;
	}
	
	public void setName(String name) {
	   this.name = name; 
	}
	 
	public String getTagName() {
		return tagName;
	}

	public void setTagName(String tagName) {
		this.tagName = tagName;
	}

	public String getIdentification() {
		return identification;
	}

	public void setIdentification(String identification) {
		this.identification = identification;
	}

	public int getLength() {
		return length;
	}

	public void setLength(int length) {
		this.length = length;
	}

	public String getMinOccurs() {
		return minOccurs;
	}

	public void setMinOccurs(String minOccurs) {
		this.minOccurs = minOccurs;
	}

	public String getFileExtension() {
		return fileExtension;
	}

	public void setFileExtension(String fileExtention) {
		this.fileExtension = fileExtention;
	}

	public boolean getIsOutputfile() {
		return isOutputfile;
	}

	public void setIsOutputfile(boolean isOutputfile) {
		this.isOutputfile = isOutputfile;
	}
	
}
