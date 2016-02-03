package com.polarbear.util;

import java.util.List;

import com.polarbear.dao.PageList;

public class JsonParseEasyUiFormatter<T> {
    private String total;
    private List<T> rows;

    public JsonParseEasyUiFormatter(PageList<T> pageList) {
        super();
        this.total = String.valueOf(pageList.getTotal());
        this.rows = pageList.getList();
    }

    public String getTotal() {
        return total;
    }

    public List<T> getRows() {
        return rows;
    }

    public void setRows(List<T> rows) {
        this.rows = rows;
    }
    
}