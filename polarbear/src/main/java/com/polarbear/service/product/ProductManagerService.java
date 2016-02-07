package com.polarbear.service.product;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.polarbear.dao.BaseDao;
import com.polarbear.dao.DaoException;
import com.polarbear.domain.Product;
import com.polarbear.service.PageList;

@Service
public class ProductManagerService {
    @Autowired(required = false)
    BaseDao<Product> productDao;

    @Transactional
    public PageList<Product> productList(String params, int pageNo, int pageSize) throws DaoException {
        StringBuilder hqlCondition = new StringBuilder();
        String[] paramStrs = params.split(";");
        for (int i = 0; i < paramStrs.length; i++) {
            hqlCondition.append(buildProductName(paramStrs[i]));
            hqlCondition.append(buildSaleTimeRang(paramStrs[i]));
            hqlCondition.append(buildStyle(paramStrs[i]));
            if (i != paramStrs.length - 1 && !StringUtils.isEmpty(hqlCondition.toString())) {
                hqlCondition.append(" and ");
            }
        }
        return productDao.findPageListByDynamicCondition(Product.class, pageNo, pageSize, hqlCondition.toString());
    }

    private String buildProductName(String paramStr) {
        // saleTimeRrang:int-int;style:全部;name:羽绒服
        String[] param = paramStr.split(":");
        if (param[0].equals("name") && !StringUtils.isEmpty(param[1])) {
            return new StringBuilder("name like '%").append(param[1]).append("%'").toString();
        }
        return "";
    }

    private String buildSaleTimeRang(String paramStr) {
        // saleTimeRrang:int-int;style:全部;name:羽绒服
        String[] param = paramStr.split(":");
        if (param[0].equals("saleTimeRrang") && !StringUtils.isEmpty(param[1])) {
            String timeRang[] = param[1].split("-");
            return new StringBuilder("(saleBeginTime >= ").append(timeRang[0]).append(" and ").append("saleEndTime =< ").append(timeRang[1]).append(")").toString();
        }
        return "";
    }

    private String buildStyle(String paramStr) {
        // saleTimeRrang:int-int;style:全部;name:羽绒服
        String[] param = paramStr.split(":");
        if (param[0].equals("style") && !StringUtils.isEmpty(param[1]) && !param[1].equals("全部")) {
            if (param[1].equals("有"))
                return new StringBuilder("productStyle != null").toString();
            if (param[1].equals("无"))
                return new StringBuilder("productStyle = null").toString();
        }
        return "";
    }
}