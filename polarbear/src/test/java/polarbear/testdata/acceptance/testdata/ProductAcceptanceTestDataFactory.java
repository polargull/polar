package polarbear.testdata.acceptance.testdata;
import static polarbear.test.util.Constants.*;
import static polarbear.testdata.builder.product.ProductBuilder.anProduct;
import static polarbear.testdata.acceptance.testdata.CategoryAcceptanceTestDataFactory.*;
import static polarbear.testdata.acceptance.testdata.ProductStyleAcceptanceTestDataFactory.*;
import com.polarbear.domain.product.Product;

public class ProductAcceptanceTestDataFactory {
    public static Product createProduct1() {
        return anProduct()
                .withName(PRODUCT_NAME + 1)
                .withID(1)
                .withNum(PRODUCT_NUM)
                .withDesc(PRODUCT_DESC + 1)
                .withImg(PRODUCT_IMG)
                .withPrice(PRODUCT_1_PRICE)
                .putOn()
                .withCategory(createCategory3())
                .build();
    }
    
    public static Product createProduct2() {
        return anProduct()
                .withName(PRODUCT_NAME + 2)
                .withID(2)
                .withNum(PRODUCT_NUM)
                .withDesc(PRODUCT_DESC + 2)
                .withImg(PRODUCT_IMG)
                .withPrice(PRODUCT_1_PRICE + 1)
                .putOn()
                .withCategory(createCategory3())
                .build();
    }
    
    public static Product createSalePrice6Product3() {
        return anProduct()
                .withName(PRODUCT_NAME + 3)
                .withID(3)
                .withNum(PRODUCT_NUM)
                .withDesc(PRODUCT_DESC)
                .withImg(PRODUCT_IMG)
                .withPrice(PRODUCT_1_PRICE)
                .putOn()
                .saleDay(1)
                .salePrice(6d)
                .withCategory(createCategory3())
                .build();
    }
    
    public static Product createMutiplyStyle1Product4() {
        Product p = anProduct()
                .withName(PRODUCT_NAME + 4)
                .withID(4)
                .withNum(PRODUCT_NUM)
                .withDesc(PRODUCT_DESC)
                .withImg(PRODUCT_IMG)
                .withPrice(PRODUCT_1_PRICE)
                .putOn()
                .withCategory(createCategory3())
                .withExtProperty(PRODUCT_STYLE1_PROPERTY1)
                .build();
        p.setProductStyle(createProductStyle1());
        return p;
    }
    
    public static Product createMutiplyStyle1Product5() {
        Product p = anProduct()
                .withID(5)
                .withName(PRODUCT_NAME + 5)
                .withNum(PRODUCT_NUM)
                .withDesc(PRODUCT_DESC)
                .withImg(PRODUCT_IMG)
                .withPrice(PRODUCT_1_PRICE)
                .putOn()
                .withExtProperty(PRODUCT_STYLE1_PROPERTY2)
                .withCategory(createCategory3())
                .build();
        p.setProductStyle(createProductStyle1());
        return p;
    }
    
    public static Product createPullOffProduct6() {
        Product p = anProduct()
                .withID(6)
                .withName(PRODUCT_NAME + 6)
                .withNum(PRODUCT_NUM)
                .withDesc(PRODUCT_DESC)
                .withImg(PRODUCT_IMG)
                .withPrice(PRODUCT_1_PRICE)
                .pullOff()
                .withCategory(createCategory3())
                .build();        
        return p;
    }
    
}