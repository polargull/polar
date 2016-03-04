package com.polarbear.service.order;

import static com.polarbear.util.Constants.ORDER_LIST_STATE.NEED_COMMENT;
import static com.polarbear.util.Constants.ORDER_LIST_STATE.NEED_PAY;
import static com.polarbear.util.Constants.ORDER_LIST_STATE.NEED_RECEIVE;
import static com.polarbear.util.Constants.ORDER_STATE.DELIVERY;
import static com.polarbear.util.Constants.ORDER_STATE.PAYED;
import static com.polarbear.util.Constants.ORDER_STATE.SUCCESS;
import static com.polarbear.util.Constants.ORDER_STATE.UNPAY;
import static com.polarbear.util.Constants.ResultState.ORDER_NOT_EXIST;
import static com.polarbear.util.Constants.ResultState.ORDER_USER_ERR;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.polarbear.dao.BaseDao;
import com.polarbear.dao.DaoException;
import com.polarbear.domain.Order;
import com.polarbear.domain.User;
import com.polarbear.service.PageList;
import com.polarbear.service.order.bean.OrderListParam;
import com.polarbear.util.Constants.ORDER_LIST_STATE;
import com.polarbear.util.factory.CurrentThreadUserFactory;

@Component
public class OrderQueryComponent {
    @Autowired
    BaseDao<Order> orderDao;

    public Order queryOrderById(long orderId) throws DaoException, OrderStateException {
        Order order = orderDao.findByIdLock(Order.class, orderId);
        if (order == null)
            throw new OrderStateException(ORDER_NOT_EXIST);
        checkOrderUser(order);
        return order;
    }

    private void checkOrderUser(Order order) throws OrderStateException {
        User user = CurrentThreadUserFactory.getUser();
        if (!user.getId().equals(order.getBuyer().getId())) {
            throw new OrderStateException(ORDER_USER_ERR);
        }
    }
    
    public PageList<Order> queryList(OrderListParam param) throws DaoException {
        User user = CurrentThreadUserFactory.getUser();
        StringBuilder hql = new StringBuilder();
        hql = convertBuyerHql(user, hql);
        hql = convertStateHql(param.getOrderListState(), hql);
        return orderDao.findPageListByDynamicCondition(Order.class, param.getPageNo(), param.getPageSize(), hql.toString());
    }

    private StringBuilder convertBuyerHql(User user, StringBuilder hql) {
        return hql.append("buyer.id = ").append(user.getId());
    }

    private StringBuilder convertStateHql(int listState, StringBuilder hql) {
        if (NEED_PAY == getOrderListState(listState)) {
            hql.append(" and ").append("state = ").append(UNPAY.value());
        } else if (NEED_RECEIVE == getOrderListState(listState)) {
            hql.append(" and ").append("state in (").append(PAYED.value()).append(",").append(DELIVERY.value()).append(")");
        } else if (NEED_COMMENT == getOrderListState(listState)) {
            hql.append(" and ").append("state = ").append(SUCCESS.value());
        }
        return hql;
    }
    
    private ORDER_LIST_STATE getOrderListState(int listState) {
        ORDER_LIST_STATE[] listStates = ORDER_LIST_STATE.values();
        for (ORDER_LIST_STATE state:listStates) {
            if (listState == state.value())
                return state;
        }
        return ORDER_LIST_STATE.ALL;
    }
}