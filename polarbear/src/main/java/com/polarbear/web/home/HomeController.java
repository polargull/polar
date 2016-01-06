package com.polarbear.web.home;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class HomeController {
    // @Autowired
    // private BaseDao<User> userDao;

    // @Autowired
    // private BaseDao<Product, Long> productDao;
    @RequestMapping("index.html")
    public ModelAndView index() {
        // // User u = new User("fuwei","fuwei@126.com",123456l,(short) 1);
        // Object[] params = new Object[] { "fuwei", (short) 1 };
        // List<User> list =
        // userDao.findByNamedQueryByPage("findUserByGenderAndNameAndPage",
        // params, 1, 10);
        // for (User u : list) {
        // System.out.println(u.getName());
        // }
        return new ModelAndView("index");
    }
}
