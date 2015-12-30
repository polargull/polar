package com.polarbear.dao;

import com.polarbear.util.Constants.ResultState;

public class DaoException extends Exception {
    /**
     * 
     */
    private static final long serialVersionUID = -6808435114099105843L;

    public DaoException(String msg) {
        super(msg);
    }
    
    public ResultState state;

    public DaoException(ResultState state) {
        super(state.emsg());
        this.state = state;
    }
}
