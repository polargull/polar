package polarbear.testdata.product;

import com.polarbear.service.product.query.bean.ProductStyleProperty;

public class StylePropertyBuilder {
	private String name;
	private String[] values;

	public static StylePropertyBuilder anStyleProperty() {
		return new StylePropertyBuilder();
	}

	public StylePropertyBuilder withProperty(String name, String[] values) {
		this.name = name;
		this.values = values;
		return this;
	}

	public ProductStyleProperty build() {
		ProductStyleProperty property = new ProductStyleProperty();
		property.setName(name);
		property.setValues(values);
		return property;
	}
}
