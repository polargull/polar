package com.polarbear.service.order;

public class OrderStateException extends Exception {

    private static final long serialVersionUID = -4539961002569308450L;
    /**
     * 用户与订单不匹配
     */
    public static final String USER_ORDER_ERR = "用户与订单不匹配";
    /**
     * 角色与操作不匹配
     */
    public static final String ROLE_OPREATE_ERR = "角色与操作不匹配";
    /**
     * 非法操作
     */
    public static final String OPREATE_ERR = "非法操作";
    /**
     * 订单获取锁错误
     */
    public static final String OPREATE_LOCK_ERR = "订单获取锁错误";
    /**
     * 订单状态值错误
     */
    public static final String ORDER_STATE_VAL_ERR = "订单状态值错误";
    /**
     * 订单不存在
     */
    public static final String ORDER_NOT_EXIST = "订单不存在";

    public OrderStateException(String msg) {
        super(msg);
    }

}