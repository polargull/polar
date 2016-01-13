package polarbear.unit.service.shopcart;

import com.polarbear.service.product.query.ProductPicker;
import com.polarbear.service.shopcart.ModifyShopcartService;

import polarbear.unit.service.AbstractMock;

public class AbstractShopcartServiceTest extends AbstractMock {
    protected ModifyShopcartService modifyShopService = new ModifyShopcartService();
    public ProductPicker productPicker;
    
    protected void setUp() {
        setServiceAndDependentComponent(modifyShopService, "shopcartDao", "shopcartLogDao", "productPicker");
    }
}