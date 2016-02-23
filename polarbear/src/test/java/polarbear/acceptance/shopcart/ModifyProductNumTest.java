package polarbear.acceptance.shopcart;

import static com.polarbear.util.Constants.ResultState.SUCCESS;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;
import static polarbear.acceptance.Request.anRequest;
import static polarbear.acceptance.shopcart.TestMyShopcartUtil.assertThatMyShopcart;
import static polarbear.test.util.Constants.SHOPCART_MODIFY_PRODUCT_NUM_URL;
import static polarbear.test.util.JsonResultConvertUtil.*;

import java.io.UnsupportedEncodingException;
import java.util.List;

import polarbear.acceptance.Request.ResultCallback;
import polarbear.acceptance.shopcart.bean.MyShopcart;
import polarbear.acceptance.shopcart.bean.ShopcartProduct;

import com.polarbear.util.JsonResult;
import com.polarbear.web.login.front.LoginController;
import static polarbear.acceptance.shopcart.TestMyShopcartUtil.*;
public class ModifyProductNumTest {

    public List<ShopcartProduct> shouldReturnMyShopcartDataWhenModifyProductNumAndUserLogined(final List<ShopcartProduct> shopcartDataList,
            ShopcartProduct... pickBuyProduct) {
        for (final ShopcartProduct sp:pickBuyProduct) {
            anRequest(SHOPCART_MODIFY_PRODUCT_NUM_URL)
                .withCookie(LoginController.USER_LOGIN_COOKIE, "MToxNDUxOTgyNjQzNTQ0OjM1ZWJhMDVjMjY5NTMxNjc5OWM1YmYwM2Q0YTE5N2M3")
                .addParams("pid", String.valueOf(sp.getPid()))
                .addParams("num", String.valueOf(sp.getNum()))
                .post(new ResultCallback() {
                    public void onSuccess(JsonResult jsonResult) throws UnsupportedEncodingException {
                        assertThat(resultState(jsonResult), is(SUCCESS));
                        MyShopcart shopcart = resultBody(jsonResult, MyShopcart.class);
                        updateShopcartDataList(shopcartDataList, sp);
                        assertThatMyShopcart(shopcart, shopcartDataList);
                    }
                });
        }
        return shopcartDataList;
    }
    
        

}