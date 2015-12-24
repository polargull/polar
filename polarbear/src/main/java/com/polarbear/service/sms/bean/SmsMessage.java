package com.polarbear.service.sms.bean;

public class SmsMessage {
    int verificationCode;
    String message;

    public SmsMessage(int verificationCode, String message) {
        this.verificationCode = verificationCode;
        this.message = message;
    }

    public SmsMessage(String message) {
        this.message = message;
    }

    public SmsMessage() {
    }

    public int getVerificationCode() {
        return verificationCode;
    }

    public void setVerificationCode(int verificationCode) {
        this.verificationCode = verificationCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
