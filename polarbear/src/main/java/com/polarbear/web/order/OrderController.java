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
import org.springframework.web.bind.annotation.RequestParam;
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

    @RequestMapping(value = { "cancleOrder.json" }, method = { RequestMethod.POST, RequestMethod.GET })
    @ResponseBody
    public Object cancle(@RequestParam("orderId") long orderId, @RequestParam(value = "reason", required = false) String reason) throws ValidateException, DaoException,
            OrderStateException {
        log.debug("orderId:" + orderId + "reason:" + reason);
        orderService.cancle(orderId, reason);
        return new JsonResult(SUCCESS);
    }

    @RequestMapping(value = { "toPayOrder.json" }, method = { RequestMethod.POST, RequestMethod.GET })
    @ResponseBody
    public Object toPayOrder(@RequestParam("orderId") long orderId, @RequestParam(value = "payPlatform") int payPlatform) throws ValidateException, DaoException,
            OrderStateException {
        log.debug("orderId:" + orderId + "payPlatform:" + payPlatform);
        orderService.toPay(orderId, payPlatform);
        return new JsonResult(SUCCESS);
    }

    @RequestMapping(value = { "payOrder.json" }, method = { RequestMethod.POST, RequestMethod.GET })
    @ResponseBody
    public Object pay(@RequestParam("orderId") long orderId, @RequestParam(value = "threePartId") String threePartId) throws ValidateException, DaoException, OrderStateException {
        log.debug("orderId:" + orderId + "threePartId:" + threePartId);
        orderService.pay(orderId, threePartId);
        return new JsonResult(SUCCESS);
    }

    @RequestMapping(value = { "signOrder.json" }, method = { RequestMethod.POST, RequestMethod.GET })
    @ResponseBody
    public Object singnin(@RequestParam("orderId") long orderId) throws ValidateException, DaoException, OrderStateException {
        log.debug("orderId:" + orderId);
        orderService.sign(orderId);
        return new JsonResult(SUCCESS);
    }

    @RequestMapping(value = { "getMyOrderDetail.json" }, method = { RequestMethod.POST, RequestMethod.GET })
    @ResponseBody
    public Object cancle(@RequestParam("orderId") long orderId) throws ValidateException, DaoException, OrderStateException {
        log.debug("orderId:" + orderId);
        return new JsonResult(SUCCESS).put(orderService.getMyOrderDetail(orderId));
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
