package polarbear.testdata.acceptance.testdata;

import static polarbear.integration.service.order.factory.OrderParamFactory.BUY_SHOPCART_PRODUCT;
import static polarbear.testdata.acceptance.testdata.UserAcceptanceTestDataFactory.createUser2;

import com.polarbear.domain.Shopcart;
import com.polarbear.domain.User;
import com.polarbear.service.balance.to.BuyProduct;
import com.polarbear.util.date.DateUtil;

public class ShopcartAcceptanceTestDataFactory {
    final static long ID = 1l;
    final static User USER2 = createUser2();
    final static int BUY_PRODUCT_NUMS = getBuyProductTotalNum();
    final static int CURTIME = DateUtil.getCurrentSeconds();

    public static Shopcart createShopcart2() {
        return new Shopcart(ID, USER2, BUY_PRODUCT_NUMS, CURTIME);
    }

    private static int getBuyProductTotalNum() {
        int totalNum = 0;
        for (BuyProduct buyProduct : BUY_SHOPCART_PRODUCT) {
            totalNum += buyProduct.getBuyNum();
        }
        return totalNum;
    }
}