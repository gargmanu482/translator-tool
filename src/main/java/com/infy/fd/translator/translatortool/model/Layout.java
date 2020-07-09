package com.infy.fd.translator.translatortool.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;
import javax.persistence.Table;


@Entity
@Table(name = "layout")
public class Layout {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "layout_id", unique = true, nullable = false)
	private Integer layoutId;

	@Column(name = "name", nullable = true)
	private String name;

	@Column(name = "client", nullable = false)
	private String client;

	@OneToMany(cascade = CascadeType.ALL)
	@JoinTable(name = "LAYOUT_FIELD_MAPPING", 
	joinColumns = @JoinColumn(name = "layout_id"), 
	inverseJoinColumns = @JoinColumn(name = "field_id", unique = true))
	private List<Field> fields;

	public Integer getLayoutId() {
		return layoutId;
	}

	public void setLayoutId(Integer layoutId) {
		this.layoutId = layoutId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name.toUpperCase();
	}

	public String getClient() {
		return client;
	}

	public void setClient(String client) {
		this.client = client.toUpperCase();
	}

	public List<Field> getFields() {
		return fields;
	}

	public void setFields(List<Field> fields) {
		this.fields = fields;
	}

}
