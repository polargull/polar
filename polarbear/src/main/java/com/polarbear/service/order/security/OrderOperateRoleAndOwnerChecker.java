package com.polarbear.service.order.security;

import static com.polarbear.util.Constants.ResultState.*;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.polarbear.dao.BaseDao;
import com.polarbear.dao.DaoException;
import com.polarbear.domain.Order;
import com.polarbear.domain.User;
import com.polarbear.service.order.OrderStateException;
import com.polarbear.service.order.security.OrderRole.Role;
import com.polarbear.util.factory.CurrentThreadAdminFactory;
import com.polarbear.util.factory.CurrentThreadUserFactory;

@Aspect
@Component
public class OrderOperateRoleAndOwnerChecker {
    @Autowired
    BaseDao<Order> orderDao;

    @Before("execution(* com.polarbear.service.order.OrderStateComponent.*(..))&&@annotation(orderRole)")
    public void before(JoinPoint pjp, OrderRole orderRole) throws OrderStateException, DaoException {
        Order order = orderDao.findById(Order.class, (Long) pjp.getArgs()[0]);
        checkOrder(order);
        checkOrderOwner(order, orderRole);
        checkRoleAuthorization(orderRole);
    }

    private void checkRoleAuthorization(OrderRole orderRole) throws OrderStateException {
        if (orderRole.role() == Role.ADMIN && CurrentThreadAdminFactory.getAdmin() != null) {
            return;
        }
        if (orderRole.role() == Role.BUYER && CurrentThreadUserFactory.getUser() != null) {
            return;
        }
        throw new OrderStateException(ROLE_OPREATE_ERR);
    }

    private void checkOrder(Order order) throws OrderStateException {
        if (order == null)
            throw new OrderStateException(ORDER_NOT_EXIST);
    }

    private void checkOrderOwner(Order order, OrderRole orderRole) throws OrderStateException {
        if (orderRole.role() != Role.BUYER) {
            return;
        }
        User user = CurrentThreadUserFactory.getUser();
        if (!user.getId().equals(order.getBuyer().getId())) {
            throw new OrderStateException(ORDER_USER_ERR);
        }
    }
}