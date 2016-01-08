package com.polarbear.service.product.query;

import static com.polarbear.util.Constants.PRODUCT_STATE.PUT_ON;
import static com.polarbear.util.Constants.ResultState.PRODUCT_PULL_OFF;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.polarbear.ValidateException;
import com.polarbear.dao.BaseDao;
import com.polarbear.dao.DaoException;
import com.polarbear.domain.Product;

@Service
public class ProductPicker {
    @Autowired
    BaseDao<Product> productDao;

    public Product pickoutTheProdct(long pid) throws DaoException, ValidateException {
        Product p = productDao.findByNamedQueryObject("queryProductByIdAndState", pid, PUT_ON.value());
        if (p == null)
            throw new ValidateException(PRODUCT_PULL_OFF);
        if (p.getNum() == 0)
            throw new ValidateException(PRODUCT_PULL_OFF);
        return p;
    }
}