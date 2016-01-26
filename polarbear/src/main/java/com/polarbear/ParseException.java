package com.polarbear;

import com.polarbear.util.Constants.ResultState;

public class ParseException extends Exception {
    /**
     * 
     */
    private static final long serialVersionUID = -8438984501545041871L;
    public ResultState state;

    public ParseException(String msg) {
        super(msg);
    }

    public ParseException(ResultState state) {
        super(state.emsg());
        this.state = state;
    }
}
