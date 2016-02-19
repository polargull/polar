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

import com.polarbear.domain.product.Product;
import com.polarbear.util.JsonResult;
import static polarbear.testdata.acceptance.testdata.ProductStyleAcceptanceTestDataFactory.*;
import static polarbear.testdata.acceptance.testdata.ProductAcceptanceTestDataFactory.*;

public class MultipleStyleProductQueryTest {

    @Test
    public void shouldReturnProductDetailDataWhenInputStyle() {
        anRequest(PRODUCT_MULTIPLE_QUERY_URL)
        .addParams("styleId", String.valueOf(createProductStyle1().getId()))
        .addParams("property", PRODUCT_STYLE1_PROPERTY1)
        .post(new ResultCallback() {
            public void onSuccess(JsonResult jsonResult) throws UnsupportedEncodingException {
                assertThat(resultState(jsonResult), is(SUCCESS));
                Product product = resultBody(jsonResult, Product.class);
                assertThat(product.getName(), equalTo(createMutiplyStyle1Product4().getName()));
                assertThat(product.getExtProperty(), equalTo(PRODUCT_STYLE1_PROPERTY1));
            }
        });

        anRequest(PRODUCT_MULTIPLE_QUERY_URL)
        .addParams("styleId", String.valueOf(createProductStyle1().getId()))
        .addParams("property", PRODUCT_STYLE1_PROPERTY2)
        .post(new ResultCallback() {
            public void onSuccess(JsonResult jsonResult) throws UnsupportedEncodingException {
                assertThat(resultState(jsonResult), is(SUCCESS));
                Product product = resultBody(jsonResult, Product.class);
                assertThat(product.getName(), equalTo(createMutiplyStyle1Product5().getName()));
                assertThat(product.getExtProperty(), equalTo(PRODUCT_STYLE1_PROPERTY2));
            }
        });
    }

    @Test
    public void shouldReturnProductNotExistFailTipWhenInputNotExistStyle() {
        anRequest(PRODUCT_MULTIPLE_QUERY_URL)
            .addParams("styleId", String.valueOf(createProductStyle1().getId()))
            .addParams("property", "颜色:白色,大小:XX")
            .post(new ResultCallback() {
            public void onSuccess(JsonResult jsonResult) throws UnsupportedEncodingException {
                assertThat(resultState(jsonResult), is(PRODUCT_NOT_EXIST));
            }
        });
    }
    
    @Test
    public void shouldReturnParamErrTipWhenNoInputStyleIdOrPropertyParam() {
        anRequest(PRODUCT_MULTIPLE_QUERY_URL)
            .addParams("styleId", String.valueOf(PRODUCT_STYLE_ID))
            .post(new ResultCallback() {
            public void onSuccess(JsonResult jsonResult) throws UnsupportedEncodingException {
                assertThat(resultState(jsonResult), is(PARAM_ERR));
            }
        });
        anRequest(PRODUCT_MULTIPLE_QUERY_URL)
        .addParams("property", "颜色:白色,大小:XX")
        .post(new ResultCallback() {
        public void onSuccess(JsonResult jsonResult) throws UnsupportedEncodingException {
            assertThat(resultState(jsonResult), is(PARAM_ERR));
        }
    });
    }
}