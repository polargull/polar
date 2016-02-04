package com.polarbear.service.product;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.polarbear.ValidateException;
import com.polarbear.dao.BaseDao;
import com.polarbear.dao.DaoException;
import com.polarbear.domain.Product;
import com.polarbear.service.PageList;

@Service
public class ProductManagerService {
    private Log log = LogFactory.getLog(ProductManagerService.class);
    @Autowired(required = false)
    BaseDao<Product> productDao;

    @Transactional
    public PageList<Product> productList(String name, int pageNo, int pageSize) throws DaoException, ValidateException {
        StringBuilder hqlCondition = new StringBuilder();
        if (!StringUtils.isEmpty(name)) {
            hqlCondition.append("name=").append(name);
        }
        return productDao.findPageListByDynamicCondition(Product.class, pageNo, pageSize, hqlCondition.toString());
    }
}