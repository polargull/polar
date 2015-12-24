package com.polarbear.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.NamedQueries;
import org.hibernate.annotations.NamedQuery;
@NamedQueries( {
	@NamedQuery(name = "querySameStyleProductByStyleId", query = "from Product p where p.productStyle.id = ?")
	})
@Entity
public class Product {
	@Id
	@GeneratedValue
	long id;
	@ManyToOne
	ProductStyle productStyle;
	@Column
	String name;
	@Column
	int count;
	@Column(name = "p_desc")
	String desc;
	@Column
	String tag;
	@Column(length = 1000)
	String image;
	@Column
	double price;
	@Column
	int state;
	@Column
	String extProperty;
	@Column
	double salePrice;
	@Column
	int saleBeginTime;
	@Column
	int saleEndTime;
	@Column
	int createTime;

	public Product() {
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public String getTag() {
		return tag;
	}

	public void setTag(String tag) {
		this.tag = tag;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public ProductStyle getProductStyle() {
		return productStyle;
	}

	public void setProductStyle(ProductStyle productStyle) {
		this.productStyle = productStyle;
	}

	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}

	public String getExtProperty() {
		return extProperty;
	}

	public void setExtProperty(String extProperty) {
		this.extProperty = extProperty;
	}

	public double getSalePrice() {
		return salePrice;
	}

	public void setSalePrice(double salePrice) {
		this.salePrice = salePrice;
	}

	public int getSaleBeginTime() {
		return saleBeginTime;
	}

	public void setSaleBeginTime(int saleBeginTime) {
		this.saleBeginTime = saleBeginTime;
	}

	public int getSaleEndTime() {
		return saleEndTime;
	}

	public void setSaleEndTime(int saleEndTime) {
		this.saleEndTime = saleEndTime;
	}

	public int getCreateTime() {
		return createTime;
	}

	public void setCreateTime(int createTime) {
		this.createTime = createTime;
	}

	@Override
	public String toString() {
		return "Product [count=" + count + ", createTime=" + createTime
				+ ", desc=" + desc + ", extProperty=" + extProperty + ", id="
				+ id + ", image=" + image + ", name=" + name + ", price="
				+ price + ", saleBeginTime=" + saleBeginTime + ", saleEndTime="
				+ saleEndTime + ", salePrice=" + salePrice + ", state=" + state
				+ ", tag=" + tag + "]";
	}

	
}
