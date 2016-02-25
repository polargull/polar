package com.polarbear.service.order.bean;

import java.util.Arrays;

public class OrderParam {
    long[] pids;
    int[] nums;
    Integer buyMode;
    Integer payCode;
    Long addressId;

    public OrderParam(){}

    public OrderParam(long[] pids, int[] nums, Integer buyMode, Integer payCode, Long addressId) {
        super();
        this.pids = pids;
        this.nums = nums;
        this.buyMode = buyMode;
        this.payCode = payCode;
        this.addressId = addressId;
    }

    public long[] getPids() {
        return pids;
    }

    public int[] getNums() {
        return nums;
    }

    public Long getAddressId() {
        return addressId;
    }

    public void setPids(long[] pids) {
        this.pids = pids;
    }

    public void setNums(int[] nums) {
        this.nums = nums;
    }

    public void setAddressId(Long addressId) {
        this.addressId = addressId;
    }

    public Integer getBuyMode() {
        return buyMode;
    }

    public void setBuyMode(Integer buyMode) {
        this.buyMode = buyMode;
    }

    public Integer getPayCode() {
        return payCode;
    }

    public void setPayCode(Integer payCode) {
        this.payCode = payCode;
    }

    @Override
    public String toString() {
        return "OrderParam [addressId=" + addressId + ", buyMode=" + buyMode + ", nums=" + Arrays.toString(nums) + ", payCode=" + payCode + ", pids=" + Arrays.toString(pids) + "]";
    }

}