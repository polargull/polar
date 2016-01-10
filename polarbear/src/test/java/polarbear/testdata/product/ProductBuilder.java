package polarbear.testdata.product;

import polarbear.test.util.ID;

import com.polarbear.domain.Product;
import com.polarbear.domain.ProductStyle;
import com.polarbear.service.product.util.StylePropertyTransferUtil;

public class ProductBuilder {
    private StyleBuilder styleBuilder;
    private double       price = 1d;
    private String       extProperty;
    private long         id    = ID.generate();

    public static ProductBuilder anProduct() {
        return new ProductBuilder();
    }

    public ProductBuilder withStyle(StyleBuilder styleBuilder) {
        this.styleBuilder = styleBuilder;
        return this;
    }

    public ProductBuilder withExtProperty(String extProperty) {
        this.extProperty = StylePropertyTransferUtil.propertyStrToJson(extProperty);
        return this;
    }

    public ProductBuilder withID(long id) {
        this.id = id;
        return this;
    }

    public ProductBuilder withPrice(double price) {
        this.price = price;
        return this;
    }

    public Product build() {
        Product p = new Product();
        p.setId(id);
        if (styleBuilder != null) {
            ProductStyle style = styleBuilder.build();
            p.setProductStyle(style);
        }
        p.setExtProperty(extProperty);
        p.setName("test1");
        p.setNum(10);
        p.setCreateTime(11111);
        p.setImage("");
        p.setPrice(price);
        p.setState(1);
        p.setDesc(null);
        p.setTag("衣服");
        p.setSalePrice(1d);
        p.setSaleBeginTime(11);
        p.setSaleEndTime(11);
        return p;
    }
}
