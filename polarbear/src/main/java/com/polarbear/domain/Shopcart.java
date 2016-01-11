package com.polarbear.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.NamedQueries;
import org.hibernate.annotations.NamedQuery;

import com.alibaba.fastjson.annotation.JSONField;


@NamedQueries( {
    @NamedQuery(name = "queryUserId", query = "from Shopcart sc where sc.user = ?")
    })
@Entity
@Table(name = "shopcart")
public class Shopcart {
    @Id
    @GeneratedValue
    long id;
    @OneToOne
    User user;
    @Column
    Integer productNum;
    @Column
    Double price;
    @Column
    Integer createTime;

    public Shopcart(long id, User user, Integer productNum, Double price, Integer createTime) {
        super();
        this.id = id;
        this.user = user;
        this.productNum = productNum;
        this.price = price;
        this.createTime = createTime;
    }

    public Shopcart(User user, Integer count, Double price, Integer createTime) {
        super();
        this.user = user;
        this.productNum = count;
        this.price = price;
        this.createTime = createTime;
    }
    
    public Shopcart(User user, Integer createTime) {
        super();
        this.user = user;
        this.productNum = 0;
        this.price = 0d;
        this.createTime = createTime;
    }
    
    @JSONField(serialize = false)
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @JSONField(serialize = false)
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Integer getProductNum() {
        return productNum;
    }

    public void setProductNum(Integer count) {
        this.productNum = count;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    @JSONField(serialize = false)
    public Integer getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Integer createTime) {
        this.createTime = createTime;
    }

}