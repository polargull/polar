package com.polarbear.service.order;

import static com.polarbear.util.Constants.ResultState.ORDER_STATE_VAL_ERR;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.polarbear.dao.BaseDao;
import com.polarbear.dao.DaoException;
import com.polarbear.domain.Order;
import com.polarbear.domain.OrderList;
import com.polarbear.domain.OrderListLog;
import com.polarbear.domain.OrderLog;
import com.polarbear.util.Constants.ORDER_STATE;
import com.polarbear.util.date.DateUtil;

@Component
public class OrderCommonComponent {
    @Autowired(required = false)
    BaseDao<OrderLog> orderLogDao;
    @Autowired(required = false)
    BaseDao<OrderList> orderListDao;
    @Autowired(required = false)
    BaseDao<OrderListLog> orderListLogDao;
    @Autowired
    BaseDao<Order> orderDao;

    public Order updateState(Order order, ORDER_STATE state) throws DaoException, OrderStateException {
        updateOrderState(order, state);
        createOrderLog(order);
        orderListDao.executeUpdate("batchUpdateStateByOrderId", state.value(), order);
        createOrderListLog(order);
        return order;
    }

    private void updateOrderState(Order order, ORDER_STATE state) throws DaoException {
        order.setState(state.value());
        order.setUpdateTime(DateUtil.getCurrentSeconds());
        orderDao.store(order);
    }
    
    public void createOrderLog(Order order) throws DaoException, OrderStateException {
        OrderLog orderLog = new OrderLog(order, stateOp(order.getState()), order.getState(), order.getUpdateTime());
        orderLogDao.store(orderLog);
    }

    private void createOrderListLog(Order order) throws DaoException, OrderStateException {
        List<OrderList> orderLists = orderListDao.findByNamedQuery("queryListByOrderId", order);
        for (OrderList orderList:orderLists) {
            createOrderListLog(orderList);
        }
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