package com.polarbear.web.exception;

import static com.polarbear.util.Constants.ResultState.*;

import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.polarbear.util.JsonResult;

@Controller
public class UnCheckExceptionHandler {
    @RequestMapping(value = { "globalErr.json" }, method = { RequestMethod.GET })
    @ResponseBody
    public Object error(HttpServletResponse response, @RequestParam("code") String code) {
        if ("404".equals(code)) {
            return new JsonResult(RESOURCE_NOT_EXIST);
        }
        if ("500".equals(code)) {
            return new JsonResult(SERVER_ERR);
        }
        return new JsonResult(SERVER_ERR);
    }
}