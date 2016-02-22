package com.polarbear.service.shopcart;

import java.util.Iterator;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.polarbear.ValidateException;
import com.polarbear.dao.BaseDao;
import com.polarbear.dao.DaoException;
import com.polarbear.domain.Shopcart;
import com.polarbear.domain.ShopcartDetail;
import com.polarbear.domain.User;
import com.polarbear.domain.product.Product;
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
    private final String ADD_SHOPCART = "addShopcart";
    private final String MODIYF_SHOPCART = "modifyShopcart";

    @Transactional
    public int addShopcart(long pid, int nums) throws DaoException, ValidateException {
        Product p = productPicker.pickoutTheProduct(pid);
        Shopcart shopcart = addOrRemoveShopCartNum(nums);
        return updateShopCartDetail(shopcart, p, 1, ADD_SHOPCART).getProductNum();
    }

    @Transactional
    public Shopcart removeProductFromShopCart(long pid) throws DaoException, ValidateException {
        Product p = productDao.findById(Product.class, pid);
        int removeNum = removeShopcartProductComponent.removeProductFromShopcart(p);
        return addOrRemoveShopCartNum(-removeNum);
    }

    @Transactional
    public Shopcart updateProductNumFromShopCart(long pid, int num) throws DaoException, ValidateException {
        Product p = productPicker.pickoutTheProduct(pid);
        Shopcart shopcart = updateShopCartNum(p, num);
        return updateShopCartDetail(shopcart, p, num, MODIYF_SHOPCART);
    }

    @Transactional
    public void synchClientShopcartDataToServer(Map<Long, Integer> clientShopcartData) throws DaoException, ValidateException {
        Iterator iter = clientShopcartData.entrySet().iterator();
        while (iter.hasNext()) {
            Map.Entry entry = (Map.Entry) iter.next();
            Long pid = (Long) entry.getKey();
            Integer nums = (Integer) entry.getValue();
            if (getShopcart().getId() == null) {
                addShopcart(pid, nums);
                continue;
            }
            updateProductNumFromShopCart(pid, nums);
        }
    }

    private Shopcart addOrRemoveShopCartNum(int num) throws DaoException {
        Shopcart shopcart = getShopcart();
        shopcart.setProductNum(shopcart.getProductNum() + num);
        shopcartDao.store(shopcart);
        return shopcart;
    }

    private Shopcart updateShopCartNum(Product p, int num) throws DaoException {
        Shopcart shopcart = getShopcart();
        ShopcartDetail sd = getShopcartDetail(shopcart, p);
        shopcart.setProductNum(shopcart.getProductNum() - sd.getNum() + num);
        shopcartDao.store(shopcart);
        return shopcart;
    }

    private Shopcart updateShopCartDetail(Shopcart shopcart, Product p, int num, String op) throws DaoException {
        ShopcartDetail sd = getShopcartDetail(shopcart, p);
        if (ADD_SHOPCART.equals(op)) {
            sd.setNum(sd.getNum() + num);
        } else if (MODIYF_SHOPCART.equals(op)) {
            sd.setNum(num);
        }
        shopcartDetailDao.store(sd);
        return shopcart;
    }

    private ShopcartDetail getShopcartDetail(Shopcart shopcart, Product p) throws DaoException {
        ShopcartDetail sd = shopcartDetailDao.findByNamedQueryObject("queryByShopcartAndProduct", shopcart, p);
        if (sd == null) {
            return new ShopcartDetail(p, 0, shopcart, DateUtil.getCurrentSeconds());
        }
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