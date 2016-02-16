package polarbear.testdata.product;

import static com.polarbear.util.Constants.PRODUCT_STATE.PULL_OFF;
import static com.polarbear.util.Constants.PRODUCT_STATE.PUT_ON;

import org.joda.time.DateTime;

import com.polarbear.domain.Category;
import com.polarbear.domain.ProductStyle;
import com.polarbear.domain.product.Product;
import com.polarbear.domain.product.Sale;
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
    private Sale sale;
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

    public ProductBuilder withCategory(Category category) {
        this.category = category;
        return this;
    }

    public ProductBuilder isSaleExpire() {
        this.sale = new Sale((int) (new DateTime().plusDays(-1).getMillis() / 1000L), (int) (new DateTime().plusMinutes(-1).getMillis() / 1000L));        
        return this;
    }

    public ProductBuilder sale(int saleDay) {
        this.sale = new Sale((int) (new DateTime().plusMinutes(-1).getMillis() / 1000L), (int) (new DateTime().plusDays(saleDay).getMillis() / 1000L));
        return this;
    }
    
    public ProductBuilder withSalePrice(double salePrice) {
        this.sale.setSalePrice(salePrice);
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
        p.setSale(sale);
        return p;
    }
}
