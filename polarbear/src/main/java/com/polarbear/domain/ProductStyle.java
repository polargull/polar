package com.polarbear.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "product_style")
public class ProductStyle {
	@Id
	@GeneratedValue
	long id;
	@Column
	String styleProperties;

	public ProductStyle() {
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getStyleProperties() {
		return styleProperties;
	}

	public void setStyleProperties(String styleProperties) {
		this.styleProperties = styleProperties;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("ProductStyle [id=");
		builder.append(id);
		builder.append(", styleProperties=");
		builder.append(styleProperties);
		builder.append("]");
		return builder.toString();
	}

}
