package com.polarbear.service;

import com.polarbear.util.Constants.ResultState;

public class RemoteInvokeServiceException extends Exception {
    /**
     * 
     */
    private static final long serialVersionUID = -5927784699025110426L;
    public RemoteInvokeServiceException(String msg) {
        super(msg);
    }
    
    public ResultState state;

    public RemoteInvokeServiceException(ResultState state) {
        super(state.emsg());
        this.state = state;
    }
}
