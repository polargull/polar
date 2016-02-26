package com.polarbear.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class OrderListLog {
    @Id
    @GeneratedValue
    Long id;
    @ManyToOne
    OrderList orderList;
    @Column
    String op;
    @Column
    Integer state;
    @Column
    Integer createTime;

    public OrderListLog() {
    }

    public OrderListLog(OrderList orderList, String op, Integer state, Integer createTime) {
        this.orderList = orderList;
        this.op = op;
        this.state = state;
        this.createTime = createTime;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public OrderList getOrderList() {
        return orderList;
    }

    public void setOrderList(OrderList orderList) {
        this.orderList = orderList;
    }

    public String getOp() {
        return op;
    }

    public void setOp(String op) {
        this.op = op;
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

}