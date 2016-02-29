package com.polarbear.service.order.bean;

import com.polarbear.domain.Order;
import com.polarbear.service.order.OrderState;

public class OrderStateBroker {
    Order order;
    OrderState orderState;

    public OrderStateBroker(Order order, OrderState orderState) {
        super();
        this.order = order;
        this.orderState = orderState;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public OrderState getOrderState() {
        return orderState;
    }

    public void setOrderState(OrderState orderState) {
        this.orderState = orderState;
    }

}