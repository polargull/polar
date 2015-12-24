package com.polarbear.service.sms;

import org.springframework.stereotype.Service;

import com.polarbear.service.RemoteInvokeServiceException;
import com.polarbear.service.sms.bean.SmsMessage;
import static com.polarbear.util.Constants.ResultState.*;
import static com.polarbear.util.Constants.*;

@Service
public class SmsService {

    private ThirdSmsServiceApi thirdSmsServiceApi = isReleaseVersion ? new GoldSmsServiceApi() : new MockSmsServiceApi();

    public void send(String[] cellphone, SmsMessage smsMsg) throws RemoteInvokeServiceException {
        if (thirdSmsServiceApi.send(cellphone, smsMsg.getMessage()) != 1) {
            throw new RemoteInvokeServiceException(SMS_SEND_FAIL);
        }
    }
}