package com.polarbear.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.NamedQueries;
import org.hibernate.annotations.NamedQuery;
@NamedQueries( {
    @NamedQuery(name = "queryDefaultAddress", query = "from Address a where a.user = ? order by a.defaultSelected desc"),
    @NamedQuery(name = "setNotDefaultByUser", query = "update Address a set a.defaultSelected= 0 where a.user = ?"),
    @NamedQuery(name = "setDefaultSelect", query = "update Address a set a.defaultSelected= 1 where a.id = ?")
})
@Entity
public class Address {
    @Id
    @GeneratedValue
    Long id;
    @Column
    String receiverName;
    @Column
    Long cellphone;
    @Column
    Long phone;
    @Column
    String district;
    @Column
    String address;
    @ManyToOne
    User user;
    @Column
    Boolean defaultSelected;

    public Address(String receiverName, Long cellphone, Long phone, String district, String address, User user, Boolean defaultSelected) {
        super();
        this.receiverName = receiverName;
        this.cellphone = cellphone;
        this.phone = phone;
        this.district = district;
        this.address = address;
        this.user = user;
        this.defaultSelected = defaultSelected;
    }
    
    public Address(String receiverName, Long cellphone, Long phone, String district, String address, User user) {
        super();
        this.receiverName = receiverName;
        this.cellphone = cellphone;
        this.phone = phone;
        this.district = district;
        this.address = address;
        this.user = user;
    }

    public Address() {
    }

    public Long getId() {
        return id;
    }

    public String getReceiverName() {
        return receiverName;
    }

    public Long getCellphone() {
        return cellphone;
    }

    public Long getPhone() {
        return phone;
    }

    public String getDistrict() {
        return district;
    }

    public String getAddress() {
        return address;
    }

    public User getUser() {
        return user;
    }

    public Boolean getDefaultSelected() {
        return defaultSelected;
    }

}