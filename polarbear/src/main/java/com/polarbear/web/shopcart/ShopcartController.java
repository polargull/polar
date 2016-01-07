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
import com.polarbear.service.shopcart.AddShopcartService;
import com.polarbear.service.shopcart.MyShopcart;
import com.polarbear.util.JsonResult;

@Controller
@RequestMapping("/shopcart")
public class ShopcartController {
    private Log log = LogFactory.getLog(ShopcartController.class);

    @Autowired(required = false)
    private AddShopcartService AddShopcartService;

    @RequestMapping(value = { "/addShopcart.json" }, method = { RequestMethod.POST, RequestMethod.GET })
    @ResponseBody
    public Object addShopcart(HttpServletResponse response, HttpServletRequest request, @RequestParam("pid") String pid) {
        log.debug("addShopcart begin!");
        try {
            validate(pid);
            AddShopcartService.addShopcart(Long.valueOf(pid));
            log.debug("addShopcart end!");
        } catch (ValidateException e) {
            return new JsonResult(e.state);
        }
        return new JsonResult(SUCCESS).put(new MyShopcart());
    }
    
    private void validate(String pid) throws ValidateException {
        if (!NumberUtils.isDigits(pid)) {
            throw new ValidateException(PARAM_ERR);
        }
    }
}