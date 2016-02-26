package com.polarbear.service.order;

public class OrderStateException extends Exception {
    /**
     * 
     */
    private static final long serialVersionUID = -4539961002569308450L;
    /**
     * 用户与订单不匹配
     */
    public static final int USER_ORDER_ERR = 1;
    /**
     * 角色与操作不匹配
     */
    public static final int ROLE_OPREATE_ERR = 2;
    /**
     * 非法操作
     */
    public static final int OPREATE_ERR = 3;
    /**
     * 订单获取锁错误
     */
    public static final int OPREATE_LOCK_ERR = 4;
    
    /**
     * 订单状态值错误
     */
    public static final int ORDER_STATE_VAL_ERR = 5;

    /**
     * 异常类型 1:用户与订单不匹配 2:角色与操作不匹配 3:非法操作
     */
    private int type = 0;

    /**
     * @param type
     */
    public OrderStateException(int type) {
        this.type = type;
    }

    public OrderStateException(int type, String msg) {
        super(msg);
        this.type = type;
    }

    /**
     * @return
     */
    public int getType() {
        return type;
    }
}