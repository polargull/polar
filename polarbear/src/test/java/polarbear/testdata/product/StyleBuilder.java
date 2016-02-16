package polarbear.testdata.product;

import com.polarbear.domain.ProductStyle;
import com.polarbear.service.product.util.StylePropertyTransferUtil;

public class StyleBuilder {
    private Long id;
    private String property;

    public static StyleBuilder anStyle() {
        return new StyleBuilder();
    }

    public StyleBuilder withId(Long id) {
        this.id = id;
        return this;
    }

    public StyleBuilder withProperty(String property) {
        this.property = property;
        return this;
    }

    public ProductStyle build() {
        ProductStyle style = new ProductStyle();
        if (id != null)
            style.setId(id);
        style.setStyleProperties(StylePropertyTransferUtil.propertyStrToJson(property));
        return style;
    }
}
