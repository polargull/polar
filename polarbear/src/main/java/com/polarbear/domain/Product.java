package com.polarbear.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.NamedQueries;
import org.hibernate.annotations.NamedQuery;

import com.polarbear.util.DateUtil;

@NamedQueries( { @NamedQuery(name = "querySameStyleProductByStyleId", query = "from Product p where p.productStyle.id = ?"),
        @NamedQuery(name = "queryProductByIdAndState", query = "from Product p where p.id = ? and p.state = ?") })
@Entity
public class Product {
    @Id
    @GeneratedValue
    Long id;
    @ManyToOne
    ProductStyle productStyle;
    @Column
    String name;
    @Column
    int num;
    @Column(name = "p_desc")
    String desc;
    @Column
    String tag;
    @Column(length = 1000)
    String image;
    @Column
    Double price;
    @Column
    Integer state;
    @Column
    String extProperty;
    @Column
    Double salePrice;
    @Column
    Integer saleBeginTime;
    @Column
    Integer saleEndTime;
    @Column
    Integer createTime;

    public Product() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ProductStyle getProductStyle() {
        return productStyle;
    }

    public void setProductStyle(ProductStyle productStyle) {
        this.productStyle = productStyle;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
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

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public String getExtProperty() {
        return extProperty;
    }

    public void setExtProperty(String extProperty) {
        this.extProperty = extProperty;
    }

    public Double getSalePrice() {
        return salePrice;
    }

    public void setSalePrice(Double salePrice) {
        this.salePrice = salePrice;
    }

    public Integer getSaleBeginTime() {
        return saleBeginTime;
    }

    public void setSaleBeginTime(Integer saleBeginTime) {
        this.saleBeginTime = saleBeginTime;
    }

    public Integer getSaleEndTime() {
        return saleEndTime;
    }

    public void setSaleEndTime(Integer saleEndTime) {
        this.saleEndTime = saleEndTime;
    }

    public Integer getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Integer createTime) {
        this.createTime = createTime;
    }

    public double getRealPrice() {
        if (salePrice >= 0 && saleBeginTime >= DateUtil.getCurrentSeconds() && saleEndTime <= DateUtil.getCurrentSeconds()) {
            return salePrice;
        }
        return price;
    }

    @Override
    public String toString() {
        return "Product [createTime=" + createTime + ", desc=" + desc + ", extProperty=" + extProperty + ", id=" + id + ", image=" + image + ", name=" + name + ", num=" + num
                + ", price=" + price + ", productStyle=" + productStyle + ", saleBeginTime=" + saleBeginTime + ", saleEndTime=" + saleEndTime + ", salePrice=" + salePrice
                + ", state=" + state + ", tag=" + tag + "]";
    }

}