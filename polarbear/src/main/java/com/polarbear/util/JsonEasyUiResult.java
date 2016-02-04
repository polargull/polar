package com.polarbear.util;

import java.util.List;

import com.polarbear.service.PageList;

public class JsonEasyUiResult<T> {
    private String total;
    private List<T> rows;

    public JsonEasyUiResult(PageList<T> pageList) {
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