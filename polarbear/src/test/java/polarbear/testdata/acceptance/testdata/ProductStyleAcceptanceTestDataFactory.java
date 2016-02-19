package polarbear.testdata.acceptance.testdata;

import static polarbear.testdata.builder.product.StyleBuilder.anStyle;

import com.polarbear.domain.ProductStyle;

public class ProductStyleAcceptanceTestDataFactory {
    public static final String PRODUCT_STYLE1_PROPERTY1 = "颜色:黄色,大小:L";
    public static final String PRODUCT_STYLE1_PROPERTY2 = "颜色:红色,大小:M";
    
    public static ProductStyle createProductStyle1() {
        return anStyle()
                .withId(1l)
                .withProperty("[{\"name\":\"颜色\",\"values\":[\"红色\",\"黄色\"]},{\"name\":\"大小\",\"values\":[\"M\",\"L\",\"XL\"]}]")
                .build();
                
    }
}