package com.polarbear.service.order;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.polarbear.dao.DaoException;
import com.polarbear.domain.Order;
import com.polarbear.service.order.state.UnpayState;

@Component
public class OrderStateComponent {
    @Autowired(required = false)
    UnpayState unpayState;
    @Autowired(required = false)
    OrderQueryProxy orderQueryProxy;

    public void cancle(long orderId, String reason) throws DaoException, OrderStateException {
        Order order = orderQueryProxy.queryOrderById(orderId);
        unpayState.cancle(order);
    }

    public void pay(long orderId, String threePartId) {

    }

    public void sellerSendGoods(long orderId, String companyName, String logisticsOrderIds) {

    }

    public void signin(long orderId) {

    }
}