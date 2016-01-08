package com.polarbear.web.exception;

import static com.polarbear.util.Constants.ResultState.PARAM_ERR;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import com.polarbear.ValidateException;
import com.polarbear.util.JsonResult;

@ControllerAdvice
public class MVCExceptionHandler {
    private Log log = LogFactory.getLog(MVCExceptionHandler.class);

    @ExceptionHandler(MissingServletRequestParameterException.class)
    public @ResponseBody
    Object handleRequestParameterMissException(Exception ex, HttpServletRequest request) {
        log.debug("");
        return new JsonResult(PARAM_ERR);
    }

    @ExceptionHandler(ValidateException.class)
    public @ResponseBody
    Object handleValidateException(ValidateException e, HttpServletRequest request) {
        log.debug(">>>>>>>>>>>" + e.state.emsg());
        return new JsonResult(e.state);
    }
}