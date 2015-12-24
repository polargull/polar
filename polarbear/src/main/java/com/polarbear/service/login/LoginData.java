package com.polarbear.service.login;

import com.polarbear.domain.User;

public class LoginData {
    User user = new User();

    public LoginData() {}
    
    public LoginData(User user) {
        this.user = user;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
