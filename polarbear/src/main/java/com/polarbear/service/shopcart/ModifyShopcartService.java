package com.polarbear.service.shopcart;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.polarbear.ValidateException;
import com.polarbear.dao.BaseDao;
import com.polarbear.dao.DaoException;
import com.polarbear.domain.Product;
import com.polarbear.domain.Shopcart;
import com.polarbear.domain.ShopcartDetail;
import com.polarbear.domain.User;
import com.polarbear.service.product.query.ProductPicker;
import com.polarbear.util.DateUtil;
import com.polarbear.util.factory.CurrentThreadUserFactory;
import com.polarbear.util.money.Arith;

@Service
public class ModifyShopcartService {
    private Log log = LogFactory.getLog(ModifyShopcartService.class);
    @Autowired
    BaseDao<Shopcart> shopcartDao;
    @Autowired
    BaseDao<ShopcartDetail> shopcartDetailDao;
    @Autowired
    ProductPicker productPicker;

    @Transactional
    public Shopcart addShopcart(long pid) throws DaoException, ValidateException {
        return increaseProductToShopcart(pid, 1);
    }

    @Transactional
    public Shopcart increaseProductToShopcart(long pid, int num) throws DaoException, ValidateException {
        return updateMyShopCart(pid, num);
    }

    @Transactional
    public Shopcart decreaseProductFromShopcart(long pid, int num) throws DaoException, ValidateException {
        return updateMyShopCart(pid, -num);
    }

    private Shopcart updateMyShopCart(long pid, int num) throws DaoException, ValidateException {
        Product p = productPicker.pickoutTheProduct(pid);
        Shopcart shopcart = updateShopCartNumAndPrice(p, num);
        updateShopCartDetail(shopcart, p, num);
        return shopcart;
    }    

    private void updateShopCartDetail(Shopcart shopcart, Product p, int num) throws DaoException {
        ShopcartDetail sd = getShopcartDetail(shopcart, p, num);
        shopcartDetailDao.store(sd);
    }

    private ShopcartDetail getShopcartDetail(Shopcart shopcart, Product p, int num) throws DaoException {
        ShopcartDetail sd = shopcartDetailDao.findByNamedQueryObject("queryByShopcartAndProduct", shopcart, p);
        if (sd == null) {
            return new ShopcartDetail(p, num, shopcart, DateUtil.getCurrentSeconds());
        }
        return sd;
    }

    private Shopcart updateShopCartNumAndPrice(Product p, int num) throws DaoException {
        Shopcart shopcart = getShopcart(p);
        shopcart.setProductNum(shopcart.getProductNum() + num);
        shopcart.setPrice(Arith.add(shopcart.getPrice(), p.getRealPrice() * num));
        shopcartDao.store(shopcart);
        return shopcart;
    }

    private Shopcart getShopcart(Product p) throws DaoException {
        User user = CurrentThreadUserFactory.getUser();
        Shopcart shopcart = shopcartDao.findByNamedQueryObject("queryUserId", user);
        if (shopcart == null) {
            shopcart = new Shopcart(user, DateUtil.getCurrentSeconds());
        }
        return shopcart;
    }
}