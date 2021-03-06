package polarbear.acceptance.balance;

import static com.polarbear.util.Constants.ResultState.*;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;
import static polarbear.acceptance.Request.anRequest;
import static polarbear.test.util.Constants.*;
import static polarbear.test.util.JsonResultConvertUtil.*;

import java.io.UnsupportedEncodingException;

import org.junit.Test;

import polarbear.acceptance.Request.ResultCallback;
import polarbear.testdata.acceptance.testdata.ProductAcceptanceTestDataFactory;

import com.polarbear.domain.product.Product;
import com.polarbear.util.JsonResult;
import com.polarbear.web.login.front.LoginController;

public class BalanceTest {

    @Test
    public void shouldReturnCorrectBalanceDataWhenSelectNumEachIs1TwoProduct() {
        final Product p1 = ProductAcceptanceTestDataFactory.createProduct1();
        final Product p2 = ProductAcceptanceTestDataFactory.createProduct2();
        String pids = p1.getId() + "," + p2.getId();
        String nums = "1,1";
        final double LOGISTIC_PRICE_0 = 0d;
        anRequest(BALANCE_URL)
                .withCookie(LoginController.USER_LOGIN_COOKIE, "MToxNDUxOTgyNjQzNTQ0OjM1ZWJhMDVjMjY5NTMxNjc5OWM1YmYwM2Q0YTE5N2M3")
                .addParams("pids", pids)
                .addParams("nums", nums)
                .post(new ResultCallback() {
                    public void onSuccess(JsonResult jsonResult) throws UnsupportedEncodingException, ClassNotFoundException {
                        assertThat(resultState(jsonResult), is(SUCCESS));
                        BalanceDataTO balanceData = resultBody(jsonResult, BalanceDataTO.class);
                        assertThat(balanceData.getAddress(), not(nullValue()));
                        assertThat(balanceData.getLogisticPrice(), equalTo(LOGISTIC_PRICE_0));
                        assertThat(balanceData.getProductDistrict().getTotalNum(), equalTo(2));
                        assertThat(balanceData.getTotalPrice(), equalTo(p1.getRealPrice() * 1 + p2.getRealPrice() * 1 + LOGISTIC_PRICE_0));
                        assertThat(balanceData.getProductDistrict().getProductsImg()[0], equalTo(p1.getImage().split(";")[0]));
                    }
                });
    }
    
    @Test
    public void shouldReturnCorrectBalanceDataWhenSelectNumIs1PriceLessThan49Product() {
        final Product salePriceIs6P3 = ProductAcceptanceTestDataFactory.createSalePrice6Product3();
        String pids = salePriceIs6P3.getId().toString();
        String nums = "1";
        final double LOGISTIC_PRICE_10 = 10d;
        anRequest(BALANCE_URL)
                .withCookie(LoginController.USER_LOGIN_COOKIE, "MToxNDUxOTgyNjQzNTQ0OjM1ZWJhMDVjMjY5NTMxNjc5OWM1YmYwM2Q0YTE5N2M3")
                .addParams("pids", pids)
                .addParams("nums", nums)
                .post(new ResultCallback() {
                    public void onSuccess(JsonResult jsonResult) throws UnsupportedEncodingException, ClassNotFoundException {
                        assertThat(resultState(jsonResult), is(SUCCESS));
                        BalanceDataTO balanceData = resultBody(jsonResult, BalanceDataTO.class);
                        assertThat(balanceData.getAddress(), not(nullValue()));
                        assertThat(balanceData.getLogisticPrice(), equalTo(LOGISTIC_PRICE_10));
                        assertThat(balanceData.getProductDistrict().getTotalNum(), equalTo(1));
                        assertThat(balanceData.getTotalPrice(), equalTo(salePriceIs6P3.getRealPrice() * 1 + LOGISTIC_PRICE_10));
                        assertThat(balanceData.getProductDistrict().getProductsImg().length, equalTo(1));
                    }
                });
    }
    
    @Test
    public void shouldReturnNeedLoginFailJsonTipWhenNoLoginBalance() {
        anRequest(BALANCE_URL)
                .post(new ResultCallback() {
                    public void onSuccess(JsonResult jsonResult) throws UnsupportedEncodingException {
                        assertThat(resultState(jsonResult), is(NEED_LOGIN));
                    }
                });
    }
}