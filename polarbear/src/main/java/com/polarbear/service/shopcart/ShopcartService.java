package com.polarbear.service.shopcart;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.polarbear.dao.BaseDao;
import com.polarbear.dao.DaoException;
import com.polarbear.domain.Product;
import com.polarbear.domain.Shopcart;
import com.polarbear.domain.ShopcartDetail;
import com.polarbear.domain.User;
import com.polarbear.util.DateUtil;
import com.polarbear.util.factory.CurrentThreadUserFactory;

@Service
public class ShopcartService {
    @Autowired
    RemoveShopcartProductComponent removeShopcartProductComponent;
    @Autowired
    BaseDao<Shopcart> shopcartDao;
    @Autowired
    BaseDao<Product> productDao;

    public MyShopcart removeProductFromShopcart(long pid) throws DaoException {
        Product p = getProductById(pid);
        removeShopcartProductComponent.removeProductFromShopcart(p);
        return convertMyShopcart(getShopcart(p));
    }

    private MyShopcart convertMyShopcart(Shopcart shopcart) {
        Iterator<ShopcartDetail> it = shopcart.getShopcartDetails().iterator();
        List<ShopcartProduct> productList = new ArrayList<ShopcartProduct>();
        while (it.hasNext()) {
            ShopcartDetail sd = it.next();
            productList.add(new ShopcartProduct(sd.getProduct(), sd.getNum()));
        }
        return new MyShopcart(shopcart, productList);
    }

    private Shopcart getShopcart(Product p) throws DaoException {
        User user = CurrentThreadUserFactory.getUser();
        Shopcart shopcart = shopcartDao.findByNamedQueryObject("queryUserId", user);
        if (shopcart == null) {
            shopcart = new Shopcart(user, DateUtil.getCurrentSeconds());
        }
        return shopcart;
    }

    private Product getProductById(long pid) throws DaoException {
        return productDao.findById(Product.class, pid);
    }
}