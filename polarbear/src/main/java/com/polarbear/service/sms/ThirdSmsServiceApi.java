package com.polarbear.service.sms;

import com.polarbear.service.RemoteInvokeServiceException;

public interface ThirdSmsServiceApi {
    public int send(String[] cellphone, String msg) throws RemoteInvokeServiceException;
}
