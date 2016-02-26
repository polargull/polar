package polarbear.integration.service.order.factory;

import static com.polarbear.util.Constants.ORDER_STATE.UNPAY;
import static polarbear.testdata.acceptance.testdata.UserAcceptanceTestDataFactory.createUser1;

import java.math.BigDecimal;

import polarbear.testdata.builder.order.OrderParamBuilder;

import com.polarbear.domain.Address;
import com.polarbear.domain.Logistic;
import com.polarbear.domain.Order;
import com.polarbear.service.balance.to.BuyProduct;

public class OrderFactory {
    public static Order expectOrder(OrderParamBuilder paramBuilder) {
        int productTotalNums = 0;
        double productTotalPrice = 0;
        final String CONTACT = getOrderContact(paramBuilder.getAddress());
        final int STATE = UNPAY.value();
        final Logistic LOGISTIC = null;
        for (BuyProduct buyProduct : paramBuilder.getBuyProducts()) {
            productTotalNums += buyProduct.getBuyNum();
            double productPrice = new BigDecimal(buyProduct.getProductRealPrice()).multiply(new BigDecimal(buyProduct.getBuyNum())).setScale(2, BigDecimal.ROUND_HALF_UP)
                    .doubleValue();
            productTotalPrice = new BigDecimal(productTotalPrice).add(new BigDecimal(productPrice)).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
        }
        final double LOGISTIC_PRICE = productTotalPrice >= 49 ? 0 : 10;
        return new Order(createUser1(), productTotalNums, productTotalPrice, CONTACT, LOGISTIC_PRICE, LOGISTIC, STATE);
    }

    private static String getOrderContact(Address address) {
        return address.getReceiverName() + "|" + address.getCellphone() + "|" + address.getPhone() + "|" + address.getDistrict() + "|" + address.getAddress();
    }
}
