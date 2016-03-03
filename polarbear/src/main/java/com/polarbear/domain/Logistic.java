package com.polarbear.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;

import org.hibernate.annotations.NamedQueries;
import org.hibernate.annotations.NamedQuery;

@NamedQueries( {
        @NamedQuery(name = "queryLogisticByOrder", query = "from Logistic l where l.order = ?"),        
        @NamedQuery(name = "queryLogisticByOrderList", query = "from Logistic l where l.orderList = ?")
} )
@Entity
public class Logistic {
    @Id
    @GeneratedValue
    Long id;
    @Column
    String company;
    @Column
    String logisticOrderId;
    @OneToOne
    Order order;
    @OneToOne
    OrderList orderList;
    @Column
    Integer createTime;

    public Logistic() {
    }

    public Logistic(String company, String logisticOrderId, Order order, Integer createTime) {
        this.company = company;
        this.logisticOrderId = logisticOrderId;
        this.order = order;
        this.createTime = createTime;
    }

    public Logistic(String company, String logisticOrderId, OrderList orderList, Integer createTime) {
        this.company = company;
        this.logisticOrderId = logisticOrderId;
        this.orderList = orderList;
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

    public String getLogisticOrderId() {
        return logisticOrderId;
    }

    public void setLogisticOrderId(String logisticOrderId) {
        this.logisticOrderId = logisticOrderId;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public OrderList getOrderList() {
        return orderList;
    }

    public void setOrderList(OrderList orderList) {
        this.orderList = orderList;
    }

    public Integer getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Integer createTime) {
        this.createTime = createTime;
    }
}
