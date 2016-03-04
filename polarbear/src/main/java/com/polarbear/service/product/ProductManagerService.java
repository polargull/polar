package com.polarbear.service.product;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.polarbear.dao.BaseDao;
import com.polarbear.dao.DaoException;
import com.polarbear.domain.product.Product;
import com.polarbear.service.PageList;

@Service
public class ProductManagerService {
    @Autowired(required = false)
    BaseDao<Product> productDao;
    private static final String HQL_AND_CONTACT = " and ";

    @Transactional(readOnly = true)
    public PageList<Product> productList(String params, int pageNo, int pageSize) throws DaoException {
        StringBuilder hqlConditionSb = new StringBuilder();
        String[] paramStrs = params.split(";");
        for (int i = 0; i < paramStrs.length; i++) {
            hqlConditionSb.append(buildProductName(paramStrs[i]));
            hqlConditionSb.append(buildSaleTimeRang(paramStrs[i]));
            hqlConditionSb.append(buildStyle(paramStrs[i]));
            if (i != paramStrs.length - 1 && !StringUtils.isEmpty(hqlConditionSb.toString())) {
                hqlConditionSb.append(HQL_AND_CONTACT);
            }
        }
        String hqlCondition = hqlConditionSb.toString().endsWith(HQL_AND_CONTACT) ? hqlConditionSb.toString().substring(0, hqlConditionSb.toString().length() - HQL_AND_CONTACT.length()) : hqlConditionSb.toString();
        return productDao.findPageListByDynamicCondition(Product.class, pageNo, pageSize, hqlCondition);
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
            return new StringBuilder("(saleBeginTime >= ").append(timeRang[0]).append(" and ").append("saleEndTime <= ").append(timeRang[1]).append(")").toString();
        }
        return "";
    }

    private String buildStyle(String paramStr) {
        // saleTimeRrang:int-int;style:全部;name:羽绒服
        String[] param = paramStr.split(":");
        if (param[0].equals("style") && !StringUtils.isEmpty(param[1]) && !param[1].equals("全部")) {
            if (param[1].equals("多款"))
                return new StringBuilder("productStyle != null").toString();
            if (param[1].equals("单款"))
                return new StringBuilder("productStyle = null").toString();
        }
        return "";
    }
}