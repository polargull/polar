package polarbear.unit.service.product;

import static com.polarbear.util.Constants.ResultState.PRODUCT_NOT_EXIST;
import static org.junit.Assert.assertNotNull;
import static polarbear.test.util.Constants.PRODUCT_STYLE_ID;
import static polarbear.testdata.product.NeedStyleBuilder.anNeedStyle;
import static polarbear.testdata.product.ProductBuilder.anProduct;

import java.util.ArrayList;
import java.util.List;

import org.jmock.Expectations;
import org.junit.Before;
import org.junit.Test;

import polarbear.unit.service.AbstractMock;

import com.polarbear.NullObjectException;
import com.polarbear.dao.DaoException;
import com.polarbear.domain.product.Product;
import com.polarbear.service.product.query.MultipleStyleProductQuery;

@SuppressWarnings("unchecked")
public class MultipleStyleProductQueryTest extends AbstractMock {
    MultipleStyleProductQuery productQuery = new MultipleStyleProductQuery();

    @Before
    public void setUp() {
        setServiceAndDependentComponent(productQuery, "productDao");
    }

    @Test
    public void shouldReturnSameStyleProductWhenQuerySameStyleProduct() throws DaoException, NullObjectException {
        context.checking(new Expectations() {
            {
                allowing(productDao).findByNamedQuery("querySameStyleProductByStyleId", PRODUCT_STYLE_ID);
                List<Product> sameStyleProductList = new ArrayList<Product>();
                sameStyleProductList.add(anProduct().withExtProperty("颜色:红色").build());
                sameStyleProductList.add(anProduct().withExtProperty("颜色:黄色,大小:L").build());
                // 设定预期值
                will(returnValue(sameStyleProductList));
            }
        });

        Product needStyleProduct = productQuery.querySameStyleProductByNeedStyle(anNeedStyle().withProperty("颜色:红色").build());
        assertNotNull("有需要的红色款式", needStyleProduct);
        needStyleProduct = productQuery.querySameStyleProductByNeedStyle(anNeedStyle().withProperty("颜色:黄色,大小:L").build());
        assertNotNull("有需要的黄色,大小为L 的款式", needStyleProduct);
    }

    @Test
    public void shouldThrowNullObjectExceptionProductWhenQuerySameStyleProductNotExist() throws DaoException, NullObjectException {
        context.checking(new Expectations() {
            {
                allowing(productDao).findByNamedQuery("querySameStyleProductByStyleId", PRODUCT_STYLE_ID);
                List<Product> sameStyleProductList = new ArrayList<Product>();
                sameStyleProductList.add(anProduct().withExtProperty("颜色:红色").build());
                sameStyleProductList.add(anProduct().withExtProperty("颜色:黄色,大小:L").build());
                // 设定预期值
                will(returnValue(sameStyleProductList));
            }
        });
        expectedEx.expect(NullObjectException.class);
        expectedEx.expectMessage(PRODUCT_NOT_EXIST.emsg());
        productQuery.querySameStyleProductByNeedStyle(anNeedStyle().withProperty("颜色:蓝色").build());
    }
}