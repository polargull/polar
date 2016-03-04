package com.polarbear.service.order.bean;

public class OrderListParam {
    private int orderListState = 1;
    private int pageNo = 1;
    private int pageSize = 10;

    public OrderListParam() {
    }

    public OrderListParam(int orderListState, int pageNo, int pageSize) {
        super();
        this.orderListState = orderListState;
        this.pageNo = pageNo;
        this.pageSize = pageSize;
    }

    public int getOrderListState() {
        return orderListState;
    }

    public void setOrderListState(int orderListState) {
        this.orderListState = orderListState;
    }

    public int getPageNo() {
        return pageNo;
    }

    public void setPageNo(int pageNo) {
        this.pageNo = pageNo;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    @Override
    public String toString() {
        return "OrderListParam [orderListState=" + orderListState + ", pageNo=" + pageNo + ", pageSize=" + pageSize + "]";
    }
    
}