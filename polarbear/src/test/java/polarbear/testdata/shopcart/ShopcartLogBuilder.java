package polarbear.testdata.shopcart;

import polarbear.testdata.product.ProductBuilder;

import com.polarbear.domain.ShopcartLog;
import com.polarbear.util.DateUtil;

public class ShopcartLogBuilder {
    Long id;
    ProductBuilder productBuilder;
    Integer count;
    ShopcartBuilder shopCartBuilder;
    Integer createTime;

    public static ShopcartLogBuilder anShopcartLog() {
        return new ShopcartLogBuilder();
    }

    public ShopcartLogBuilder withId(long id) {
        this.id = id;
        return this;
    }

    public ShopcartLogBuilder withProduct(ProductBuilder productBuilder) {
        this.productBuilder = productBuilder;
        return this;
    }

    public ShopcartLogBuilder withCount(int count) {
        this.count = count;
        return this;
    }

    public ShopcartLogBuilder withShopcart(ShopcartBuilder shopcartBuilder) {
        this.shopCartBuilder = shopcartBuilder;
        return this;
    }

    public ShopcartLog build() {
        return new ShopcartLog(
                id, 
                productBuilder == null ? null : productBuilder.build(), 
                count,
                shopCartBuilder == null ? null : shopCartBuilder.build(),
                DateUtil.getCurrentSeconds());
    }
}