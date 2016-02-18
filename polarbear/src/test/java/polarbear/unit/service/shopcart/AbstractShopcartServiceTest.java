package polarbear.unit.service.shopcart;

import static polarbear.test.util.Constants.*;
import static polarbear.testdata.builder.product.ProductBuilder.anProduct;
import static polarbear.testdata.builder.shopcart.ShopcartBuilder.anShopcart;
import static polarbear.testdata.builder.shopcart.ShopcartDetailBuilder.anShopcartDetail;

import org.jmock.Expectations;

import polarbear.unit.service.AbstractMock;

import com.polarbear.ValidateException;
import com.polarbear.dao.DaoException;
import com.polarbear.domain.Shopcart;
import com.polarbear.domain.ShopcartDetail;
import com.polarbear.domain.product.Product;
import com.polarbear.service.product.query.ProductPicker;
import com.polarbear.service.shopcart.ModifyShopcartService;
import com.polarbear.service.shopcart.RemoveShopcartProductComponent;

public class AbstractShopcartServiceTest extends AbstractMock {
    ModifyShopcartService modifyShopcartService = new ModifyShopcartService();
    public ProductPicker productPicker;
    public RemoveShopcartProductComponent removeShopcartProductComponent; 
    
    public void setUp() {
        setServiceAndDependentComponent(modifyShopcartService, "shopcartDao", "shopcartDetailDao", "productPicker", "productDao", "removeShopcartProductComponent");
    }

    protected void expectFindProductOp() throws DaoException, ValidateException {
        context.checking(new Expectations() {
            {
                allowing(productDao).findById(Product.class, PRODUCT_1_ID);
                will(returnValue(anProduct().withID(PRODUCT_1_ID).withPrice(PRODUCT_1_PRICE).build()));
            }
        });
    }
    
    protected void expectRemoveShopcartProductComponentOp() throws DaoException, ValidateException {
        context.checking(new Expectations() {
            {
                allowing(removeShopcartProductComponent).removeProductFromShopcart(with(any(Product.class)));
                will(returnValue(1));
            }
        });
    }

    protected void expectProductPickerOp() throws DaoException, ValidateException {
        context.checking(new Expectations() {
            {
                allowing(productPicker).pickoutTheProduct(PRODUCT_1_ID);
                will(returnValue(anProduct().withPrice(PRODUCT_1_PRICE).build()));
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
                will(returnValue(null));
            }
        });
        context.checking(new Expectations() {
            {
                allowing(shopcartDetailDao).store(with(any(ShopcartDetail.class)));
            }
        });
    }
}