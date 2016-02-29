package com.polarbear.service.product;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.polarbear.dao.BaseDao;
import com.polarbear.dao.DaoException;
import com.polarbear.domain.Order;
import com.polarbear.domain.OrderList;
import com.polarbear.domain.product.Product;
import com.polarbear.service.balance.to.BuyProduct;

@Component
public class ModifyProductComponent {
    @Autowired(required = false)
    BaseDao<Product> productDao;
    @Autowired(required = false)
    BaseDao<OrderList> orderListDao;

    public final static int INCREASE = 1;
    public final static int DECREASE = -1;

    public void modifyProductNum(List<BuyProduct> buyProducts, int increaseOrDecrease) throws DaoException {
        for (BuyProduct buyProduct : buyProducts) {
            productDao.executeUpdate("modifyProductNum", buyProduct.getBuyNum() * increaseOrDecrease, buyProduct.getPid());
        }
    }
    
    public void modifyProductNum(Order order, int increaseOrDecrease) throws DaoException {
        List<OrderList> orderLists = orderListDao.findByNamedQuery("queryListByOrderId", order);
        for (OrderList orderList:orderLists) {
            productDao.executeUpdate("modifyProductNum", orderList.getProductNums() * increaseOrDecrease, orderList.getProductId());
        }
    }
}