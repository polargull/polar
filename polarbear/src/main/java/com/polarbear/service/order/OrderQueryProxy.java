package com.polarbear.service.order;

import static com.polarbear.util.Constants.ResultState.ORDER_NOT_EXIST;
import static com.polarbear.util.Constants.ResultState.ORDER_USER_ERR;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.polarbear.dao.BaseDao;
import com.polarbear.dao.DaoException;
import com.polarbear.domain.Order;
import com.polarbear.domain.User;
import com.polarbear.util.factory.CurrentThreadUserFactory;

@Component
public class OrderQueryProxy {
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
}