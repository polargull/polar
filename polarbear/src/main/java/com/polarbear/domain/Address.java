package com.polarbear.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Address {
    @Id
    @GeneratedValue
    private Long id;
    @Column
    private String receiverName;
    @Column
    private Long cellphone;
    @Column
    private Long phone;
    @Column
    private String district;
    @Column
    private String address;

    public Address(String receiverName, Long cellphone, Long phone, String district, String address) {
        super();
        this.receiverName = receiverName;
        this.cellphone = cellphone;
        this.phone = phone;
        this.district = district;
        this.address = address;
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

}