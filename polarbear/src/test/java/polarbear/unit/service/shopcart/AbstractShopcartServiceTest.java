package polarbear.unit.service.shopcart;

import static polarbear.test.util.Constants.*;
import static polarbear.testdata.product.ProductBuilder.anProduct;
import static polarbear.testdata.shopcart.ShopcartBuilder.anShopcart;
import static polarbear.testdata.shopcart.ShopcartDetailBuilder.anShopcartDetail;

import org.jmock.Expectations;

import polarbear.unit.service.AbstractMock;

import com.polarbear.ValidateException;
import com.polarbear.dao.DaoException;
import com.polarbear.domain.Shopcart;
import com.polarbear.domain.ShopcartDetail;
import com.polarbear.service.product.query.ProductPicker;
import com.polarbear.service.shopcart.ModifyShopcartService;

public class AbstractShopcartServiceTest extends AbstractMock {
    ModifyShopcartService modifyShopcartService = new ModifyShopcartService();
    public ProductPicker productPicker;
    
    public void setUp() {
        setServiceAndDependentComponent(modifyShopcartService, "shopcartDao", "shopcartDetailDao", "productPicker");
    }
    
    protected void expectProductPickerOp() throws DaoException, ValidateException {
        context.checking(new Expectations() {
            {
                allowing(productPicker).pickoutTheProduct(PRODUCT_ID);
                will(returnValue(anProduct().withPrice(PRODUCT_PRICE).build()));
            }
        });
    }
    
    @SuppressWarnings("unchecked")
    protected void expectShopcartOp() throws DaoException {
        context.checking(new Expectations() {
            {
                allowing(shopcartDao).findByNamedQueryObject(with(any(String.class)), with(any(Object.class)));
                will(returnValue(anShopcart().withProductNum(SHOPCART_ORIGIN_NUM).build()));
            }
        });
        context.checking(new Expectations() {
            {
                allowing(shopcartDao).store(with(any(Shopcart.class)));
            }
        });
    }

    @SuppressWarnings("unchecked")
    protected void expectShopcartDetailOp() throws DaoException {
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
    }
}