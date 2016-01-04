package com.polarbear.service.register;

import static com.polarbear.util.Constants.ResultState.PARAM_ERR;

import java.io.UnsupportedEncodingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.polarbear.ValidateException;
import com.polarbear.service.RemoteInvokeServiceException;
import com.polarbear.service.register.util.VerifyCodeEncoder;
import com.polarbear.service.sms.SmsMessageCreater;
import com.polarbear.service.sms.SmsService;
import com.polarbear.service.sms.bean.SmsMessage;
import com.polarbear.web.regist.bean.ReturnVerifyCode;

@Service
public class AppRegisterStep1Service {
    @Autowired(required = false)
    SmsService smsService;
    VerifyCodeEncoder verifyCodeEncoder = VerifyCodeEncoder.getInstance();
    
    public ReturnVerifyCode completeStep1(long cellphone) throws RemoteInvokeServiceException, UnsupportedEncodingException {
        SmsMessage smsMessage = SmsMessageCreater.createMsg(SmsMessageCreater.REGISTER_VERIFICATION_CODE);
        smsService.send(new String[] { String.valueOf(cellphone) }, smsMessage);
        int verifyCode = smsMessage.getVerificationCode();
        return new ReturnVerifyCode(verifyCode, verifyCodeEncoder.encodeNeedCompareVerifyCode(verifyCode, cellphone));
    }
    
    public void completeStep2(int verifyCode, String encodeVerifyCode) throws ValidateException {
        validateVerifyCode(verifyCode, encodeVerifyCode);
    }
    
    private void validateVerifyCode(int verifyCode, String encodeVerifyCode) throws ValidateException {
        throw new ValidateException(PARAM_ERR);
    }
}
