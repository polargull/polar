package com.polarbear.domain;

import javax.persistence.Column;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.NamedQuery;

@NamedQuery(name = "queryLastOneLogByOrder", query = "from ToPayLog log where log.order = ? order by log.createTime desc")
@Entity
@Table(name = "to_pay_log")
public class ToPayLog {
    @Id
    @GeneratedValue
    Long id;
    @Column
    Integer payPlatform;
    @Column
    Integer createTime;
    @OneToOne
    Order order;

    public ToPayLog() {
    }
    
    public ToPayLog(Integer payPlatform, Integer createTime, Order order) {
        this.payPlatform = payPlatform;
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

    public Integer getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Integer createTime) {
        this.createTime = createTime;
    }

}