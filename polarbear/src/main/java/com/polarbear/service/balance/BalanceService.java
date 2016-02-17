package com.polarbear.service.balance;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.polarbear.dao.BaseDao;
import com.polarbear.dao.DaoException;
import com.polarbear.domain.Address;
import com.polarbear.domain.product.Product;
import com.polarbear.service.balance.to.BalanceDataTO;
import com.polarbear.service.balance.to.BuyProduct;

@Service
public class BalanceService {
    @Autowired
    BaseDao<Product> productDao;
    @Autowired
    BaseDao<Address> addressDao;

    @SuppressWarnings( { "serial", "unchecked" })
    public BalanceDataTO balance(final Long[] pids, final Integer[] nums) throws DaoException {
        Map<Long, Integer> pid_num = new HashMap<Long, Integer>();
        for (int i = 0; i < pids.length; i++) {
            pid_num.put(pids[i], nums[i]);
        }
        final List<Long> pidLst = new ArrayList<Long>();
        Collections.addAll(pidLst, pids);
        Map<String, List> param = new HashMap<String, List>() {
            {
                put("ids", pidLst);
            }
        };
        List<Product> productLst = productDao.findByNamedQuery("queryPutOnProductByIds", param);
        List<BuyProduct> buyProductLst = new ArrayList<BuyProduct>();
        for (Product p : productLst) {
            BuyProduct buyProduct = new BuyProduct(p, pid_num.get(p.getId()));
            buyProductLst.add(buyProduct);
        }
        Address receiveAddress = addressDao.findByNamedQueryObject("queryDefaultAddress");
        return new BalanceDataTO(receiveAddress, buyProductLst);
    }
}