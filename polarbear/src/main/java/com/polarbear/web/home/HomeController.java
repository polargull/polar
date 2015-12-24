package com.polarbear.web.home;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.polarbear.dao.BaseDao;
import com.polarbear.domain.User;

@Controller
@RequestMapping("/index.html")
public class HomeController {
//    @Autowired
//    private BaseDao<User> userDao;

    @Autowired
    // private BaseDao<Product, Long> productDao;
    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView index() {
//        // User u = new User("fuwei","fuwei@126.com",123456l,(short) 1);
//        Object[] params = new Object[] { "fuwei", (short) 1 };
//        List<User> list = userDao.findByNamedQueryByPage("findUserByGenderAndNameAndPage", params, 1, 10);
//        for (User u : list) {
//            System.out.println(u.getName());
//        }
        return new ModelAndView("index");
    }
}
