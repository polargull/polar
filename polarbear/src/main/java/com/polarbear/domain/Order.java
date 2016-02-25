package com.polarbear.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;

@Entity
public class Order {
    @Id
    @GeneratedValue
    Long id;
    @Column
    Integer productTotalNums;
    @Column
    Double productTotalPrice;
    @Column
    String contact;
    @Column
    Double logisticPrice;
    @OneToOne
    Logistic logistic;
    @Column
    Integer state;
    @Column
    Integer createTime;
    @Column
    Integer updateTime;

    public Order() {}

    public Order(Integer productTotalNums, Double productTotalPrice, String contact, Double logisticPrice, Integer state, Integer createTime, Integer updateTime) {
        this.productTotalNums = productTotalNums;
        this.productTotalPrice = productTotalPrice;
        this.contact = contact;
        this.logisticPrice = logisticPrice;
        this.state = state;
        this.createTime = createTime;
        this.updateTime = updateTime;
    }

    public Order(Integer productTotalNums, Double productTotalPrice, String contact, Double logisticPrice, Logistic logistic, Integer state) {
        super();
        this.productTotalNums = productTotalNums;
        this.productTotalPrice = productTotalPrice;
        this.contact = contact;
        this.logisticPrice = logisticPrice;
        this.logistic = logistic;
        this.state = state;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getProductTotalNums() {
        return productTotalNums;
    }

    public void setProductTotalNums(Integer productTotalNums) {
        this.productTotalNums = productTotalNums;
    }

    public Double getProductTotalPrice() {
        return productTotalPrice;
    }

    public void setProductTotalPrice(Double productTotalPrice) {
        this.productTotalPrice = productTotalPrice;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public Double getLogisticPrice() {
        return logisticPrice;
    }

    public void setLogisticPrice(Double logisticPrice) {
        this.logisticPrice = logisticPrice;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
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

    public Logistic getLogistic() {
        return logistic;
    }

    public void setLogistic(Logistic logistic) {
        this.logistic = logistic;
    }

}