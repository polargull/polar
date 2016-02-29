package com.polarbear.service.order;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.polarbear.ValidateException;
import com.polarbear.dao.DaoException;
import com.polarbear.domain.Order;
import com.polarbear.service.order.bean.OrderParam;

@Service
public class OrderService {
    @Autowired(required = false)
    OrderCreateComponent orderCreateComponent;
    @Autowired(required = false)
    OrderStateComponent orderStateComponent;
    @Autowired(required = false)
    OrderQueryProxy orderQueryProxy;

    @Transactional
    public Order createOrder(OrderParam orderParam) throws DaoException, ValidateException, OrderStateException {
        return orderCreateComponent.createOrder(orderParam);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void cancle(long orderId, String reason) throws DaoException, ValidateException, OrderStateException {
         orderStateComponent.cancle(orderId, reason);
    }

    @Transactional(readOnly = true)
    public Order getMyOrderDetail(long orderId) throws DaoException, OrderStateException {
        return orderQueryProxy.queryOrderById(orderId);
    }
    
    // public Order payOrder(long orderId,String threePartOrderId) {
    //        
    // }

}
