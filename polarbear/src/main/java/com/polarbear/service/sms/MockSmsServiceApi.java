package com.polarbear.service.sms;

public class MockSmsServiceApi implements ThirdSmsServiceApi {

    @Override
    public int send(String[] cellphone, String msg) {
        return 1;
    }

}
