package com.polarbear.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

@Entity
public class OrderList {
    @Id
    @GeneratedValue
    Long id;
    @ManyToOne
    Order order;
    @Column
    Long productId;
    @Column
    String productName;
    @Column
    String productImg;
    @Column
    Integer productNums;
    @Column
    Double productPrice;
    @Column
    Integer createTime;
    @Column
    Integer updateTime;
    @Column
    Integer state;
    @OneToOne
    Logistic logistic;

    public OrderList() {
    }
    public OrderList(Order order, Long productId, String productName, String productImg, Integer productNums, Double productPrice, Integer createTime, Integer updateTime,
            Integer state) {
        this.order = order;
        this.productId = productId;
        this.productName = productName;
        this.productImg = productImg;
        this.productNums = productNums;
        this.productPrice = productPrice;
        this.createTime = createTime;
        this.updateTime = updateTime;
        this.state = state;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductImg() {
        return productImg;
    }

    public void setProductImg(String productImg) {
        this.productImg = productImg;
    }

    public Integer getProductNums() {
        return productNums;
    }

    public void setProductNums(Integer productNums) {
        this.productNums = productNums;
    }

    public Double getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(Double productPrice) {
        this.productPrice = productPrice;
    }

    public Integer getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Integer createTime) {
        this.createTime = createTime;
    }

    public Integer getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Integer updateTime) {
        this.updateTime = updateTime;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public Logistic getLogistic() {
        return logistic;
    }

    public void setLogistic(Logistic logistic) {
        this.logistic = logistic;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

}