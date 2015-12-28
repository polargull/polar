package com.polarbear.service.register.util;

import com.polarbear.util.Constants;
import com.polarbear.util.date.IClock;
import com.polarbear.util.date.SystemClock;
import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;

public class VerifyCodeEncoder {

    IClock clock = new SystemClock();
    
    public VerifyCodeEncoder setClock(IClock clock) {
        this.clock = clock;
        return this;
    }

    public String encodeNeedCompareVerifyCode(int verifyCode, long cellphone) {
        StringBuilder needCompareVerifyCode = new StringBuilder();
        needCompareVerifyCode.append(verifyCode).append(":").append(cellphone).append(":").append(clock.now().getMillis()).append(":").append(Constants.WEB_KEY);
        return Base64.encode(needCompareVerifyCode.toString().getBytes());
    }
}
