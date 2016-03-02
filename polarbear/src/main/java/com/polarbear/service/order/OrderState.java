package com.polarbear.service.order;

import static com.polarbear.util.Constants.ResultState.ORDER_OPREATE_ERR;

import com.polarbear.dao.DaoException;
import com.polarbear.domain.Order;
public abstract class OrderState {
    
    public void agreeRefundGoods(Long orderId) throws OrderStateException {
        throw new OrderStateException(ORDER_OPREATE_ERR);
    }

    public void buyerSendGoods(Long orderId, String companyName, String logisticsOrderIds) throws OrderStateException {
        throw new OrderStateException(ORDER_OPREATE_ERR);
    }

    public void cancle(Order order) throws OrderStateException, DaoException {
        throw new OrderStateException(ORDER_OPREATE_ERR);
    }

    public void cancelRefundGoods(Long orderId) throws OrderStateException {
        throw new OrderStateException(ORDER_OPREATE_ERR);
    }

    public void operationalAgreeAppeal(Long orderId) throws OrderStateException {
        throw new OrderStateException(ORDER_OPREATE_ERR);
    }

    public void operationalRejectAppeal(Long orderId) throws OrderStateException {
        throw new OrderStateException(ORDER_OPREATE_ERR);
    }

    public void cancelRefundMoney(Long orderId) throws OrderStateException {
        throw new OrderStateException(ORDER_OPREATE_ERR);
    }

    public void buyerComplain(Long orderId) throws OrderStateException {
        throw new OrderStateException(ORDER_OPREATE_ERR);
    }

    public void pay(Order order, String threePartId) throws OrderStateException, DaoException {
        throw new OrderStateException(ORDER_OPREATE_ERR);
    }

    public void refundGoods(Long orderId) throws OrderStateException {
        throw new OrderStateException(ORDER_OPREATE_ERR);
    }

    public void refundMoney(Long orderId) throws OrderStateException {
        throw new OrderStateException(ORDER_OPREATE_ERR);
    }

    public void sellerSendGoods(Long orderId, String companyName, String logisticsOrderIds) throws OrderStateException {
        throw new OrderStateException(ORDER_OPREATE_ERR);
    }

    public void sign(Order order) throws OrderStateException, DaoException {
        throw new OrderStateException(ORDER_OPREATE_ERR);
    }

    public void transaction(Long orderId) throws OrderStateException {
        throw new OrderStateException(ORDER_OPREATE_ERR);
    }

    public void sellerComplain(Long orderId) throws OrderStateException {
        throw new OrderStateException(ORDER_OPREATE_ERR);
    }
}