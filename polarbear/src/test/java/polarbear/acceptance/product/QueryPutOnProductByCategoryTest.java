package polarbear.acceptance.product;

import static com.polarbear.util.Constants.ResultState.SUCCESS;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static polarbear.acceptance.Request.anRequest;
import static polarbear.test.util.Constants.PRODUCT_CATEGORY_1_ID;
import static polarbear.test.util.Constants.PRODUCT_CATEGORY_QUERY_URL;
import static polarbear.test.util.JsonResultConvertUtil.resultBodyArray;
import static polarbear.test.util.JsonResultConvertUtil.resultState;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import polarbear.acceptance.Request.ResultCallback;

import com.alibaba.fastjson.TypeReference;
import com.polarbear.domain.Product;
import com.polarbear.util.JsonResult;

public class QueryPutOnProductByCategoryTest {
    private static final String PRODUCT_NAME = "羽绒服";
    private static final String PRODUCT_EXTPROPERTY1 = "颜色:红色,大小:M";
    private static final String PRODUCT_EXTPROPERTY2 = "颜色:黄色,大小:L";
    
    @Test
    public void shouldReturnProductListWhenInputCategoryIdAndPageNo() {        
        anRequest(PRODUCT_CATEGORY_QUERY_URL)
            .addParams("categoryId", String.valueOf(PRODUCT_CATEGORY_1_ID))
            .addParams("pageNo", String.valueOf("1"))
            .post(new ResultCallback() {
            public void onSuccess(JsonResult jsonResult) throws UnsupportedEncodingException, ClassNotFoundException {
                assertThat(resultState(jsonResult), is(SUCCESS));
                List<Product> productList = resultBodyArray(jsonResult, new TypeReference<ArrayList<Product>>(){});
                assertThat(productList.size(), equalTo(2));
                assertThat(productList.get(0).getName(), equalTo(PRODUCT_NAME));
                assertThat(productList.get(1).getName(), equalTo(PRODUCT_NAME));
                assertThat(productList.get(0).getExtProperty(), equalTo(PRODUCT_EXTPROPERTY1));
                assertThat(productList.get(1).getExtProperty(), equalTo(PRODUCT_EXTPROPERTY2));
            }
        });
    }
}
