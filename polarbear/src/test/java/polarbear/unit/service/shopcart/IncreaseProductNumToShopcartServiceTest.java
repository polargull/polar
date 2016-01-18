package polarbear.unit.service.shopcart;

import org.junit.Before;

import polarbear.unit.service.AbstractMock;

import com.polarbear.service.shopcart.ShopcartService;

public class IncreaseProductNumToShopcartServiceTest extends AbstractMock {
    ShopcartService shopcartSvc = new ShopcartService();
    @Before
    public void setUp() {
        setServiceAndDependentComponent(shopcartSvc,"");
    }
    
    
}