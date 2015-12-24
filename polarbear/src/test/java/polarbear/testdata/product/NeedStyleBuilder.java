package polarbear.testdata.product;

import com.polarbear.service.product.query.bean.NeedStyle;

public class NeedStyleBuilder {
    public static final long STYLE_ID = 1;
    private String property;

    public static NeedStyleBuilder anNeedStyle() {
        return new NeedStyleBuilder();
    }

    public NeedStyleBuilder withProperty(String property) {
        this.property = property;
        return this;
    }

    public NeedStyle build() {
        NeedStyle needStyle = new NeedStyle();
        needStyle.setStyleId(STYLE_ID);
        needStyle.setProperty(property);
        return needStyle;

    }
}
