package com.polarbear.service.shopcart;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.polarbear.ValidateException;
import com.polarbear.dao.BaseDao;
import com.polarbear.dao.DaoException;
import com.polarbear.domain.Product;
import com.polarbear.domain.Shopcart;
import com.polarbear.domain.ShopcartDetail;

@Service
public class ShopcartService {
    @Autowired
    ModifyShopcartService modifyShopcartService;
    @Autowired
    BaseDao<Shopcart> shopcartDao;
    @Autowired
    BaseDao<ShopcartDetail> shopcartDetailDao;
    @Autowired
    BaseDao<Product> productDao;

    @Transactional
    public int addProductToShopcart(long pid) throws DaoException, ValidateException {
        return modifyShopcartService.addShopcart(pid);
    }

    @Transactional
    public MyShopcart deleteProductFromShopcart(long pid) throws DaoException, ValidateException {
        Shopcart shopcart = modifyShopcartService.removeProductFromShopCart(pid);
        return new MyShopcart(shopcart, addShopcartProductList(shopcart));
    }

    @Transactional
    public MyShopcart modifyProductNumFromShopcart(long pid, int num) throws DaoException, ValidateException {
        Shopcart shopcart = modifyShopcartService.updateProductNumFromShopCart(pid, num);
        return new MyShopcart(shopcart, addShopcartProductList(shopcart));
    }

    @Transactional
    public MyShopcart getMyShopcart() throws DaoException {
        Shopcart shopcart = modifyShopcartService.getShopcart();
        return new MyShopcart(shopcart, addShopcartProductList(shopcart));
    }

    private List<ShopcartProduct> addShopcartProductList(Shopcart shopcart) throws DaoException {
        List<ShopcartProduct> productList = new ArrayList<ShopcartProduct>();
        List<ShopcartDetail> sdLst = shopcartDetailDao.findByNamedQuery("queryByShopcart", shopcart);
        for (ShopcartDetail sd : sdLst) {
            productList.add(new ShopcartProduct(sd.getProduct(), sd.getNum()));
        }
        return productList;
    }

    /**
     * 未登录时,返回购物车数据
     * 
     * @param shopcartCookieData
     *            pid_num@pid_num@
     * @return
     * @throws DaoException
     */
    @SuppressWarnings({ "unchecked", "serial" })
    @Transactional
    public Shopcart getShopcart(String shopcartCookieData) throws DaoException {
        final List<Long> pidList = parseShopcartCookieDataReturnPidList(shopcartCookieData);
        Map<String,List> param = new HashMap<String,List>(){{
            put("ids", pidList);
        }};
        List<Product> productList = productDao.findByNamedQuery("queryPutOnProductByIds", param);
        return null;
    }

    private Map<Long, Integer> parseShopcartCookieDataReturnMap(String shopcartCookieData) {
        String[] pid_num_array = shopcartCookieData.split("@");
        Map<Long, Integer> pid_num_map = new HashMap<Long, Integer>();
        for (String pid_num : pid_num_array) {
            long pid = Long.parseLong(pid_num.split("_")[0]);
            int num = Integer.parseInt(pid_num.split("_")[1]);
            pid_num_map.put(pid, num);
        }
        return pid_num_map;
    }

    private List<Long> parseShopcartCookieDataReturnPidList(String shopcartCookieData) {
        String[] pid_num_array = shopcartCookieData.split("@");
        List<Long> pidList = new ArrayList<Long>();
        for (String pid_num : pid_num_array) {
            long pid = Long.parseLong(pid_num.split("_")[0]);
            pidList.add(pid);
        }
        return pidList;
    }
}