package com.polarbear.service.shopcart;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.polarbear.dao.BaseDao;
import com.polarbear.dao.DaoException;
import com.polarbear.domain.Product;
import com.polarbear.domain.Shopcart;
import com.polarbear.domain.ShopcartDetail;

@Component
public class RemoveShopcartProductComponent {
    @Autowired
    BaseDao<ShopcartDetail> shopcartDetailDao;
    
    public void removeProductFromShopcart(Shopcart shopcart, Product product) throws DaoException {
        ShopcartDetail sd = shopcartDetailDao.findByNamedQueryObject("queryByShopcartAndProduct", shopcart, product);
        if (sd != null) {
            shopcartDetailDao.delete(sd);
        }
    }
    
}