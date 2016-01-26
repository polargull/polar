package com.polarbear.service.shopcart;

import static com.polarbear.util.Constants.ResultState.SHOPCART_PARSE_ERR;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.polarbear.ParseException;
import com.polarbear.ValidateException;
import com.polarbear.dao.BaseDao;
import com.polarbear.dao.DaoException;
import com.polarbear.domain.Product;
import com.polarbear.domain.Shopcart;
import com.polarbear.domain.ShopcartDetail;
import com.polarbear.util.factory.CurrentThreadUserFactory;

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
    public MyShopcart getMyShopcart(String shopcartCookieData) throws DaoException, ParseException {
        if (CurrentThreadUserFactory.getUser() == null) {
            return getClientMyShopcart(shopcartCookieData);
        }
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
     * @throws ParseException
     */
    @Transactional
    public MyShopcart getClientMyShopcart(String shopcartCookieData) throws DaoException, ParseException {
        final List<Long> pidList = parseShopcartCookieDataReturnPidList(shopcartCookieData);
        List<ShopcartProduct> shopcartProductList = wrapShopcartProductList(shopcartCookieData, pidList);
        return new MyShopcart(shopcartProductList);
    }

    @SuppressWarnings( { "unchecked", "serial" })
    private List<ShopcartProduct> wrapShopcartProductList(String shopcartCookieData, final List<Long> pidList) throws DaoException, ParseException {
        Map<String, List> param = new HashMap<String, List>() {
            {
                put("ids", pidList);
            }
        };
        List<Product> productList = productDao.findByNamedQuery("queryPutOnProductByIds", param);
        Map<Long, Integer> pid_num_map = parseShopcartCookieDataReturnMap(shopcartCookieData);
        List<ShopcartProduct> shopcartProductList = new ArrayList<ShopcartProduct>();
        for (Product p : productList) {
            shopcartProductList.add(new ShopcartProduct(p, pid_num_map.get(p.getId())));
        }
        return shopcartProductList;
    }

    private Map<Long, Integer> parseShopcartCookieDataReturnMap(String shopcartCookieData) throws ParseException {
        String[] pid_num_array = shopcartCookieData.split("\\|");
        Map<Long, Integer> pid_num_map = new HashMap<Long, Integer>();
        try {
            for (String pid_num : pid_num_array) {
                long pid = Long.parseLong(pid_num.split("_")[0]);
                int num = Integer.parseInt(pid_num.split("_")[1]);
                pid_num_map.put(pid, num);
            }
        } catch (Exception e) {
            throw new ParseException(SHOPCART_PARSE_ERR);
        }
        return pid_num_map;
    }

    private List<Long> parseShopcartCookieDataReturnPidList(String shopcartCookieData) throws ParseException {
        if (shopcartCookieData == null) {
            throw new ParseException(SHOPCART_PARSE_ERR);
        }
        Map<Long, Integer> pid_num_map = parseShopcartCookieDataReturnMap(shopcartCookieData);
        List<Long> pidList = new ArrayList<Long>(pid_num_map.keySet());
        return pidList;
    }
}
