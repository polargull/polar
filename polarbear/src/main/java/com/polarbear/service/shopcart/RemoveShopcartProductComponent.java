package com.polarbear.service.shopcart;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.polarbear.dao.BaseDao;
import com.polarbear.dao.DaoException;
import com.polarbear.domain.Product;
import com.polarbear.domain.Shopcart;
import com.polarbear.domain.ShopcartDetail;
import com.polarbear.domain.User;
import com.polarbear.util.factory.CurrentThreadUserFactory;

@Component
public class RemoveShopcartProductComponent {
    @Autowired
    BaseDao<ShopcartDetail> shopcartDetailDao;
    @Autowired
    BaseDao<Shopcart> shopcartDao;
    @Autowired
    BaseDao<Product> productDao;

    public void removeProductFromShopcart(long pid) throws DaoException {
        User u = CurrentThreadUserFactory.getUser();
        Product p = productDao.findById(Product.class, pid);
        Shopcart shopcart = shopcartDao.findByNamedQueryObject("queryUserId", u);
        ShopcartDetail shopcartDetail = shopcartDetailDao.findByNamedQueryObject("queryByShopcartAndProduct", shopcart, p);
        shopcartDetailDao.delete(shopcartDetail);
    }

}