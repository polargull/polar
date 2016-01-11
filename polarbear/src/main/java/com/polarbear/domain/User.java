package com.polarbear.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.hibernate.annotations.NamedQueries;
import org.hibernate.annotations.NamedQuery;

import com.alibaba.fastjson.annotation.JSONField;

@NamedQueries( { @NamedQuery(name = "queryUnameAndPwd", query = "from User u where u.name = ? and u.pwd = ?"),
        @NamedQuery(name = "findUserByGenderAndNameAndPage", query = "from User u where u.name = ? and u.gender = ?"),
        @NamedQuery(name = "updateUserNameByGender", query = "update User u set u.name = ? where u.gender = ?") })
@Entity
@Table(name = "user", uniqueConstraints = { @UniqueConstraint(columnNames = "name"), @UniqueConstraint(columnNames = "cellphone") })
public class User {
    @Id
    @GeneratedValue
    Long id;
    @Column(nullable = false)
    String name;
    @Column(nullable = false)
    String pwd;
    @Column
    String email;
    @Column(nullable = false)
    Long cellphone;
    @Column
    Short gender;
    @Column(nullable = false)
    Integer createTime;

    public User() {
    }

    public User(Long id, String name, String pwd, String email, Long cellphone, Short gender, Integer createTime) {
        super();
        this.id = id;
        this.name = name;
        this.pwd = pwd;
        this.email = email;
        this.cellphone = cellphone;
        this.gender = gender;
        this.createTime = createTime;
    }

    public User(String name, String pwd, String email, Long cellphone, Short gender) {
        super();
        this.name = name;
        this.pwd = pwd;
        this.email = email;
        this.cellphone = cellphone;
        this.gender = gender;
    }

    public User(String name, String pwd, Long cellphone, Integer createTime) {
        super();
        this.name = name;
        this.pwd = pwd;
        this.cellphone = cellphone;
        this.createTime = createTime;
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

    @JSONField(serialize = false)
    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Long getCellphone() {
        return cellphone;
    }

    public void setCellphone(Long cellphone) {
        this.cellphone = cellphone;
    }

    public Short getGender() {
        return gender;
    }

    public void setGender(Short gender) {
        this.gender = gender;
    }

    public Integer getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Integer createTime) {
        this.createTime = createTime;
    }

}