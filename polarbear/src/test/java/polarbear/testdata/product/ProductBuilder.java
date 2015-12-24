package polarbear.testdata.product;

import polarbear.test.util.ID;

import com.polarbear.domain.Product;
import com.polarbear.domain.ProductStyle;

public class ProductBuilder {
    private StyleBuilder styleBuilder = new StyleBuilder();
    private long id = ID.generate();

    public static ProductBuilder anProduct() {
        return new ProductBuilder();
    }

    public ProductBuilder withStyle(StyleBuilder styleBuilder) {
        this.styleBuilder = styleBuilder;
        return this;
    }

    public ProductBuilder withID(long id) {
        this.id = id;
        return this;
    }

    public Product build() {
        Product p = new Product();
        ProductStyle style = styleBuilder.build();
        p.setId(id);
        p.setName("test1");
        p.setCount(10);
        p.setProductStyle(style);
        p.setCreateTime(11111);
        p.setExtProperty(style.getStyleProperties());
        p.setImage("");
        p.setPrice(20d);
        p.setState(1);
        p.setDesc(null);
        p.setTag("衣服");
        p.setSalePrice(1d);
        p.setSaleBeginTime(11);
        p.setSaleEndTime(11);
        return p;
    }
}
