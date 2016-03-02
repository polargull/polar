package com.polarbear.service.order;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.polarbear.ValidateException;
import com.polarbear.dao.DaoException;
import com.polarbear.domain.Order;
import com.polarbear.service.order.bean.OrderParam;
import com.polarbear.service.topaylog.ToPayLogComponent;

@Service
public class OrderService {
    @Autowired(required = false)
    OrderCreateComponent orderCreateComponent;
    @Autowired(required = false)
    OrderStateComponent orderStateComponent;
    @Autowired(required = false)
    OrderQueryProxy orderQueryProxy;
    @Autowired(required = false)
    ToPayLogComponent toPayLogComponent;

    @Transactional
    public Order createOrder(OrderParam orderParam) throws DaoException, ValidateException, OrderStateException {
        return orderCreateComponent.createOrder(orderParam);
    }

    @Transactional
    public void cancle(long orderId, String reason) throws DaoException, ValidateException, OrderStateException {
        orderStateComponent.cancle(orderId, reason);
    }

    @Transactional
    public void toPay(long orderId, int payPlatform) throws DaoException, ValidateException, OrderStateException {
        toPayLogComponent.createToPayLog(orderId, payPlatform);
    }

    @Transactional
    public void pay(long orderId, String threePartId) throws DaoException, ValidateException, OrderStateException {
        orderStateComponent.pay(orderId, threePartId);
    }

    @Transactional
    public void sign(long orderId) throws DaoException, ValidateException, OrderStateException {
        orderStateComponent.sign(orderId);
    }

    @Transactional(readOnly = true)
    public Order getMyOrderDetail(long orderId) throws DaoException, OrderStateException {
        return orderQueryProxy.queryOrderById(orderId);
    }

    @Transactional
    public void delivery(long orderId, String companyName, String logisticsOrderIds) throws DaoException, OrderStateException {
        orderStateComponent.delivery(orderId, companyName, logisticsOrderIds);
    }
}