package com.polarbear.service.order;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.polarbear.dao.DaoException;
import com.polarbear.service.order.bean.OrderStateBroker;
import com.polarbear.service.order.state.OrderStateBuilder;

@Component
public class OrderStateComponent {
    @Autowired(required = false)
    OrderStateBuilder orderStateBuilder;

    public void cancle(long orderId, String reason) throws DaoException, OrderStateException {
        OrderStateBroker stateBroker = orderStateBuilder.buildOrderState(orderId);
        stateBroker.getOrderState().cancle(stateBroker.getOrder());
    }

    public void pay(long orderId, String threePartId) throws DaoException, OrderStateException {
        OrderStateBroker stateBroker = orderStateBuilder.buildOrderState(orderId);
        stateBroker.getOrderState().pay(stateBroker.getOrder(), threePartId);
    }

    public void sellerSendGoods(long orderId, String companyName, String logisticsOrderIds) {

    }

    public void sign(long orderId) throws OrderStateException, DaoException {
        OrderStateBroker stateBroker = orderStateBuilder.buildOrderState(orderId);
        stateBroker.getOrderState().sign(stateBroker.getOrder());
    }
}