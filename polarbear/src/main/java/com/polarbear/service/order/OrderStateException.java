package com.polarbear.service.order;

import com.polarbear.util.Constants.ResultState;

public class OrderStateException extends Exception {

    private static final long serialVersionUID = -4539961002569308450L;

    public ResultState state;

    public OrderStateException(ResultState state) {
        super(state.emsg());
        this.state = state;
    }

}