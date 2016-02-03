package com.polarbear.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import org.hibernate.annotations.NamedQuery;
@NamedQuery(name = "queryAdminNameAndPwd", query = "from Admin a where a.name = ? and a.pwd = ?")
@Entity
public class Admin {
    @Id
    @GeneratedValue
    Long id;
    @Column
    String name;
    @Column
    String pwd;
    @Column
    Integer createTime;
    @Column
    Integer loginTime;

    public Admin() {
    }

    public Admin(Long id, String name, String pwd, Integer createTime, Integer loginTime) {
        super();
        this.id = id;
        this.name = name;
        this.pwd = pwd;
        this.createTime = createTime;
        this.loginTime = loginTime;
    }

    public Admin(Long id, String name, String pwd) {
        super();
        this.id = id;
        this.name = name;
        this.pwd = pwd;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public Integer getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Integer createTime) {
        this.createTime = createTime;
    }

    public Integer getLoginTime() {
        return loginTime;
    }

    public void setLoginTime(Integer loginTime) {
        this.loginTime = loginTime;
    }
}