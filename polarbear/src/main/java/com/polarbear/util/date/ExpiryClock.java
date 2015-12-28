package com.polarbear.util.date;

import org.joda.time.DateTime;

public class ExpiryClock implements IClock {
    DateTime dateTime = new DateTime();

    public ExpiryClock plusMinutes(int minutes) {
        dateTime = dateTime.plusMinutes(minutes);
        return this;
    }

    @Override
    public DateTime now() {
        return dateTime;
    }

}
