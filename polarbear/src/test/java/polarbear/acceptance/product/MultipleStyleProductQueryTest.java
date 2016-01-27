package polarbear.acceptance.product;

import static com.polarbear.util.Constants.ResultState.*;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;
import static polarbear.acceptance.Request.anRequest;
import static polarbear.test.util.Constants.*;
import static polarbear.test.util.JsonResultConvertUtil.*;

import java.io.UnsupportedEncodingException;
import org.junit.Test;
import polarbear.acceptance.Request.ResultCallback;
import com.polarbear.domain.Product;
import com.polarbear.util.JsonResult;

public class MultipleStyleProductQueryTest {
    private final String PRODUCT_NAME = "羽绒服";
    private final String PRODUCT_PROPERTY1 = "颜色:黄色,大小:L";
    private final String PRODUCT_PROPERTY2 = "颜色:红色,大小:M";

    @Test
    public void shouldReturnProductDetailDataWhenInputStyle() {
        anRequest(PRODUCT_MULTIPLE_QUERY_URL).addParams("styleId", String.valueOf(PRODUCT_STYLE_ID)).addParams("property", "颜色:黄色,大小:L").post(new ResultCallback() {
            public void onSuccess(JsonResult jsonResult) throws UnsupportedEncodingException {
                assertThat(resultState(jsonResult), is(SUCCESS));
                Product product = resultBody(jsonResult, Product.class);
                assertThat(product.getName(), equalTo(PRODUCT_NAME));
                assertThat(product.getExtProperty(), equalTo(PRODUCT_PROPERTY1));
            }
        });

        anRequest(PRODUCT_MULTIPLE_QUERY_URL).addParams("styleId", String.valueOf(PRODUCT_STYLE_ID)).addParams("property", "颜色:红色,大小:M").post(new ResultCallback() {
            public void onSuccess(JsonResult jsonResult) throws UnsupportedEncodingException {
                assertThat(resultState(jsonResult), is(SUCCESS));
                Product product = resultBody(jsonResult, Product.class);
                assertThat(product.getName(), equalTo(PRODUCT_NAME));
                assertThat(product.getExtProperty(), equalTo(PRODUCT_PROPERTY2));
            }
        });
    }

    @Test
    public void shouldReturnProductNotExistFailTipWhenInputProductId() {
        anRequest(PRODUCT_MULTIPLE_QUERY_URL)
            .addParams("styleId", String.valueOf(PRODUCT_STYLE_ID))
            .addParams("property", "颜色:白色,大小:XX")
            .post(new ResultCallback() {
            public void onSuccess(JsonResult jsonResult) throws UnsupportedEncodingException {
                assertThat(resultState(jsonResult), is(PRODUCT_NOT_EXIST));
            }
        });
    }
}