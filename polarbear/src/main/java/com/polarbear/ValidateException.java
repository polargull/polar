package com.polarbear;

import com.polarbear.util.Constants.ResultState;

public class ValidateException extends Exception {
    /**
     * 
     */
    private static final long serialVersionUID = -1690377545816024381L;
    public static final String UNAME_UNIQUE_ERR = "用户名已被占用";
    
    public ValidateException(String msg) {
        super(msg);
    }
    
    public ResultState state;

    public ValidateException(ResultState state) {
        super(state.emsg());
        this.state = state;
    }

}
