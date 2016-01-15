package polarbear.testdata.shopcart;

import polarbear.testdata.product.ProductBuilder;

import com.polarbear.domain.ShopcartDetail;
import com.polarbear.util.DateUtil;

public class ShopcartDetailBuilder {
    Long id;
    ProductBuilder productBuilder;
    Integer num;
    ShopcartBuilder shopCartBuilder;
    Integer createTime;

    public static ShopcartDetailBuilder anShopcartDetail() {
        return new ShopcartDetailBuilder();
    }

    public ShopcartDetailBuilder withId(long id) {
        this.id = id;
        return this;
    }

    public ShopcartDetailBuilder withProduct(ProductBuilder productBuilder) {
        this.productBuilder = productBuilder;
        return this;
    }

    public ShopcartDetailBuilder withNum(int num) {
        this.num = num;
        return this;
    }

    public ShopcartDetailBuilder withShopcart(ShopcartBuilder shopcartBuilder) {
        this.shopCartBuilder = shopcartBuilder;
        return this;
    }

    public ShopcartDetail build() {
        return new ShopcartDetail(
                id, 
                productBuilder == null ? null : productBuilder.build(), 
                num,
                shopCartBuilder == null ? null : shopCartBuilder.build(),
                DateUtil.getCurrentSeconds());
    }
}