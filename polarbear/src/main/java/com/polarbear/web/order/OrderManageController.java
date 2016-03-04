package com.polarbear.web.order;

import static com.polarbear.util.Constants.ResultState.SUCCESS;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.polarbear.ValidateException;
import com.polarbear.dao.DaoException;
import com.polarbear.service.order.OrderService;
import com.polarbear.service.order.OrderStateException;
import com.polarbear.util.JsonResult;
import com.polarbear.web.interceptor.login.AdminAuth;

@Controller
@RequestMapping("/back")
public class OrderManageController {
    private Log log = LogFactory.getLog(OrderManageController.class);
    @Autowired(required = false)
    private OrderService orderService;

    @RequestMapping(value = { "deliveryOrder.json" }, method = { RequestMethod.POST, RequestMethod.GET })
    @ResponseBody
    @AdminAuth
    public Object deliveryOrder(@RequestParam("orderId") long orderId, @RequestParam("companyName") String companyName, @RequestParam("logisticsOrderIds") String logisticsOrderIds)
            throws ValidateException, DaoException, OrderStateException {
        log.debug("orderId:" + ", companyName:" + companyName + ", logisticsOrderIds:" + logisticsOrderIds);
        orderService.delivery(orderId, companyName, logisticsOrderIds);
        return new JsonResult(SUCCESS);
    }

}