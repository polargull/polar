package com.polarbear.service.sms;

import com.polarbear.service.RemoteInvokeServiceException;
import static com.polarbear.util.Constants.ResultState.*;
public class GoldSmsServiceApi implements ThirdSmsServiceApi{

    @Override
    public int send(String[] cellphone, String msg) throws RemoteInvokeServiceException {
        //TODO 短信服务调用实现
        throw new RemoteInvokeServiceException(SERVICE_NOT_IMPLEMNET);
    }

}
