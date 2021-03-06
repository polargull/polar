package polarbear.unit.controller.shopcart;

import static com.polarbear.util.Constants.ResultState.DB_ERR;
import static com.polarbear.util.Constants.ResultState.PARAM_ERR;
import static com.polarbear.util.Constants.ResultState.SUCCESS;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.junit.Assert.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static polarbear.test.util.Constants.PRODUCT_1_ID;
import static polarbear.test.util.Constants.SHOPCART_ADD_URL;
import static polarbear.test.util.JsonResultConvertUtil.convertJsonObj;
import static polarbear.test.util.JsonResultConvertUtil.resultState;

import java.io.UnsupportedEncodingException;

import org.jmock.Expectations;
import org.junit.Before;
import org.junit.Test;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import polarbear.unit.controller.AbstractContextControllerTest;

import com.polarbear.dao.DaoException;
import com.polarbear.service.shopcart.ShopcartService;
import com.polarbear.web.shopcart.ShopcartController;

public class AddShopcartControllerTest extends AbstractContextControllerTest {
    ShopcartController shopcartController = new ShopcartController();
    public ShopcartService shopcartService;

    @Before
    public void setup() {
        setServiceAndDependentComponent(shopcartController, "shopcartService");
        super.setUp(shopcartController);
    }

    @Test
    public void shouldReturnShopcartCountInCookieWhenInputProductId() throws Exception {
        context.checking(new Expectations() {
            {
                allowing(shopcartService).addProductToShopcart(PRODUCT_1_ID);
                // 设定预期值
                will(returnValue(1));
            }
        });
        MvcResult result = mockMvc
            .perform(post(SHOPCART_ADD_URL)
                    .param("pid", String.valueOf(PRODUCT_1_ID)))
            .andExpect(status().isOk())
//            .andDo(print())
            .andReturn();
        assertThat(result.getResponse().getCookie(ShopcartController.SHOPCART_PRODUCT_NUM).getValue(),not(equalTo("")));
        assertThat(resultState(result), is(SUCCESS));
        assertThat(convertJsonObj(result).getBody().isEmpty(), is(true));
    }
    
    @Test
    public void shouldInvalidateWhenInputErrProductId() throws Exception {
        context.checking(new Expectations() {
            {
                allowing(shopcartService).addProductToShopcart(PRODUCT_1_ID);
                // 设定预期值
                will(returnValue(1));
            }
        });
        testInputErrProductId(null);
        testInputErrProductId("");
        testInputErrProductId("aaa");
    }

    private void testInputErrProductId(String pid) throws Exception, UnsupportedEncodingException {
        MockHttpServletRequestBuilder requestBuilder = post(SHOPCART_ADD_URL);
        if (pid != null)
            requestBuilder = requestBuilder.param("pid", pid);
        MvcResult result = mockMvc
            .perform(requestBuilder)
            .andExpect(status().isOk())
//            .andDo(print())
            .andReturn();
        assertThat(resultState(result), is(PARAM_ERR));
    }

    @Test
    public void shouldInvalidateWhenAddShopcartServiceDaoErr() throws Exception {
        context.checking(new Expectations() {
            {
                allowing(shopcartService).addProductToShopcart(PRODUCT_1_ID);
                // 设定预期值
                will(throwException(new DaoException(DB_ERR)));
            }
        });
        MvcResult result = mockMvc
        .perform(post(SHOPCART_ADD_URL)
                .param("pid", String.valueOf(PRODUCT_1_ID)))
        .andExpect(status().isOk())
//        .andDo(print())
        .andReturn();
        assertThat(resultState(result), is(DB_ERR));
    }
}