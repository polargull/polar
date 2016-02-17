package com.polarbear.service.balance.to;

import java.util.HashMap;
import java.util.Map;
import static com.polarbear.util.Constants.PAY_CODE.ZHI_FU_BAO;
import static com.polarbear.util.Constants.PAY_CODE.WEI_XIN;

public class PayDistrict {
    private static Map<Integer, String> payType = new HashMap<Integer, String>();
    static {
        payType.put(ZHI_FU_BAO.value(), ZHI_FU_BAO.name());
        payType.put(WEI_XIN.value(), WEI_XIN.name());
    }

    public static Map<Integer, String> getPayType() {
        return payType;
    }
}