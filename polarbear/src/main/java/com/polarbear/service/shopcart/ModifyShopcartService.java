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

@Service
public class ModifyShopcartService {
    private Log log = LogFactory.getLog(ModifyShopcartService.class);
    @Autowired(required = false)
    BaseDao<Shopcart> shopcartDao;
    @Autowired(required = false)
    BaseDao<ShopcartDetail> shopcartDetailDao;
    @Autowired(required = false)
    ProductPicker productPicker;
    @Autowired(required = false)
    BaseDao<Product> productDao;
    @Autowired(required = false)
    RemoveShopcartProductComponent removeShopcartProductComponent;

    @Transactional
    public int addShopcart(long pid) throws DaoException, ValidateException {
        return updateProductNumFromShopCart(pid, 1).getProductNum();
    }

    @Transactional
    public Shopcart updateProductNumFromShopCart(long pid, int num) throws DaoException, ValidateException {
        Product p = productPicker.pickoutTheProduct(pid);
        return updateShopcarRelationOp(num, p);
    }

    @Transactional
    public Shopcart removeProductFromShopCart(long pid) throws DaoException, ValidateException {
        Product p = productDao.findById(Product.class, pid);
        int removeNum = removeShopcartProductComponent.removeProductFromShopcart(p);
        return updateShopCartNum(-removeNum);
    }

    private Shopcart updateShopcarRelationOp(int num, Product p) throws DaoException {
        Shopcart shopcart = updateShopCartNum(num);
        return updateShopCartDetail(shopcart, p, num);
    }

    private Shopcart updateShopCartNum(int num) throws DaoException {
        Shopcart shopcart = getShopcart();
        shopcart.setProductNum(shopcart.getProductNum() + num);
        shopcartDao.store(shopcart);
        return shopcart;
    }

    private Shopcart updateShopCartDetail(Shopcart shopcart, Product p, int num) throws DaoException {
        ShopcartDetail sd = getShopcartDetail(shopcart, p, num);
        shopcartDetailDao.store(sd);
        return shopcart;
    }

    private ShopcartDetail getShopcartDetail(Shopcart shopcart, Product p, int num) throws DaoException {
        ShopcartDetail sd = shopcartDetailDao.findByNamedQueryObject("queryByShopcartAndProduct", shopcart, p);
        if (sd == null) {
            return new ShopcartDetail(p, num, shopcart, DateUtil.getCurrentSeconds());
        }
        sd.setNum(num);
        return sd;
    }

    public Shopcart getShopcart() throws DaoException {
        User user = CurrentThreadUserFactory.getUser();
        Shopcart shopcart = shopcartDao.findByNamedQueryObject("queryUserId", user);
        if (shopcart == null) {
            shopcart = new Shopcart(user, DateUtil.getCurrentSeconds());
        }
        return shopcart;
    }
}