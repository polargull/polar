package com.polarbear.service.order.state;

import static com.polarbear.service.product.ModifyProductComponent.DECREASE;
import static com.polarbear.util.Constants.ORDER_STATE.CANCLE;
import static com.polarbear.util.Constants.ORDER_STATE.PAYED;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.polarbear.dao.BaseDao;
import com.polarbear.dao.DaoException;
import com.polarbear.domain.Order;
import com.polarbear.domain.Pay;
import com.polarbear.domain.ToPayLog;
import com.polarbear.service.PageList;
import com.polarbear.service.order.OrderCommonComponent;
import com.polarbear.service.order.OrderState;
import com.polarbear.service.order.OrderStateException;
import com.polarbear.service.product.ModifyProductComponent;
import com.polarbear.util.date.DateUtil;

@Component
public class UnpayState extends OrderState {
    @Autowired(required = false)
    OrderCommonComponent orderCommonComponent;
    @Autowired(required = false)
    ModifyProductComponent modifyProductComponent;
    @Autowired(required = false)
    BaseDao<Pay> payDao;
    @Autowired(required = false)
    BaseDao<ToPayLog> toPayLogDao;

    @Override
    public void cancle(Order order) throws OrderStateException, DaoException {
        orderCommonComponent.updateState(order, CANCLE);
        modifyProductComponent.modifyProductNum(order, DECREASE);
    }

    @Override
    public void pay(Order order, String threePartNo) throws OrderStateException, DaoException {
        orderCommonComponent.updateState(order, PAYED);
        int payPlatform = getPayPlatform(order);
        int curTime = DateUtil.getCurrentSeconds();
        payDao.store(new Pay(payPlatform, threePartNo, curTime, order));
    }

    private int getPayPlatform(Order order) throws DaoException {
        PageList<ToPayLog> list = toPayLogDao.findByNamedQueryByPage("queryLastOneLogByOrder", 1, 1, order);
        return list.getList().get(0).getPayPlatform();
    }
}