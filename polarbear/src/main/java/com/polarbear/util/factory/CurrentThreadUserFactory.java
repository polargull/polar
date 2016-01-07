package com.polarbear.util.factory;

import com.polarbear.domain.User;

public class CurrentThreadUserFactory {
    private static final ThreadLocal<User> userThreadLocal = new ThreadLocal<User>();

    public static void setUser(User user) {
        userThreadLocal.set(user);
    }

    public static User getUser() {        
        return userThreadLocal.get();
    }
    
    public static void remove() {
        userThreadLocal.remove();
    }
}