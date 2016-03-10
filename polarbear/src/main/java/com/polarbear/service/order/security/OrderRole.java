package com.polarbear.service.order.security;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface OrderRole {
    public enum Role {
        BUYER(2), SELLER(4), ADMIN(8), SYS(16), BUYER_OR_SELLER(6), BUYER_OR_SYS(18);
        public int val;
        Role(int val) {
            this.val = val;
        }
    };
    Role role();
}