package com.polarbear.service.product.query;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.polarbear.dao.BaseDao;
import com.polarbear.domain.Product;
import com.polarbear.service.product.query.bean.NeedStyle;

public class MultipleStyleProductQuery {
    @Autowired
    private BaseDao<Product> productDao;

    public Product querySameStyleProductByNeedStyle(NeedStyle needStyle) {
        List<Product> sameStyleAllProducts = productDao.findByNamedQuery("querySameStyleProductByStyleId", needStyle.getStyleId());
        for (Product p : sameStyleAllProducts) {
            if (needStyle.getProperty().equals(p.getExtProperty())) {
                return p;
            }
        }
        return null;
    }

}
