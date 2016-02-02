package com.polarbear.service.login;

public class LoginData<T> {
    T user;

    public LoginData() {
    }

    public LoginData(T user) {
        this.user = user;
    }

    public T getUser() {
        return user;
    }

    public void setUser(T t) {
        this.user = t;
    }

}