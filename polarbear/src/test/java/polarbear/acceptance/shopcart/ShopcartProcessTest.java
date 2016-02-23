package polarbear.acceptance.shopcart;

import static polarbear.testdata.acceptance.testdata.ProductAcceptanceTestDataFactory.createProduct1;
import static polarbear.testdata.acceptance.testdata.ProductAcceptanceTestDataFactory.createProduct2;
import static polarbear.testdata.acceptance.testdata.ProductAcceptanceTestDataFactory.createSalePrice6Product3;

import java.util.ArrayList;
import java.util.List;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import polarbear.acceptance.shopcart.bean.ShopcartProduct;

import com.polarbear.domain.product.Product;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ShopcartProcessTest {
    Product pickProduct1 = createProduct1();
    Product pickProduct2 = createProduct2();
    Product pickProduct3 = createSalePrice6Product3();
    ShopcartProduct buyProduct1 = new ShopcartProduct(pickProduct1, 1);
    ShopcartProduct buyProduct2 = new ShopcartProduct(pickProduct2, 2);
    ShopcartProduct buyProduct3 = new ShopcartProduct(pickProduct3, 3);
    static List<ShopcartProduct> shopcartDataList = new ArrayList<ShopcartProduct>();
    
    @Test
    public void test1ShouldReturnMyShopcartEmptyDataWhenQueryShopcartAndUserFirstLogin() {
        new GetMyShopcartTest().shouldReturnMyShopcartEmptyDataWhenQueryShopcartAndUserFirstLogin();
    }
    
    @Test
    public void test2ShouldSynchValidateWhenLoginHaveCorrectSynchData() {
        shopcartDataList = new LoginSynchShopcartDataTest().shouldSynchValidateWhenLoginHaveCorrectSynchData(buyProduct1, buyProduct2);
    }

    @Test
    public void test3ShouldSuccessReturnCookieShopcartNumJsonDataWhenSelectProductAddShopcartAndUserLogined() {
        shopcartDataList = new AddShopcartTest().shouldSuccessReturnCookieShopcartNumJsonDataWhenSelectProductAddShopcartAndUserLogined(shopcartDataList, pickProduct1, pickProduct3);
    }

    @Test
    public void test4ShouldReturnMyShopcartDataWhenModifyProductNumAndUserLogined() {
        shopcartDataList = new ModifyProductNumTest().shouldReturnMyShopcartDataWhenModifyProductNumAndUserLogined(shopcartDataList, buyProduct1);
    }

    @Test
    public void test5ShouldReturnMyShopcartDataWhenQueryShopcartAndUserLogined() {
        new GetMyShopcartTest().shouldReturnMyShopcartDataWhenQueryShopcartAndUserLogined(shopcartDataList);
    }
    
    @Test
    public void test6ShouldSuccessReturnShopcartDataWhenRemoveProductInShopcartAndUserLogined() {
        shopcartDataList = new RemoveProductFromShopcartTest().shouldSuccessReturnShopcartDataWhenRemoveProductInShopcartAndUserLogined(shopcartDataList, pickProduct1, pickProduct2, pickProduct3);
    }
    
    @Test
    public void test7ShouldReturnMyShopcartDataWhenQueryShopcartAndUserLogined() {
        new GetMyShopcartTest().shouldReturnMyShopcartDataWhenQueryShopcartAndUserLogined(shopcartDataList);
    }
}
