package com.polarbear.web.regist.bean;

public class ReturnVerifyCode {
    int verifyCode;
    String encodeNeedCompareVerifyCode;

    public ReturnVerifyCode() {
    }

    public ReturnVerifyCode(int verifyCode, String encodeNeedCompareVerifyCode) {
        super();
        this.verifyCode = verifyCode;
        this.encodeNeedCompareVerifyCode = encodeNeedCompareVerifyCode;
    }

    public int getVerifyCode() {
        return verifyCode;
    }

    public void setVerifyCode(int verifyCode) {
        this.verifyCode = verifyCode;
    }

    public String getEncodeNeedCompareVerifyCode() {
        return encodeNeedCompareVerifyCode;
    }

    public void setEncodeNeedCompareVerifyCode(String encodeNeedCompareVerifyCode) {
        this.encodeNeedCompareVerifyCode = encodeNeedCompareVerifyCode;
    }

    
}
