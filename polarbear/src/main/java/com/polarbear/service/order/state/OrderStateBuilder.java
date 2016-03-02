package com.polarbear.service.order.state;

import static com.polarbear.util.Constants.ResultState.ORDER_NOT_EXIST;
import static com.polarbear.util.Constants.ResultState.ORDER_STATE_VAL_ERR;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.polarbear.dao.DaoException;
import com.polarbear.domain.Order;
import com.polarbear.service.order.OrderQueryProxy;
import com.polarbear.service.order.OrderStateException;
import com.polarbear.service.order.bean.OrderStateBroker;

@Component
public class OrderStateBuilder {
    @Autowired
    OrderQueryProxy orderQueryProxy;
    @Autowired
    UnpayState unpayState;
    @Autowired
    DeliveryState deliveryState;
    
    public OrderStateBroker buildOrderState(long orderId) throws DaoException, OrderStateException {
        Order order = orderQueryProxy.queryOrderById(orderId);
        if (order == null)
            throw new OrderStateException(ORDER_NOT_EXIST);
        
        switch (order.getState()) {
        case 1:
            return new OrderStateBroker(order, unpayState);
            // case 2: return new PayState();
        case 3:
            return new OrderStateBroker(order, deliveryState);
            // case 4:;
            // case 5:;
        }
        
        throw new OrderStateException(ORDER_STATE_VAL_ERR);
    }
}
