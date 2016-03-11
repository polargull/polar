package com.polarbear.service.sysjob;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class OrderSysJob {
    @Scheduled(fixedDelay = 5000)
    public void defaultUpdateOrderState() {
        System.out.print(">>>>>>>>>>>>>>> sysjob fixedDelay 5 sec test >>>>>>>>>>>>>>>>");
    }
}