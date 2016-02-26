package com.polarbear.web.order;

import static com.polarbear.util.Constants.ResultState.PARAM_ERR;
import static com.polarbear.util.Constants.ResultState.SUCCESS;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.polarbear.ValidateException;
import com.polarbear.dao.DaoException;
import com.polarbear.service.order.OrderService;
import com.polarbear.service.order.OrderStateException;
import com.polarbear.service.order.bean.OrderParam;
import com.polarbear.util.JsonResult;

@Controller
@RequestMapping("/order")
public class OrderController {
    private Log log = LogFactory.getLog(OrderController.class);
    @Autowired(required = false)
    private OrderService orderService;

    @RequestMapping(value = { "createOrder.json" }, method = { RequestMethod.POST, RequestMethod.GET })
    @ResponseBody
    public Object createOrder(@ModelAttribute OrderParam orderParam) throws ValidateException, DaoException, OrderStateException {
        log.debug(orderParam.toString());
        validate(orderParam);
        return new JsonResult(SUCCESS).put(orderService.createOrder(orderParam));
    }

    private void validate(OrderParam orderParam) throws ValidateException {
        if (orderParam.getPids() == null || orderParam.getNums() == null || orderParam.getBuyMode() == null || orderParam.getPayCode() == null || orderParam.getAddressId() == null) {
            throw new ValidateException(PARAM_ERR.emsg());
        }
        if (orderParam.getPids().length != orderParam.getNums().length) {
            throw new ValidateException(PARAM_ERR.emsg());
        }
    }
}
