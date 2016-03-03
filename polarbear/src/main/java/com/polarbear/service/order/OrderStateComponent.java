package com.polarbear.service.order;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.polarbear.dao.DaoException;
import com.polarbear.service.order.bean.OrderStateBroker;
import com.polarbear.service.order.security.OrderRole;
import com.polarbear.service.order.security.OrderRole.Role;
import com.polarbear.service.order.state.OrderStateBuilder;

@Component
public class OrderStateComponent {
    @Autowired(required = false)
    OrderStateBuilder orderStateBuilder;

    @OrderRole
    public void cancle(long orderId, String reason) throws DaoException, OrderStateException {
        OrderStateBroker stateBroker = orderStateBuilder.buildOrderState(orderId);
        stateBroker.getOrderState().cancle(stateBroker.getOrder());
    }

    @OrderRole
    public void pay(long orderId, String threePartId) throws DaoException, OrderStateException {
        OrderStateBroker stateBroker = orderStateBuilder.buildOrderState(orderId);
        stateBroker.getOrderState().pay(stateBroker.getOrder(), threePartId);
    }

    @OrderRole(role = Role.ADMIN)
    public void delivery(long orderId, String companyName, String logisticsOrderIds) throws DaoException, OrderStateException {
        OrderStateBroker stateBroker = orderStateBuilder.buildOrderState(orderId);
        stateBroker.getOrderState().delivery(stateBroker.getOrder(), companyName, logisticsOrderIds);
    }

    @OrderRole
    public void sign(long orderId) throws OrderStateException, DaoException {
        OrderStateBroker stateBroker = orderStateBuilder.buildOrderState(orderId);
        stateBroker.getOrderState().sign(stateBroker.getOrder());
    }
}