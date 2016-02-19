package polarbear.testdata.acceptance.testdata;

import static polarbear.testdata.builder.product.StyleBuilder.anStyle;

import com.polarbear.domain.ProductStyle;

public class ProductStyleAcceptanceTestDataFactory {
    public static ProductStyle createProductStyle1() {
        return anStyle()
                .withId(1l)
                .withProperty("[{\"name\":\"颜色\",\"values\":[\"红色\",\"黄色\"]},{\"name\":\"大小\",\"values\":[\"M\",\"L\",\"XL\"]}]")
                .build();
                
    }
}