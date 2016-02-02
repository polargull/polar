package com.polarbear.service.login;

import com.alibaba.fastjson.annotation.JSONField;

public class LoginData<T> {
    T user;
    @JSONField(serialize = false)
    String authEncode;

    public LoginData() {
    }

    public LoginData(T user) {
        this.user = user;
    }

    public LoginData(T user, String authEncode) {
        this.user = user;
        this.authEncode = authEncode;
    }

    public String getAuthEncode() {
        return authEncode;
    }

    public T getUser() {
        return user;
    }

    public void setUser(T t) {
        this.user = t;
    }

}