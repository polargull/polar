package polarbear.unit.service.shopcart;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static polarbear.test.util.Constants.PRODUCT_BUY_NUM;
import static polarbear.test.util.Constants.PRODUCT_ID;
import static polarbear.test.util.Constants.PRODUCT_PRICE;
import static polarbear.test.util.Constants.SHOPCART_ORIGIN_PRICE;
import static polarbear.testdata.product.ProductBuilder.anProduct;
import static polarbear.testdata.shopcart.ShopcartBuilder.anShopcart;
import static polarbear.testdata.shopcart.ShopcartDetailBuilder.anShopcartDetail;

import org.jmock.Expectations;
import org.junit.Before;
import org.junit.Test;

import polarbear.unit.service.AbstractMock;

import com.polarbear.ValidateException;
import com.polarbear.dao.DaoException;
import com.polarbear.domain.Shopcart;
import com.polarbear.domain.ShopcartDetail;
import com.polarbear.service.product.query.ProductPicker;
import com.polarbear.service.shopcart.ModifyShopcartService;

public class AddProductToShopcartServiceTest extends AbstractMock {
    ModifyShopcartService modifyShopcartService = new ModifyShopcartService();
    public ProductPicker productPicker;

    @Before
    public void setUp() {
        setServiceAndDependentComponent(modifyShopcartService, "shopcartDao", "shopcartDetailDao", "productPicker");
    }

    @Test
    @SuppressWarnings("unchecked")
    public void shouldReturnCorrectShopcartProductNumWhenAddProductToShopcart() throws DaoException, ValidateException {
        context.checking(new Expectations() {
            {
                allowing(productPicker).pickoutTheProduct(PRODUCT_ID);
                will(returnValue(anProduct().withPrice(PRODUCT_PRICE).build()));
            }
        });
        context.checking(new Expectations() {
            {
                allowing(shopcartDao).findByNamedQueryObject(with(any(String.class)), with(any(Object.class)));
                will(returnValue(anShopcart().withPrice(SHOPCART_ORIGIN_PRICE).withProductNum(PRODUCT_BUY_NUM).build()));
            }
        });
        context.checking(new Expectations() {
            {
                allowing(shopcartDao).store(with(any(Shopcart.class)));
            }
        });
        context.checking(new Expectations() {
            {
                allowing(shopcartDetailDao).findByNamedQueryObject(with(any(String.class)), with(any(Object.class)));
                will(returnValue(anShopcartDetail().build()));
            }
        });
        context.checking(new Expectations() {
            {
                allowing(shopcartDetailDao).store(with(any(ShopcartDetail.class)));
            }
        });
        
        assertThat(modifyShopcartService.addShopcart(PRODUCT_ID), is(PRODUCT_BUY_NUM + 1));
    }
}