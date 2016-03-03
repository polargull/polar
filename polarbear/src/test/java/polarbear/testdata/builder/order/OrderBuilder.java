package polarbear.testdata.builder.order;

import static polarbear.testdata.acceptance.testdata.UserAcceptanceTestDataFactory.createUser1;

import java.math.BigDecimal;

import com.polarbear.domain.Address;
import com.polarbear.domain.Logistic;
import com.polarbear.domain.Order;
import com.polarbear.domain.User;
import com.polarbear.service.balance.to.BuyProduct;
import com.polarbear.util.Constants.ORDER_STATE;
import com.polarbear.util.date.DateUtil;

public class OrderBuilder {
    Long id;
    User buyer;
    int productTotalNums;
    double productTotalPrice;
    String contact;
    int state;
    double logistic_price;

    public static OrderBuilder anOrder() {
        return new OrderBuilder();
    }

    public OrderBuilder withId(Long id) {
        this.id = id;
        return this;
    }
    
    public OrderBuilder withBuyer(User u) {
        this.buyer = u;
        return this;
    }

    public OrderBuilder withState(ORDER_STATE state) {
        this.state = state.value();
        return this;
    }

    public OrderBuilder withContact(Address address) {
        this.contact = address.getReceiverName() + "|" + address.getCellphone() + "|" + address.getPhone() + "|" + address.getDistrict() + "|" + address.getAddress();
        return this;
    }

    public OrderBuilder withBuyProducts(BuyProduct[] buyProducts) {
        for (BuyProduct buyProduct : buyProducts) {
            productTotalNums += buyProduct.getBuyNum();
            double productPrice = new BigDecimal(buyProduct.getProductRealPrice()).multiply(new BigDecimal(buyProduct.getBuyNum())).setScale(2, BigDecimal.ROUND_HALF_UP)
                    .doubleValue();
            productTotalPrice = new BigDecimal(productTotalPrice).add(new BigDecimal(productPrice)).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
        }
        return this;
    }

    public Order build() {
        this.logistic_price = productTotalPrice >= 49 ? 0 : 10;
        int curTime = DateUtil.getCurrentSeconds();
        return new Order(id, buyer, productTotalNums, productTotalPrice, contact, logistic_price, state, curTime, curTime);
    }
}
