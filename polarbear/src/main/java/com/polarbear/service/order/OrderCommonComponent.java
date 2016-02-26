package com.polarbear.service.order;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import static com.polarbear.service.order.OrderStateException.*;
import com.polarbear.dao.BaseDao;
import com.polarbear.dao.DaoException;
import com.polarbear.domain.Order;
import com.polarbear.domain.OrderList;
import com.polarbear.domain.OrderListLog;
import com.polarbear.domain.OrderLog;
import com.polarbear.util.Constants.ORDER_STATE;

@Component
public class OrderCommonComponent {
    @Autowired(required = false)
    BaseDao<OrderLog> orderLogDao;
    @Autowired(required = false)
    BaseDao<OrderListLog> orderListLogDao;

    public void createOrderLog(Order order) throws DaoException, OrderStateException {
        OrderLog orderLog = new OrderLog(order, stateOp(order.getState()), order.getState(), order.getUpdateTime());
        orderLogDao.store(orderLog);
    }

    public void createOrderListLog(OrderList orderList) throws DaoException, OrderStateException {
        OrderListLog orderListLog = new OrderListLog(orderList, stateOp(orderList.getState()), orderList.getState(), orderList.getUpdateTime());
        orderListLogDao.store(orderListLog);
    }

    private String stateOp(int orderState) throws OrderStateException {
        ORDER_STATE[] orderStates = ORDER_STATE.values();
        for (ORDER_STATE state : orderStates) {
            if (state.value() == orderState)
                return state.op();
        }
        throw new OrderStateException(ORDER_STATE_VAL_ERR);
    }
}