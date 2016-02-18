package polarbear.testdata.builder.shopcart;

import polarbear.testdata.builder.user.UserBuilder;

import com.polarbear.domain.Shopcart;
import com.polarbear.util.DateUtil;

public class ShopcartBuilder {
    private Long id;
    private UserBuilder userBuilder;
    private Integer productNum;

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

    public Shopcart build() {
        return new Shopcart(id, userBuilder == null ? null : userBuilder.build(), productNum, DateUtil.getCurrentSeconds());
    }
}