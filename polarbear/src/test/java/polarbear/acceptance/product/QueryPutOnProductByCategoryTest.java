package polarbear.acceptance.product;

import static com.polarbear.util.Constants.ResultState.SUCCESS;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;
import static polarbear.acceptance.Request.anRequest;
import static polarbear.test.util.Constants.PRODUCT_CATEGORY_1_ID;
import static polarbear.test.util.Constants.PUT_ON_PRODUCT_CATEGORY_QUERY_URL;
import static polarbear.test.util.JsonResultConvertUtil.resultBodyArray;
import static polarbear.test.util.JsonResultConvertUtil.resultState;
import static polarbear.testdata.acceptance.testdata.CategoryAcceptanceTestDataFactory.*;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import polarbear.acceptance.Request.ResultCallback;

import com.alibaba.fastjson.TypeReference;
import com.polarbear.domain.product.Product;
import com.polarbear.util.JsonResult;

public class QueryPutOnProductByCategoryTest {
    
    @Test
    public void shouldReturnProductListWhenInputCategoryIdAndPageNo() {
        anRequest(PUT_ON_PRODUCT_CATEGORY_QUERY_URL)
            .addParams("categoryId", String.valueOf(createCategory1().getId()))
            .addParams("pageNo", "1")
            .post(new ResultCallback() {
            public void onSuccess(JsonResult jsonResult) throws UnsupportedEncodingException, ClassNotFoundException {
                assertThat(resultState(jsonResult), is(SUCCESS));
                List<Product> productList = resultBodyArray(jsonResult, new TypeReference<ArrayList<Product>>(){});
                assertThat(productList.size(), equalTo(0));
            }
        });
        anRequest(PUT_ON_PRODUCT_CATEGORY_QUERY_URL)
        .addParams("categoryId", String.valueOf(createCategory3().getId()))
        .addParams("pageNo", "1")
        .post(new ResultCallback() {
        public void onSuccess(JsonResult jsonResult) throws UnsupportedEncodingException, ClassNotFoundException {
            assertThat(resultState(jsonResult), is(SUCCESS));
            List<Product> productList = resultBodyArray(jsonResult, new TypeReference<ArrayList<Product>>(){});
            assertThat(productList.size(), equalTo(5));
        }
    });
    }
}
