package com.polarbear.service.shopcart;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.polarbear.ValidateException;
import com.polarbear.dao.BaseDao;
import com.polarbear.dao.DaoException;
import com.polarbear.domain.Shopcart;
import com.polarbear.domain.ShopcartDetail;
import com.polarbear.domain.User;
import com.polarbear.util.factory.CurrentThreadUserFactory;

@Service
public class ShopcartService {
    @Autowired
    ModifyShopcartService modifyShopcartService;
    @Autowired
    BaseDao<Shopcart> shopcartDao;

    @Transactional
    public int addProductToShopcart(long pid) throws DaoException, ValidateException {
        return modifyShopcartService.addShopcart(pid);
    }
    
    @Transactional
    public MyShopcart getMyShopcart() throws DaoException {
        Shopcart shopcart = modifyShopcartService.getShopcart();
        List<ShopcartProduct> productList = new ArrayList<ShopcartProduct>();
        List<ShopcartDetail> sdLst = shopcart.getShopcartDetails();
        for (ShopcartDetail sd : sdLst) {
            productList.add(new ShopcartProduct(sd.getProduct(),sd.getNum()));
        }
        return new MyShopcart(shopcart,productList);
    }

}