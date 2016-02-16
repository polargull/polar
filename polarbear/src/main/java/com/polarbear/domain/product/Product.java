package com.polarbear.domain.product;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.NamedQueries;
import org.hibernate.annotations.NamedQuery;

import com.polarbear.domain.Category;
import com.polarbear.domain.ProductStyle;
import com.polarbear.util.DateUtil;

@NamedQueries( { @NamedQuery(name = "querySameStyleProductByStyleId", query = "from Product p where p.productStyle.id = ?"),
        @NamedQuery(name = "queryProductByIdAndState", query = "from Product p where p.id = ? and p.state = ?"),
        @NamedQuery(name = "queryPutOnProductByIds", query = "from Product p where p.id in (:ids) and p.state = 1"),
        @NamedQuery(name = "queryPutOnProductByCategoryId", query = "from Product p where p.category = ? and p.state = 1 order by p.createTime desc") })
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
    Integer num;
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
    @Embedded
    Sale sale;
    @Column
    Integer createTime;
    @ManyToOne
    Category category;

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

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
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

    public Sale getSale() {
        return sale;
    }

    public void setSale(Sale sale) {
        this.sale = sale;
    }

    public Integer getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Integer createTime) {
        this.createTime = createTime;
    }

    public double getRealPrice() {
        if (sale == null)
            return price;
        if ((sale.salePrice != null && sale.salePrice >= 0) && (sale.saleBeginTime != null && sale.saleBeginTime <= DateUtil.getCurrentSeconds())
                && (sale.saleEndTime != null && sale.saleEndTime >= DateUtil.getCurrentSeconds())) {
            return sale.salePrice;
        }
        return price;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    @Override
    public String toString() {
        return "Product [category=" + category + ", createTime=" + createTime + ", desc=" + desc + ", extProperty=" + extProperty + ", id=" + id + ", image=" + image + ", name="
                + name + ", num=" + num + ", price=" + price + ", productStyle=" + productStyle + ", sale=" + sale + ", state=" + state + ", tag=" + tag + "]";
    }

}