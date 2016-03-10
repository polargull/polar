package com.polarbear.service.order.security;

import static com.polarbear.util.Constants.ResultState.*;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
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
        if (CurrentThreadUserFactory.getUser() != null) {
            checkOrderAndOwner(order);
        }
        checkRoleAuthorization(order, orderRole);
    }

    @AfterReturning(value="execution(* com.polarbear.service.order.OrderQueryComponent.queryOrderById(..))", argNames="order", returning="order")
    public void checkOrderAndOwner(JoinPoint jp, Object order) throws OrderStateException {
        checkOrderAndOwner((Order)order);
    }

    private void checkOrderAndOwner(Order order) throws OrderStateException {
        checkOrder(order);
        checkOrderOwner(order);
    }

    private void checkRoleAuthorization(Order order, OrderRole orderRole) throws OrderStateException {
        if((orderRole.role().val & getCurRole(order).val) == getCurRole(order).val) {
            return;
        }
        throw new OrderStateException(ROLE_OPREATE_ERR);
    }

    private void checkOrder(Order order) throws OrderStateException {
        if (order == null)
            throw new OrderStateException(ORDER_NOT_EXIST);
    }

    private void checkOrderOwner(Order order) throws OrderStateException {
        if (isBuyer(order)) {
            return;
        }
        throw new OrderStateException(ORDER_USER_ERR);        
    }

    private Role getCurRole(Order order) {
        if (isBuyer(order)) {
            return Role.BUYER;
        }
        if (isAdmin()) {
            return Role.ADMIN;
        }
        return null;
    }

    private boolean isBuyer(Order order) {
        User user = CurrentThreadUserFactory.getUser();
        return user != null && order.getBuyer().getId().equals(user.getId());
    }

    private boolean isAdmin() {
        return CurrentThreadAdminFactory.getAdmin() != null;
    }

    private boolean isSys() {
        return false;
    }
}