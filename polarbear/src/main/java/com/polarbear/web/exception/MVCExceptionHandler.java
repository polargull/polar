package com.polarbear.web.exception;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import static com.polarbear.util.Constants.ResultState.*;
import com.polarbear.util.JsonResult;

@ControllerAdvice
public class MVCExceptionHandler {

    @ExceptionHandler(MissingServletRequestParameterException.class)
    public @ResponseBody
    Object handleRequestParameterMissException(Exception ex, HttpServletRequest request) {
        return new JsonResult(PARAM_ERR);
    }

}
