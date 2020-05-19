/**
 * 
 */
package com.infy.fd.translator.translatortool.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * @author Amit Srivastava
 *
 */
@Entity
@Table(name = "layout")
public class Layout {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "layout_id", unique = true, nullable = false)
	private String layoutId ="";

	@Column(name = "name", nullable = true)
	private String name;

	@Column(name = "client", nullable = false)
	private String client;

	@OneToMany(mappedBy = "layout", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private List<Field> fields;

	public String getLayoutId() {
		return layoutId;
	}

	public void setLayoutId(String layoutId) {
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
		this.client = client.toUpperCase() ;
	}

	public List<Field> getFields() {
		return fields;
	}

	public void setFields(List<Field> fields) {
		this.fields = fields;
	}

}
