package com.polarbear.web.shopcart;

import static com.polarbear.util.Constants.ResultState.PARAM_ERR;
import static com.polarbear.util.Constants.ResultState.SUCCESS;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.math.NumberUtils;
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
import com.polarbear.service.shopcart.ModifyShopcartService;
import com.polarbear.util.JsonResult;
import com.polarbear.util.cookie.CookieHelper;

@Controller
@RequestMapping("/shopcart")
public class ShopcartController {
    private Log                log = LogFactory.getLog(ShopcartController.class);
    public static String COUNT = "count";

    @Autowired(required = false)
    private ModifyShopcartService modifyShopcartService;

    @RequestMapping(value = { "/addShopcart.json" }, method = { RequestMethod.POST, RequestMethod.GET })
    @ResponseBody
    public Object addShopcart(HttpServletResponse response, HttpServletRequest request, @RequestParam("pid") String pid) throws ValidateException, DaoException {
        log.debug("pid=" + pid);
        validate(pid);
        int count = modifyShopcartService.addShopcart(Long.valueOf(pid)).getProductNum();
        CookieHelper.setCookie(response, COUNT, String.valueOf(count));
        log.debug("pid = " + pid + ", op successful!");
        return new JsonResult(SUCCESS);
    }

    private void validate(String pid) throws ValidateException {
        if (!NumberUtils.isDigits(pid)) {
            throw new ValidateException(PARAM_ERR);
        }
    }
}