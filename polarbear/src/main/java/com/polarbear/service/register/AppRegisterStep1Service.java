package com.polarbear.service.register;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.polarbear.ValidateException;
import com.polarbear.service.RemoteInvokeServiceException;
import com.polarbear.service.sms.SmsMessageCreater;
import com.polarbear.service.sms.SmsService;
import com.polarbear.service.sms.bean.SmsMessage;
import com.polarbear.util.Constants;
import static com.polarbear.util.Constants.ResultState.*;
import com.polarbear.web.regist.bean.ReturnVerifyCode;
import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;

@Service
public class AppRegisterStep1Service {
    @Autowired
    SmsService smsService;

    public ReturnVerifyCode completeStep1(long cellphone) throws RemoteInvokeServiceException {
        SmsMessage smsMessage = SmsMessageCreater.createMsg(SmsMessageCreater.REGISTER_VERIFICATION_CODE);
        smsService.send(new String[] { String.valueOf(cellphone) }, smsMessage);
        int verifyCode = smsMessage.getVerificationCode();
        return new ReturnVerifyCode(verifyCode, encodeNeedCompareVerifyCode(verifyCode, cellphone));
    }

    public static String encodeNeedCompareVerifyCode(int verifyCode, long cellphone) {
        StringBuilder needCompareVerifyCode = new StringBuilder();
        needCompareVerifyCode.append(verifyCode).append(":").append(cellphone).append(":").append(System.currentTimeMillis()).append(":").append(Constants.WEB_KEY);
        return Base64.encode(needCompareVerifyCode.toString().getBytes());
    }
    
    public void completeStep2(int verifyCode, String encodeVerifyCode) throws ValidateException {
        validateVerifyCode(verifyCode, encodeVerifyCode);
    }
    
    private void validateVerifyCode(int verifyCode, String encodeVerifyCode) throws ValidateException {
        throw new ValidateException(PARAM_ERR);
    }
}
