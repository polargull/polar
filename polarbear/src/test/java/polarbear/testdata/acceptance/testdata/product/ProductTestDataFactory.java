package polarbear.testdata.acceptance.testdata.product;
import static polarbear.test.util.Constants.*;
import static polarbear.testdata.builder.product.ProductBuilder.anProduct;

import com.polarbear.domain.Category;
import com.polarbear.domain.product.Product;

public class ProductTestDataFactory {
    public static Product createProduct1() {
        return anProduct()
                .withName(PRODUCT_NAME + "1")
                .withID(PRODUCT_1_ID)
                .withNum(PRODUCT_NUM)
                .withDesc(PRODUCT_DESC + "1")
                .withImg(PRODUCT_IMG)
                .withPrice(PRODUCT_1_PRICE)
                .putOn()
                .withCategory(new Category(3l, "玩具"))
                .build();
    }
    
    public static Product createProduct2() {
        return anProduct()
                .withName(PRODUCT_NAME + "2")
                .withID(PRODUCT_2_ID)
                .withNum(PRODUCT_NUM)
                .withDesc(PRODUCT_DESC + "2")
                .withImg(PRODUCT_IMG)
                .withPrice(PRODUCT_1_PRICE + 1)
                .putOn()
                .withCategory(new Category(3l, "玩具"))
                .build();
    }
}