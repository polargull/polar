package polarbear.testdata.shopcart;

import polarbear.testdata.user.UserBuilder;

import com.polarbear.domain.Shopcart;
import com.polarbear.util.DateUtil;

public class ShopcartBuilder {
    private Long id;
    private UserBuilder userBuilder;
    private Integer productNum;
    private Double price;

    public static ShopcartBuilder anShopcart() {
        return new ShopcartBuilder();
    }

    public ShopcartBuilder withId(long id) {
        this.id = id;
        return this;
    }

    public ShopcartBuilder withUser(UserBuilder userBuilder) {
        this.userBuilder = userBuilder;
        return this;
    }

    public ShopcartBuilder withProductNum(int productNum) {
        this.productNum = productNum;
        return this;
    }

    public ShopcartBuilder withPrice(double price) {
        this.price = price;
        return this;
    }

    public Shopcart build() {
        return new Shopcart(id, userBuilder == null ? null : userBuilder.build(), productNum, price, DateUtil.getCurrentSeconds());
    }
}