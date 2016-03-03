package com.polarbear.util.factory;

import com.polarbear.domain.Admin;

public class CurrentThreadAdminFactory {
    private static final ThreadLocal<Admin> adminThreadLocal = new ThreadLocal<Admin>();

    public static void setAdmin(Admin Admin) {
        adminThreadLocal.set(Admin);
    }

    public static Admin getAdmin() {        
        return adminThreadLocal.get();
    }
    
    public static void remove() {
        adminThreadLocal.remove();
    }
}