package com.polarbear.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.alibaba.fastjson.annotation.JSONField;
@Entity
@Table(name = "orders")
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
    @Column
    Integer state;
    @Column
    Integer createTime;
    @Column
    Integer updateTime;
    @ManyToOne
    User buyer;
    
    public Order() {}

    public Order(User buyer, Integer productTotalNums, Double productTotalPrice, String contact, Double logisticPrice, Integer state, Integer createTime,
            Integer updateTime) {
        this.buyer = buyer;
        this.productTotalNums = productTotalNums;
        this.productTotalPrice = productTotalPrice;
        this.contact = contact;
        this.logisticPrice = logisticPrice;
        this.state = state;
        this.createTime = createTime;
        this.updateTime = updateTime;
    }

    /**
     * @param id
     * @param buyer
     * @param productTotalNums
     * @param productTotalPrice
     * @param contact
     * @param logisticPrice
     * @param logistic
     * @param state
     */
    public Order(long id, User buyer, Integer productTotalNums, Double productTotalPrice, String contact, Double logisticPrice, Integer state, Integer createTime, Integer updateTime) {
        super();
        this.id = id;
        this.buyer = buyer;
        this.productTotalNums = productTotalNums;
        this.productTotalPrice = productTotalPrice;
        this.contact = contact;
        this.logisticPrice = logisticPrice;
        this.state = state;
        this.createTime = createTime;
        this.updateTime = updateTime;
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

    @JSONField(serialize = false)
    public User getBuyer() {
        return buyer;
    }

    public void setBuyer(User buyer) {
        this.buyer = buyer;
    }

}
