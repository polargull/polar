package com.polarbear.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Logistic {
    @Id
    @GeneratedValue
    Long id;
    @Column
    String company;
    @Column
    Long logisticOrderId;
    @Column
    Integer createTime;

    public Logistic(String company, Long logisticOrderId, Integer createTime) {
        this.company = company;
        this.logisticOrderId = logisticOrderId;
        this.createTime = createTime;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public Long getLogisticOrderId() {
        return logisticOrderId;
    }

    public void setLogisticOrderId(Long logisticOrderId) {
        this.logisticOrderId = logisticOrderId;
    }

    public Integer getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Integer createTime) {
        this.createTime = createTime;
    }
}
