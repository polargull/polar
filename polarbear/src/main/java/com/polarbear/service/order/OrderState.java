package com.polarbear.service.order;

public abstract class OrderState {

    public void agreeRefundGoods(Long orderId) throws OrderStateException {
        throw new OrderStateException(OrderStateException.OPREATE_ERR);
    }

    public void buyerSendGoods(Long orderId, String companyName, String logisticsOrderIds) throws OrderStateException {
        throw new OrderStateException(OrderStateException.OPREATE_ERR);
    }

    public void buyerCancelOrder(Long orderId, String reason) throws OrderStateException {
        throw new OrderStateException(OrderStateException.OPREATE_ERR);
    }

    public void cancelRefundGoods(Long orderId) throws OrderStateException {
        throw new OrderStateException(OrderStateException.OPREATE_ERR);
    }

    public void sellerCancelOrder(Long orderId) throws OrderStateException {
        throw new OrderStateException(OrderStateException.OPREATE_ERR);
    }

    public void operationalAgreeAppeal(Long orderId) throws OrderStateException {
        throw new OrderStateException(OrderStateException.OPREATE_ERR);
    }

    public void operationalRejectAppeal(Long orderId) throws OrderStateException {
        throw new OrderStateException(OrderStateException.OPREATE_ERR);
    }

    public void cancelRefundMoney(Long orderId) throws OrderStateException {
        throw new OrderStateException(OrderStateException.OPREATE_ERR);
    }

    public void buyerComplain(Long orderId) throws OrderStateException {
        throw new OrderStateException(OrderStateException.OPREATE_ERR);
    }

    public void pay(Long orderId, String thirdOrderId) throws OrderStateException {
        throw new OrderStateException(OrderStateException.OPREATE_ERR);
    }

    public void refundGoods(Long orderId) throws OrderStateException {
        throw new OrderStateException(OrderStateException.OPREATE_ERR);
    }

    public void refundMoney(Long orderId) throws OrderStateException {
        throw new OrderStateException(OrderStateException.OPREATE_ERR);
    }

    public void sellerSendGoods(Long orderId, String companyName, String logisticsOrderIds) throws OrderStateException {
        throw new OrderStateException(OrderStateException.OPREATE_ERR);
    }

    public void signin(Long orderId) throws OrderStateException {
        throw new OrderStateException(OrderStateException.OPREATE_ERR);
    }

    public void transaction(Long orderId) throws OrderStateException {
        throw new OrderStateException(OrderStateException.OPREATE_ERR);
    }

    public void sellerComplain(Long orderId) throws OrderStateException {
        throw new OrderStateException(OrderStateException.OPREATE_ERR);
    }
}