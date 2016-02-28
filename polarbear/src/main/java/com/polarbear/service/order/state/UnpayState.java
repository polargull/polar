package com.polarbear.service.order.state;

import static com.polarbear.util.Constants.ORDER_STATE.CANCLE;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.polarbear.dao.DaoException;
import com.polarbear.domain.Order;
import com.polarbear.service.order.OrderCommonComponent;
import com.polarbear.service.order.OrderState;
import com.polarbear.service.order.OrderStateException;

@Component
public class UnpayState extends OrderState {
    @Autowired
    OrderCommonComponent orderCommonComponent;

    @Override
    public void cancle(Order order) throws OrderStateException, DaoException {
        orderCommonComponent.updateState(order, CANCLE);
    }

    @Override
    public void pay(Order order, String thirdOrderId) throws OrderStateException {
        throw new OrderStateException(1);
    }
}