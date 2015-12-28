package com.polarbear.util.date;

import org.joda.time.DateTime;
import org.springframework.stereotype.Component;

@Component
public class SystemClock implements IClock {

    @Override
    public DateTime now() {
        return new DateTime();
    }

}
