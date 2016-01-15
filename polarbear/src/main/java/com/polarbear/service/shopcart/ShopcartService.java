package com.polarbear.service.shopcart;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.polarbear.ValidateException;
import com.polarbear.dao.BaseDao;
import com.polarbear.dao.DaoException;
import com.polarbear.domain.Product;
import com.polarbear.domain.Shopcart;
import com.polarbear.domain.User;
import com.polarbear.service.product.query.ProductPicker;
import com.polarbear.util.DateUtil;
import com.polarbear.util.factory.CurrentThreadUserFactory;

public class ShopcartService {
    private Log log = LogFactory.getLog(ShopcartService.class);
    @Autowired
    BaseDao<Shopcart> shopcartDao;
    @Autowired
    RemoveShopcartProductComponent removeShopcartProductComponent;    
    @Autowired
    ProductPicker productPicker;
    
    public MyShopcart removeProductFromShopcart(long pid) throws DaoException, ValidateException {
        Shopcart shopcart = getShopcart();
        removeShopcartProductComponent.removeProductFromShopcart(shopcart, getProduct(pid));
        return null;
    }

    private Product getProduct(long pid) throws DaoException, ValidateException {
        return productPicker.pickoutTheProduct(pid);
    }
    
    private Shopcart getShopcart() throws DaoException {
        User user = CurrentThreadUserFactory.getUser();
        Shopcart shopcart = shopcartDao.findByNamedQueryObject("queryUserId", user);
        if (shopcart == null) {
            shopcart = new Shopcart(user, DateUtil.getCurrentSeconds());
        }
        return shopcart;
    }
}