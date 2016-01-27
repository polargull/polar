package com.polarbear.service.product.query.bean;

import com.polarbear.service.product.util.StylePropertyTransferUtil;

public class NeedStyle {
    long styleId;
    String property;

    public NeedStyle() {
    }

    public NeedStyle(long styleId, String property) {
        super();
        this.styleId = styleId;
        this.property = property;
    }

    public long getStyleId() {
        return styleId;
    }

    public void setStyleId(long styleId) {
        this.styleId = styleId;
    }

    public String getProperty() {
        return property;
    }

    public void setProperty(String property) {
        this.property = property;
    }

}
