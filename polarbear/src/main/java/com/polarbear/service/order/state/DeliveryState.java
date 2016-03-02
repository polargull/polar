package com.polarbear.service.order.state;

import static com.polarbear.util.Constants.ORDER_STATE.SUCCESS;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.polarbear.dao.DaoException;
import com.polarbear.domain.Order;
import com.polarbear.service.order.OrderCommonComponent;
import com.polarbear.service.order.OrderState;
import com.polarbear.service.order.OrderStateException;

@Component
public class DeliveryState extends OrderState {
    @Autowired(required = false)
    OrderCommonComponent orderCommonComponent;

    @Override
    public void sign(Order order) throws DaoException, OrderStateException {
        orderCommonComponent.updateState(order, SUCCESS);
    }
}