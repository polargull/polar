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
import com.polarbear.domain.ShopcartLog;
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
    BaseDao<ShopcartLog> shopcartLogDao;
    @Autowired
    ProductPicker productPicker;

    @Transactional
    public Shopcart addShopcart(long pid) throws DaoException, ValidateException {
        return addProductToShopcart(pid, 1);
    }

    @Transactional
    public Shopcart addProductToShopcart(long pid, int num) throws DaoException, ValidateException {
        return updateMyShopCart(pid, num);
    }

    @Transactional
    public Shopcart removeProductFromShopcart(long pid, int num) throws DaoException, ValidateException {
        return updateMyShopCart(pid, -num);
    }

    private Shopcart updateMyShopCart(long pid, int num) throws DaoException, ValidateException {
        Product p = productPicker.pickoutTheProduct(pid);
        Shopcart shopcart = getShopcart(p);
        updateShopCartNumAndPrice(p, num, shopcart);
        dbDelegateOp(p, num, shopcart);
        return shopcart;
    }

    private void dbDelegateOp(Product p, int num, Shopcart shopcart) throws DaoException {
        shopcartDao.store(shopcart);
        shopcartLogDao.store(new ShopcartLog(p, num, shopcart, DateUtil.getCurrentSeconds()));
    }

    private void updateShopCartNumAndPrice(Product p, int num, Shopcart shopcart) {
        shopcart.setProductNum(shopcart.getProductNum() + num);
        shopcart.setPrice(Arith.add(shopcart.getPrice(), p.getRealPrice()));
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