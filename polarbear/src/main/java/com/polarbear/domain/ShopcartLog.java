package com.polarbear.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "shopcart_log")
public class ShopcartLog {
    @Id
    @GeneratedValue
    Long id;
    @ManyToOne
    Product product;
    @Column
    Integer count;
    @ManyToOne
    Shopcart shopCart;
    @Column
    Integer createTime;

    public ShopcartLog(Long id, Product product, Integer count, Shopcart shopCart, Integer createTime) {
        super();
        this.id = id;
        this.product = product;
        this.count = count;
        this.shopCart = shopCart;
        this.createTime = createTime;
    }

    public ShopcartLog(Product product, Integer count, Shopcart shopCart, Integer createTime) {
        super();
        this.product = product;
        this.count = count;
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

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
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