package polarbear.testdata.builder.order;

import com.polarbear.domain.Logistic;
import com.polarbear.domain.Order;
import com.polarbear.domain.OrderList;
import com.polarbear.service.balance.to.BuyProduct;
import com.polarbear.util.Constants.ORDER_STATE;
import com.polarbear.util.date.DateUtil;

public class OrderListBuilder {
    Long id;
    Order order;
    Long productId;
    String productName;
    String productImg;
    Integer productNums;
    Double productPrice;
    Integer state;
    Logistic logistic;

    public static OrderListBuilder anOrderList() {
        return new OrderListBuilder();
    }

    public OrderListBuilder withId(long id) {
        this.id = id;
        return this;
    }

    public OrderListBuilder withState(ORDER_STATE state) {
        this.state = state.value();
        return this;
    }

    public OrderListBuilder withOrder(Order order) {
        this.order = order;
        return this;
    }
    
    public OrderListBuilder withBuyProducts(BuyProduct buyProduct) {
        productId = buyProduct.getPid();
        productName = buyProduct.getProductName();
        productImg = buyProduct.getProductImg();
        productNums = buyProduct.getBuyNum();
        productPrice = buyProduct.getProductRealPrice();
        return this;
    }

    public OrderListBuilder withLogistic(Logistic logistic) {
        this.logistic = logistic;
        return this;
    }

    public OrderList build() {
        int curTime = DateUtil.getCurrentSeconds();
        return new OrderList(id, order, productId, productName, productImg, productNums, productPrice, curTime, curTime, state);
    }
}