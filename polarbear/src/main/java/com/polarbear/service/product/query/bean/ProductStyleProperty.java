package com.polarbear.service.product.query.bean;

public class ProductStyleProperty {
    String name;
    String[] values;
    String value;

    public String getValue() {
        return value;
    }

    public String[] getValues() {
        return values;
    }

    public ProductStyleProperty setValues(String[] values) {
        this.values = values;
        return this;
    }

    public ProductStyleProperty setValue(String value) {
        this.value = value;
        return this;
    }

    public String getName() {
        return name;
    }

    public ProductStyleProperty setName(String name) {
        this.name = name;
        return this;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (!(obj instanceof ProductStyleProperty)) {
            return false;
        }
        ProductStyleProperty other = (ProductStyleProperty) obj;
        if (this.name.equals(other.getName()) && this.value.equals(other.getValue())) {
            return true;
        }
        return false;
    }

    @Override
    public int hashCode() {
        return 1; // any arbitrary constant will do
    }

}
