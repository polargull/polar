package com.polarbear;

import com.polarbear.util.Constants.ResultState;

public class NullObjectException extends Exception {
    /**
     * 
     */
    private static final long serialVersionUID = 10162800927834046L;
    public ResultState state;

    public NullObjectException(String msg) {
        super(msg);
    }

    public NullObjectException(ResultState state) {
        super(state.emsg());
        this.state = state;
    }
}