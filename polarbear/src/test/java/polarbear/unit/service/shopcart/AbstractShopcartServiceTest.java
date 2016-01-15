package polarbear.unit.service.shopcart;

import static polarbear.test.util.Constants.PRODUCT_ID;
import static polarbear.test.util.Constants.PRODUCT_PRICE;
import static polarbear.test.util.Constants.PRODUCT_SALE_PRICE;
import static polarbear.test.util.Constants.SHOPCARD_ID;
import static polarbear.test.util.Constants.SHOPCART_DETAIL_ID;
import static polarbear.test.util.Constants.SHOPCART_ORIGIN_NUM;
import static polarbear.test.util.Constants.SHOPCART_ORIGIN_PRICE;
import static polarbear.testdata.product.ProductBuilder.anProduct;
import static polarbear.testdata.shopcart.ShopcartBuilder.anShopcart;
import static polarbear.testdata.shopcart.ShopcartDetailBuilder.anShopcartDetail;
import static polarbear.testdata.user.UserBuilder.anUser;

import org.jmock.Expectations;
import org.joda.time.DateTime;

import com.polarbear.ValidateException;
import com.polarbear.dao.DaoException;
import com.polarbear.domain.Shopcart;
import com.polarbear.domain.ShopcartDetail;
import com.polarbear.service.product.query.ProductPicker;
import com.polarbear.service.shopcart.ModifyShopcartService;

import polarbear.unit.service.AbstractMock;

public class AbstractShopcartServiceTest extends AbstractMock {
    protected ModifyShopcartService modifyShopService = new ModifyShopcartService();
    public ProductPicker productPicker;
    
    protected void setUp() {
        setServiceAndDependentComponent(modifyShopService, "shopcartDao", "shopcartDetailDao", "productPicker");
    }
    
    protected void expectProductPickerOp() throws DaoException, ValidateException {
        context.checking(new Expectations() {
            {
                oneOf(productPicker).pickoutTheProduct(PRODUCT_ID);
                will(returnValue(anProduct().withID(PRODUCT_ID).withPrice(PRODUCT_PRICE).build()));
                oneOf(productPicker).pickoutTheProduct(PRODUCT_ID);
                will(returnValue(anProduct()
                        .withID(PRODUCT_ID)
                        .withPrice(PRODUCT_PRICE)
                        .withSalePrice(PRODUCT_SALE_PRICE)
                        .withSaleBeginTime((int)(new DateTime().plusMinutes(-1).getMillis()/1000L))
                        .withSaleEndTime((int)(new DateTime().plusDays(1).getMillis()/1000L))
                        .build()));
            }
        });
    }
    
    @SuppressWarnings("unchecked")
    protected void expectShopcartDaoOp(final boolean isAlreayHaveShopcart) throws DaoException {
        context.checking(new Expectations() {
            {
                allowing(shopcartDao).findByNamedQueryObject(with(any(String.class)), with(any(Object.class)));
                will(returnValue(isAlreayHaveShopcart?anShopcart().withId(SHOPCARD_ID).withPrice(SHOPCART_ORIGIN_PRICE).withProductNum(SHOPCART_ORIGIN_NUM).withUser(anUser()).build():null));
            }
        });
        context.checking(new Expectations() {
            {
                allowing(shopcartDao).store(with(any(Shopcart.class)));
            }
        });
    }
    
    @SuppressWarnings("unchecked")
    protected void expectShopcartDetailDaoOp() throws DaoException {
        context.checking(new Expectations() {
            {
                oneOf(shopcartDetailDao).findByNamedQueryObject(with(any(String.class)), with(any(Object.class)));
                will(returnValue(null));
                oneOf(shopcartDetailDao).findByNamedQueryObject(with(any(String.class)), with(any(Object.class)));
                will(returnValue(anShopcartDetail().withId(SHOPCART_DETAIL_ID).withNum(1).withProduct(anProduct().withID(PRODUCT_ID)).withShopcart(anShopcart().withId(SHOPCARD_ID)).build()));
            }
        });
        context.checking(new Expectations() {
            {
                allowing(shopcartDetailDao).store(with(any(ShopcartDetail.class)));
            }
        });
    }
    
}