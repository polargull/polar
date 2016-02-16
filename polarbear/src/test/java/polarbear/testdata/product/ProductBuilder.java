package polarbear.testdata.product;

import static com.polarbear.util.Constants.PRODUCT_STATE.*;

import org.joda.time.DateTime;

import com.polarbear.domain.Category;
import com.polarbear.domain.Product;
import com.polarbear.domain.ProductStyle;
import com.polarbear.util.DateUtil;

public class ProductBuilder {
    private StyleBuilder styleBuilder;
    private Category category;
    private Double price;
    private String extProperty;
    private Long id;
    private Integer state;
    private String name;
    private String img;
    private String desc;
    private String tag;
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
        this.extProperty = extProperty;
        return this;
    }

    public ProductBuilder withID(long id) {
        this.id = id;
        return this;
    }

    public ProductBuilder withName(String name) {
        this.name = name;
        return this;
    }    
    
    public ProductBuilder putOn() {
        this.state = PUT_ON.value();
        return this;
    }

    public ProductBuilder pullOff() {
        this.state = PULL_OFF.value();
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

    public ProductBuilder withCategory(Category category) {
        this.category = category;
        return this;
    }

    private ProductBuilder withSaleBeginTime(int saleBeginTime) {
        this.saleBeginTime = saleBeginTime;
        return this;
    }

    private ProductBuilder withSaleEndTime(int saleEndTime) {
        this.saleEndTime = saleEndTime;
        return this;
    }

    public ProductBuilder isSaleExpire() {
        return withSaleBeginTime((int) (new DateTime().plusMinutes(-1).getMillis() / 1000L)).withSaleEndTime((int) (new DateTime().plusDays(1).getMillis() / 1000L));
    }

    public ProductBuilder isSale() {
        return withSaleBeginTime((int) (new DateTime().plusDays(-1).getMillis() / 1000L)).withSaleEndTime((int) (new DateTime().plusMinutes(-1).getMillis() / 1000L));
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
        p.setCategory(category);
        p.setExtProperty(extProperty);
        p.setName(name);
        p.setNum(num == null ? 1 : num);
        p.setCreateTime(DateUtil.getCurrentSeconds());
        p.setImage(img);
        p.setPrice(price);
        p.setState(state);
        p.setDesc(desc);
        p.setTag(tag);
        p.setSalePrice(salePrice);
        p.setSaleBeginTime(saleBeginTime);
        p.setSaleEndTime(saleEndTime);
        return p;
    }
}
