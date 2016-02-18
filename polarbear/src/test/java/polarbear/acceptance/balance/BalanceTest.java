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
import polarbear.testdata.acceptance.testdata.product.ProductTestDataFactory;

import com.polarbear.domain.product.Product;
import com.polarbear.util.JsonResult;
import com.polarbear.web.login.front.LoginController;

public class BalanceTest {
    @Test
    public void shouldReturnCorrectBalanceDataWhenInputPidsAndNums() {
        final Product p1 = ProductTestDataFactory.createProduct1();
        final Product p2 = ProductTestDataFactory.createProduct2();
        final Product salePriceIs6P3 = ProductTestDataFactory.createSalePrice6Product3();

        String pids = p1.getId() + "," + p2.getId() + "," + salePriceIs6P3.getId();
        String nums = "1,1";
        anRequest(BALANCE_URL)
                .withCookie(LoginController.USER_LOGIN_COOKIE, "MToxNDUxOTgyNjQzNTQ0OjM1ZWJhMDVjMjY5NTMxNjc5OWM1YmYwM2Q0YTE5N2M3")
                .addParams("pids", pids)
                .addParams("nums", nums)
                .post(new ResultCallback() {
                    public void onSuccess(JsonResult jsonResult) throws UnsupportedEncodingException, ClassNotFoundException {
                        assertThat(resultState(jsonResult), is(SUCCESS));
                        BalanceDataTO balanceData = resultBody(jsonResult, BalanceDataTO.class);
                        assertThat(balanceData.getAddress(), nullValue());
                        assertThat(balanceData.getLogisticPrice(), equalTo(0d));
                        assertThat(balanceData.getProductDistrict().getTotalNum(), equalTo(2));
                        assertThat(balanceData.getProductDistrict().getTotalProductPrice(), equalTo(p1.getRealPrice() * 1 + p2.getRealPrice() * 1));
                        assertThat(balanceData.getProductDistrict().getProductsImg()[0], equalTo(p1.getImage().split(";")[0]));
                    }
                });
    }
}
