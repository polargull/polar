package polarbear.testdata.acceptance.testdata;

import static polarbear.integration.service.order.factory.OrderParamFactory.BUY_SHOPCART_PRODUCT;
import static polarbear.testdata.acceptance.testdata.ShopcartAcceptanceTestDataFactory.createShopcart2;

import com.polarbear.domain.ShopcartDetail;
import com.polarbear.util.date.DateUtil;

public class ShopcartDetailAcceptanceTestDataFactory {
    final static long ID = 1l;
    final static int CURTIME = DateUtil.getCurrentSeconds();

    public static ShopcartDetail createShopcart2Detail1() {
        return new ShopcartDetail(ID, BUY_SHOPCART_PRODUCT[0].getProduct(), BUY_SHOPCART_PRODUCT[0].getBuyNum(), createShopcart2(), CURTIME);
    }

    public static ShopcartDetail createShopcart2Detail2() {
        return new ShopcartDetail(ID + 1, BUY_SHOPCART_PRODUCT[1].getProduct(), BUY_SHOPCART_PRODUCT[1].getBuyNum(), createShopcart2(), CURTIME);
    }
}