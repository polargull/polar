package polarbear.testdata.product;

import com.polarbear.domain.ProductStyle;
import com.polarbear.service.product.util.StylePropertyTransferUtil;

public class StyleBuilder {
    public static final long STYLE_ID = 1;
    private String property;

    public static StyleBuilder anStyle() {
        return new StyleBuilder();
    }

    public StyleBuilder withProperty(String property) {
        this.property = property;
        return this;
    }

    public ProductStyle build() {
        ProductStyle style = new ProductStyle();
        style.setId(STYLE_ID);
        style.setStyleProperties(StylePropertyTransferUtil.propertyStrToJson(property));
        return style;
    }
}
