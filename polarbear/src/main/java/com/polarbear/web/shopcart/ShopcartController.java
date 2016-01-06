package com.polarbear.web.shopcart;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.polarbear.domain.User;
import com.polarbear.service.register.AppRegisterStep1Service;

@Controller
@RequestMapping("/shopcart")
public class ShopcartController {
    @Autowired(required = false)
    private AppRegisterStep1Service appRegisterStep1Service;

    @RequestMapping(value = { "/addShopcart.json" }, method = { RequestMethod.POST, RequestMethod.GET })
    @ResponseBody
    public Object addShopcart(HttpServletResponse response, HttpServletRequest request, @RequestParam("pid") String pid) {
        User u = (User) request.getAttribute("user");
        return "addShopcart pid=" + pid + ", name=" + u.getName();
    }
}