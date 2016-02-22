package polarbear.unit.service.shopcart;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static polarbear.test.util.Constants.*;
import static polarbear.testdata.builder.product.ProductBuilder.anProduct;
import static polarbear.testdata.builder.shopcart.ShopcartBuilder.anShopcart;
import static polarbear.testdata.builder.shopcart.ShopcartDetailBuilder.anShopcartDetail;

import org.jmock.Expectations;
import org.junit.Before;
import org.junit.Test;


import com.polarbear.ValidateException;
import com.polarbear.dao.DaoException;
import com.polarbear.domain.Shopcart;
import com.polarbear.domain.ShopcartDetail;
import static com.polarbear.util.Constants.ResultState.*;

public class AddProductToShopcartServiceTest extends AbstractShopcartServiceTest {

    @Before
    public void setUp() {
        super.setUp();
    }

    @Test
    public void shouldReturnCorrectShopcartProductNumWhenAddProductToShopcart() throws DaoException, ValidateException {
        expectProductPickerOp();
        expectShopcartOp();
        expectShopcartDetailOp();
        assertThat(modifyShopcartService.addShopcart(PRODUCT_1_ID, 1), is(SHOPCART_ORIGIN_NUM + 1));
    }

    @Test
    public void shouldthrowProductPullOffValidateExceptionWhenProductPullOff() throws DaoException, ValidateException {
        expectProductPickerOpThrowProductPullOffValidateException();
        expectedEx.expect(ValidateException.class);
        expectedEx.expectMessage(PRODUCT_PULL_OFF.emsg());        
        modifyShopcartService.addShopcart(PRODUCT_1_ID, 1);
    }

    @Test
    public void shouldthrowProductNumIs0ValidateExceptionWhenProductNumIs0() throws DaoException, ValidateException {
        expectProductPickerOpThrowProductNumIs0ValidateException();
        expectedEx.expect(ValidateException.class);
        expectedEx.expectMessage(PRODUCT_NUM_IS_0.emsg());
        modifyShopcartService.addShopcart(PRODUCT_1_ID, 1);
    }
    
    private void expectProductPickerOpThrowProductPullOffValidateException() throws DaoException, ValidateException {
        context.checking(new Expectations() {
            {
                allowing(productPicker).pickoutTheProduct(PRODUCT_1_ID);
                will(throwException(new ValidateException(PRODUCT_PULL_OFF)));                    
            }
        });
    }

    private void expectProductPickerOpThrowProductNumIs0ValidateException() throws DaoException, ValidateException {
        context.checking(new Expectations() {
            {
                allowing(productPicker).pickoutTheProduct(PRODUCT_1_ID);
                will(throwException(new ValidateException(PRODUCT_NUM_IS_0)));                    
            }
        });
    }
    
}