package polarbear.testdata.product;


import com.polarbear.domain.Product;
import com.polarbear.domain.ProductStyle;
import com.polarbear.service.product.util.StylePropertyTransferUtil;
import com.polarbear.util.DateUtil;

public class ProductBuilder {
    private StyleBuilder styleBuilder;
    private Double       price = 1d;
    private String       extProperty;
    private Long         id;
    private Double salePrice;
    private Integer saleBeginTime;
    private Integer saleEndTime;
    private Integer num;

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

    public ProductBuilder withSalePrice(double price) {
        this.salePrice = price;
        return this;
    }
    
    public ProductBuilder withSaleBeginTime(int saleBeginTime) {
        this.saleBeginTime = saleBeginTime;
        return this;
    }
    
    public ProductBuilder withSaleEndTime(int saleEndTime) {
        this.saleEndTime = saleEndTime;
        return this;
    }
    
    public ProductBuilder withNum(int num) {
        this.num = num;
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
        p.setNum(num == null ? 1 : num);
        p.setCreateTime(DateUtil.getCurrentSeconds());
        p.setImage("");
        p.setPrice(price);
        p.setState(1);
        p.setDesc(null);
        p.setTag("衣服");
        p.setSalePrice(salePrice);
        p.setSaleBeginTime(saleBeginTime);
        p.setSaleEndTime(saleEndTime);
        return p;
    }
}
