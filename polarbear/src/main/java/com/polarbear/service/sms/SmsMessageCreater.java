package com.polarbear.service.sms;

import com.polarbear.service.sms.bean.SmsMessage;
import com.polarbear.util.RandomUtil;

public class SmsMessageCreater {
    public static final int REGISTER_VERIFICATION_CODE = 1;

    public static SmsMessage createMsg(int code) {
        if (code == REGISTER_VERIFICATION_CODE) {
            StringBuilder sb = new StringBuilder();
            int verificationCode = RandomUtil.getRegisterVerificationCode();
            String message = sb.append("验证码为").append(RandomUtil.getRegisterVerificationCode()).append(", 请您于10分钟内输入。").toString();
            return new SmsMessage(verificationCode, message);
        }
        return null;
    }
}