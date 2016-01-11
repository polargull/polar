package polarbear.unit.service.product;

import org.junit.Before;
import org.junit.Test;

import polarbear.unit.service.AbstractMock;

import com.polarbear.service.product.query.ProductPicker;

public class ProductPickerTest extends AbstractMock {
    ProductPicker productPicker = new ProductPicker();

    @Before
    public void setUp() {
        setServiceAndDependentComponent(productPicker, "productDao");
    }
    
    @Test
    public void shouldPickSuccessWhenPickOutHadPutOnAndNumIsNot0Product() {
    }
}
