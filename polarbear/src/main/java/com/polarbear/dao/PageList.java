package com.polarbear.dao;

import java.util.List;

public class PageList<T> {
    long total;
    int pageNo;
    int pageTotal;
    List<T> list;

    public PageList() {
    }

    public PageList(long total, int pageNo, int pageSize, List<T> list) {
        super();
        this.total = total;
        this.pageNo = pageNo;
        this.pageTotal = (int)Math.ceil((double)total/pageSize);
        this.list = list;
    }

    public long getTotal() {
        return total;
    }

    public int getPageNo() {
        return pageNo;
    }

    public int getPageTotal() {
        return pageTotal;
    }

    public List<T> getList() {
        return list;
    }

}