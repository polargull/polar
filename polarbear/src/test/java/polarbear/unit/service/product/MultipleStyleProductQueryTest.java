package polarbear.unit.service.product;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static polarbear.testdata.product.NeedStyleBuilder.anNeedStyle;
import static polarbear.testdata.product.ProductBuilder.anProduct;
import static polarbear.testdata.product.StyleBuilder.anStyle;

import java.util.ArrayList;
import java.util.List;

import org.jmock.Expectations;
import org.junit.Before;
import org.junit.Test;

import polarbear.unit.service.AbstractMock;

import com.polarbear.dao.DaoException;
import com.polarbear.domain.Product;
import com.polarbear.service.product.query.MultipleStyleProductQuery;

@SuppressWarnings("unchecked")
public class MultipleStyleProductQueryTest extends AbstractMock {
    MultipleStyleProductQuery productQuery = new MultipleStyleProductQuery();

    @Before
    public void setUp() {
        setServiceAndDependentComponent(productQuery, "productDao");
    }

    @Test
    public void shouldReturnSameStyleProductByProperty() throws DaoException {
        context.checking(new Expectations() {
            {
                allowing(productDao).findByNamedQuery("querySameStyleProductByStyleId", anNeedStyle().STYLE_ID);
                List<Product> sameStyleProductList = new ArrayList<Product>();
                sameStyleProductList.add(anProduct().withExtProperty("颜色:红色").build());
                sameStyleProductList.add(anProduct().withExtProperty("颜色:黄色,大小:L").build());
                // 设定预期值
                will(returnValue(sameStyleProductList));
            }
        });

        Product needStyleProduct = productQuery.querySameStyleProductByNeedStyle(anNeedStyle().withProperty("颜色:红色").build());
        assertNotNull("有需要的红色款式", needStyleProduct);
        needStyleProduct = productQuery.querySameStyleProductByNeedStyle(anNeedStyle().withProperty("颜色:蓝色").build());
        assertNull("没有蓝色款式", needStyleProduct);
        needStyleProduct = productQuery.querySameStyleProductByNeedStyle(anNeedStyle().withProperty("颜色:黄色,大小:L").build());
        assertNotNull("有需要的黄色,大小为L 的款式", needStyleProduct);
    }
}
