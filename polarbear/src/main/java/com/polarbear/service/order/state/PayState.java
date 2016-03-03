package com.polarbear.service.order.state;

import static com.polarbear.util.Constants.ORDER_STATE.DELIVERY;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.polarbear.dao.BaseDao;
import com.polarbear.dao.DaoException;
import com.polarbear.domain.Logistic;
import com.polarbear.domain.Order;
import com.polarbear.service.order.OrderCommonComponent;
import com.polarbear.service.order.OrderState;
import com.polarbear.service.order.OrderStateException;
import com.polarbear.util.date.DateUtil;

@Component
public class PayState extends OrderState {
    @Autowired(required = false)
    OrderCommonComponent orderCommonComponent;
    @Autowired(required = false)
    BaseDao<Logistic> logisticDao;
    @Autowired(required = false)
    BaseDao<Order> orderDao;

    @Override
    public void delivery(Order order, String companyName, String logisticsOrderIds) throws OrderStateException, DaoException {
        order = orderCommonComponent.updateState(order, DELIVERY);
        recordOrderLogistic(order, companyName, logisticsOrderIds);
    }

    private void recordOrderLogistic(Order order, String companyName, String logisticsOrderIds) throws DaoException {
        Logistic logistic = new Logistic(companyName, logisticsOrderIds, order, DateUtil.getCurrentSeconds());
        logisticDao.store(logistic);
        orderDao.store(order);
    }

}