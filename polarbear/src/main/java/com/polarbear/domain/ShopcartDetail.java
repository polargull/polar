package com.polarbear.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Index;
import org.hibernate.annotations.NamedQueries;
import org.hibernate.annotations.NamedQuery;

@NamedQueries( { 
@NamedQuery(name = "queryByShopcartAndProduct", query = "from ShopcartDetail sd where sd.shopCart = ? and sd.product = ?"),
@NamedQuery(name = "queryByShopcart", query = "from ShopcartDetail sd where sd.shopCart = ?")
})
@Entity
@Table(name = "shopcart_detail")
public class ShopcartDetail {
    @Id
    @GeneratedValue
    Long id;
    @ManyToOne
    @Index(name = "productid_shopcartid_index")
    Product product;
    @Column
    Integer num;
    @ManyToOne
    @Index(name = "productid_shopcartid_index")
    Shopcart shopCart;
    @Column
    Integer createTime;

    public ShopcartDetail() {
    }

    public ShopcartDetail(Long id, Product product, Integer num, Shopcart shopCart, Integer createTime) {
        super();
        this.id = id;
        this.product = product;
        this.num = num;
        this.shopCart = shopCart;
        this.createTime = createTime;
    }

    public ShopcartDetail(Product product, Integer num, Shopcart shopCart, Integer createTime) {
        super();
        this.product = product;
        this.num = num;
        this.shopCart = shopCart;
        this.createTime = createTime;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }

    public Shopcart getShopCart() {
        return shopCart;
    }

    public void setShopCart(Shopcart shopCart) {
        this.shopCart = shopCart;
    }

    public Integer getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Integer createTime) {
        this.createTime = createTime;
    }

}