package com.polarbear.service.shopcart;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.polarbear.dao.BaseDao;
import com.polarbear.domain.Product;
import com.polarbear.domain.Shopcart;
import com.polarbear.domain.User;
import com.polarbear.util.factory.CurrentThreadUserFactory;

@Service
public class AddShopcartService {
    private Log log = LogFactory.getLog(AddShopcartService.class);
    @Autowired
    BaseDao<Shopcart> shopcartDao;
    @Autowired
    BaseDao<Product> productDao;

    public MyShopcart addShopcart(long pid) {
        User user = CurrentThreadUserFactory.getUser();
        
        return null;
    }
}
