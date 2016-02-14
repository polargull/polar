package com.polarbear.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class PayLog {
    @Id
    @GeneratedValue
    Long id;
    @Column
    Integer payPlatform;
    @Column
    String threePartNo;
    @Column
    Integer createTime;

    public PayLog(Integer payPlatform, String threePartNo, Integer createTime) {
        this.payPlatform = payPlatform;
        this.threePartNo = threePartNo;
        this.createTime = createTime;
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

}
