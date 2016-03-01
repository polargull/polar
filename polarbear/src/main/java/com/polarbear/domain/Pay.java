package com.polarbear.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;

@Entity
public class Pay {
    @Id
    @GeneratedValue
    Long id;
    @Column
    Integer payPlatform;
    @Column
    String threePartNo;
    @Column
    Integer createTime;
    @OneToOne
    Order order;

    public Pay() {
    }

    public Pay(Integer payPlatform, String threePartNo, Integer createTime, Order order) {
        super();
        this.payPlatform = payPlatform;
        this.threePartNo = threePartNo;
        this.createTime = createTime;
        this.order = order;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getPayPlatform() {
        return payPlatform;
    }

    public void setPayPlatform(Integer payPlatform) {
        this.payPlatform = payPlatform;
    }

    public String getThreePartNo() {
        return threePartNo;
    }

    public void setThreePartNo(String threePartNo) {
        this.threePartNo = threePartNo;
    }

    public Integer getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Integer createTime) {
        this.createTime = createTime;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

}