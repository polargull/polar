package com.polarbear.service.topaylog;

import static com.polarbear.util.Constants.ORDER_STATE.UNPAY;
import static com.polarbear.util.Constants.ResultState.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.polarbear.dao.BaseDao;
import com.polarbear.dao.DaoException;
import com.polarbear.domain.Order;
import com.polarbear.domain.ToPayLog;
import com.polarbear.service.order.OrderQueryProxy;
import com.polarbear.service.order.OrderStateException;
import com.polarbear.util.date.DateUtil;

@Component
public class ToPayLogComponent {
    @Autowired(required = false)
    OrderQueryProxy orderQueryProxy;
    @Autowired(required = false)
    BaseDao<ToPayLog> toPayLogDao;

    public void createToPayLog(long orderId, int payPlatform) throws DaoException, OrderStateException {
        Order order = orderQueryProxy.queryOrderById(orderId);
        if (order.getState() != UNPAY.value())
            throw new OrderStateException(ORDER_OPREATE_ERR);
        int curTime = DateUtil.getCurrentSeconds();
        toPayLogDao.store(new ToPayLog(payPlatform, curTime, order));
    }
}