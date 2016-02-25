package com.polarbear.service.order;

import static com.polarbear.util.ProductBuyNumCalc.calcProductTotalBuyNum;
import static com.polarbear.util.money.PriceCalc.calcLogisticPrice;
import static com.polarbear.util.money.PriceCalc.calcProductTotalPrice;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.polarbear.ValidateException;
import com.polarbear.dao.BaseDao;
import com.polarbear.dao.DaoException;
import com.polarbear.domain.Address;
import com.polarbear.domain.Order;
import com.polarbear.domain.OrderList;
import com.polarbear.domain.OrderListLog;
import com.polarbear.domain.OrderLog;
import com.polarbear.domain.product.Product;
import com.polarbear.service.balance.to.BuyProduct;
import com.polarbear.service.order.bean.OrderParam;
import com.polarbear.service.product.query.ProductPicker;
import com.polarbear.service.shopcart.ModifyShopcartService;
import com.polarbear.util.DateUtil;
import com.polarbear.util.Constants.BUY_MODE;
import com.polarbear.util.Constants.ORDER_STATE;

@Service
public class OrderService {
    @Autowired(required = false)
    BaseDao<Order> orderDao;
    @Autowired(required = false)
    BaseDao<OrderLog> orderLogDao;
    @Autowired(required = false)
    BaseDao<OrderList> orderListDao;
    @Autowired(required = false)
    BaseDao<OrderListLog> orderListLogDao;
    @Autowired(required = false)
    ProductPicker productPicker;
    @Autowired(required = false)
    BaseDao<Address> addressDao;
    @Autowired(required = false)
    BaseDao<Product> productDao;
    @Autowired(required = false)
    ModifyShopcartService modifyShopcartService;

    @Transactional
    public Order createOrder(OrderParam orderParam) throws DaoException, ValidateException {
        List<BuyProduct> buyProductLst = validateBuyProducts(orderParam);
        Order order = createOrderData(buyProductLst, orderParam);
        decreaseProductNum(buyProductLst);
        clearShopcartProduct(buyProductLst, orderParam);
        return order;
    }

    private void clearShopcartProduct(List<BuyProduct> buyProducts, OrderParam orderParam) throws DaoException, ValidateException {
        if (orderParam.getBuyMode().equals(BUY_MODE.IMMEDIDATE.value())) {
            return;
        }
        for (BuyProduct buyProduct : buyProducts) {
            modifyShopcartService.removeProductFromShopCart(buyProduct.getPid());
        }
    }

    private void decreaseProductNum(List<BuyProduct> buyProducts) throws DaoException {
        for (BuyProduct buyProduct : buyProducts) {
            productDao.executeUpdate("decreaseProductNum", buyProduct.getBuyNum(), buyProduct.getPid());
        }
    }

    private Order createOrderData(List<BuyProduct> buyProducts, OrderParam orderParam) throws DaoException {
        Address address = addressDao.findById(Address.class, orderParam.getAddressId());
        StringBuilder contact = new StringBuilder(address.getReceiverName()).append("|").append(address.getCellphone()).append("|").append(address.getDistrict()).append("|")
                .append(address.getAddress());
        int curTime = DateUtil.getCurrentSeconds();
        int state = ORDER_STATE.UNPAY.value();
        String op = ORDER_STATE.UNPAY.op();

        Order order = new Order(calcProductTotalBuyNum(buyProducts), calcProductTotalPrice(buyProducts), contact.toString(), calcLogisticPrice(buyProducts), state, curTime,
                curTime);
        orderDao.store(order);
        OrderLog orderLog = new OrderLog(order, op, state, curTime);
        orderLogDao.store(orderLog);
        for (BuyProduct buyProduct : buyProducts) {
            OrderList orderList = new OrderList(order, buyProduct.getPid(), buyProduct.getProductName(), buyProduct.getProductImg(), buyProduct.getBuyNum(), buyProduct
                    .getProductRealPrice(), curTime, curTime, state);
            orderListDao.store(orderList);
            OrderListLog orderListLog = new OrderListLog(orderList, op, state, curTime);
            orderListLogDao.store(orderListLog);
        }
        return order;
    }

    private List<BuyProduct> validateBuyProducts(OrderParam orderParam) throws DaoException, ValidateException {
        List<BuyProduct> buyProducts = new ArrayList<BuyProduct>();
        for (int i = 0; i < orderParam.getPids().length; i++) {
            long pid = orderParam.getPids()[i];
            int nums = orderParam.getNums()[i];
            Product product = productPicker.pickoutTheProduct(pid);
            buyProducts.add(new BuyProduct(product, nums));
        }
        return buyProducts;
    }

}