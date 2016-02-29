package com.polarbear.service.order.state;

import static com.polarbear.service.product.ModifyProductComponent.DECREASE;
import static com.polarbear.util.Constants.ORDER_STATE.CANCLE;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.polarbear.dao.DaoException;
import com.polarbear.domain.Order;
import com.polarbear.service.order.OrderCommonComponent;
import com.polarbear.service.order.OrderState;
import com.polarbear.service.order.OrderStateException;
import com.polarbear.service.product.ModifyProductComponent;

@Component
public class UnpayState extends OrderState {
    @Autowired(required = false)
    OrderCommonComponent orderCommonComponent;
    @Autowired(required = false)
    ModifyProductComponent modifyProductComponent;

    @Override
    public void cancle(Order order) throws OrderStateException, DaoException {
        orderCommonComponent.updateState(order, CANCLE);
        modifyProductComponent.modifyProductNum(order, DECREASE);
    }

    @Override
    public void pay(Order order, String thirdOrderId) throws OrderStateException {
        throw new OrderStateException(1);
    }
}