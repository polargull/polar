package com.polarbear.service.product.query;

import static com.polarbear.util.Constants.ResultState.PRODUCT_NOT_EXIST;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.polarbear.NullObjectException;
import com.polarbear.dao.BaseDao;
import com.polarbear.dao.DaoException;
import com.polarbear.domain.product.Product;
import com.polarbear.service.product.query.bean.NeedStyle;
import com.polarbear.service.product.util.StylePropertyTransferUtil;

@Service
public class MultipleStyleProductQuery {
    @Autowired(required = false)
    private BaseDao<Product> productDao;

    public Product querySameStyleProductByNeedStyle(NeedStyle needStyle) throws DaoException, NullObjectException {
        List<Product> sameStyleAllProducts = productDao.findByNamedQuery("querySameStyleProductByStyleId", needStyle.getStyleId());
        for (Product p : sameStyleAllProducts) {
            if (StylePropertyTransferUtil.propertyStrToJson(needStyle.getProperty()).equals(StylePropertyTransferUtil.propertyStrToJson(p.getExtProperty()))) {
                return p;
            }
        }
        throw new NullObjectException(PRODUCT_NOT_EXIST);
    }

}
