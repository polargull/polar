package com.polarbear.service.order;

import static com.polarbear.util.Constants.ORDER_LIST_STATE.NEED_COMMENT;
import static com.polarbear.util.Constants.ORDER_LIST_STATE.NEED_PAY;
import static com.polarbear.util.Constants.ORDER_LIST_STATE.NEED_RECEIVE;
import static com.polarbear.util.Constants.ORDER_STATE.DELIVERY;
import static com.polarbear.util.Constants.ORDER_STATE.PAYED;
import static com.polarbear.util.Constants.ORDER_STATE.SUCCESS;
import static com.polarbear.util.Constants.ORDER_STATE.UNPAY;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.polarbear.dao.BaseDao;
import com.polarbear.dao.DaoException;
import com.polarbear.domain.Order;
import com.polarbear.domain.OrderList;
import com.polarbear.domain.User;
import com.polarbear.service.PageList;
import com.polarbear.service.order.bean.OrderListParam;
import com.polarbear.service.order.bean.OrderTO;
import com.polarbear.service.order.security.OrderRole;
import com.polarbear.service.order.security.OrderRole.Role;
import com.polarbear.util.Constants.ORDER_LIST_STATE;
import com.polarbear.util.factory.CurrentThreadUserFactory;

@Component
public class OrderQueryComponent {
    @Autowired
    BaseDao<Order> orderDao;
    @Autowired
    BaseDao<OrderList> orderListDao;
    
    @OrderRole(role = Role.BUYER_OR_SELLER)
    public Order queryOrderById(long orderId) throws DaoException, OrderStateException {
        return orderDao.findByIdLock(Order.class, orderId);
    }

    public PageList<OrderTO> queryList(OrderListParam param) throws DaoException {
        List<OrderTO> orderTOList = new ArrayList<OrderTO>();
        User user = CurrentThreadUserFactory.getUser();
        StringBuilder hql = new StringBuilder();
        hql = convertBuyerHql(user, hql);
        hql = convertStateHql(param.getOrderListState(), hql);
        PageList<Order> orderList = orderDao.findPageListByDynamicCondition(Order.class, param.getPageNo(), param.getPageSize(), hql.toString());
        for (Order order : orderList.getList()) {
            List<OrderList> subOrderList = orderListDao.findByNamedQuery("queryListByOrderId", order);
            orderTOList.add(new OrderTO(order, subOrderList));
        }
        return new PageList<OrderTO>(orderList.getTotal(), param.getPageNo(), param.getPageSize(), orderTOList);
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