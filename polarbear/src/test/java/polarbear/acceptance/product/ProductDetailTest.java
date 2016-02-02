package polarbear.acceptance.product;

import static com.polarbear.util.Constants.ResultState.SUCCESS;
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

public class ProductDetailTest {

    @Test
    public void shouldReturnProductDetailDataWhenInputProductId() {
        anRequest(PRODUCT_DETAIL_URL).addParams("pid", String.valueOf(PRODUCT_1_ID)).post(new ResultCallback() {
            public void onSuccess(JsonResult jsonResult) throws UnsupportedEncodingException {
                assertThat(resultState(jsonResult), is(SUCCESS));
                Product product = resultBody(jsonResult, Product.class);
                assertThat(product.getId(), equalTo(PRODUCT_1_ID));
                assertThat(product.getPrice(), equalTo(PRODUCT_1_PRICE));
            }
        });
    }

}