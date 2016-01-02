package com.polarbear.web.regist;

import static com.polarbear.util.Constants.ResultState.*;

import java.io.UnsupportedEncodingException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.polarbear.ValidateException;
import com.polarbear.dao.DaoException;
import com.polarbear.service.RemoteInvokeServiceException;
import com.polarbear.service.login.LoginData;
import com.polarbear.service.register.AppRegisterStep1Service;
import com.polarbear.service.register.AppRegisterStep2Service;
import com.polarbear.util.JsonResult;
import com.polarbear.util.cookie.CookieHelper;
import com.polarbear.util.cookie.UserCookieUtil;
import com.polarbear.web.regist.bean.ReturnVerifyCode;

@Controller
public class AppRegisterController {
    @Autowired(required = false)
    private AppRegisterStep1Service appRegisterStep1Service;
    @Autowired(required = false)
    public AppRegisterStep2Service appRegisterStep2Service;

    public static final String VERIFY_CODE = "verifycode";
    public static final String ENCODE_VERIFY_CODE = "Encode_Verify_Code";

    @RequestMapping(value = { "registStep1.json" }, method = { RequestMethod.POST, RequestMethod.GET })
    @ResponseBody
    public Object registStep1(HttpServletResponse response, HttpServletRequest request, @RequestParam("cellphone") String cellphone) {
        try {
            validateCellphone(cellphone);
            ReturnVerifyCode verifyCode = appRegisterStep1Service.completeStep1(Long.parseLong(cellphone));
            CookieHelper.setCookie(response, ENCODE_VERIFY_CODE, verifyCode.getEncodeNeedCompareVerifyCode());
            return new JsonResult(SUCCESS).put(VERIFY_CODE, verifyCode.getVerifyCode());
        } catch (ValidateException e) {
            return new JsonResult(e.state);
        } catch (RemoteInvokeServiceException e) {
            return new JsonResult(e.state);
        } catch (UnsupportedEncodingException e) {
            return new JsonResult(SYSTEM_ERR);
        }
    }

    @RequestMapping(value = { "registStep2.json" }, method = { RequestMethod.POST, RequestMethod.GET })
    @ResponseBody
    public Object registStep2(HttpServletResponse response, HttpServletRequest request, @RequestParam("verifycode") String verifycode, @RequestParam("pwd") String pwd) {
        try {
            String encodeVerifyCode = CookieHelper.getCookieValue(request, ENCODE_VERIFY_CODE);
            validateParam(verifycode, encodeVerifyCode, pwd);
            LoginData loginData = appRegisterStep2Service.completeStep2(Integer.valueOf(verifycode), encodeVerifyCode, pwd);
            UserCookieUtil.saveUserCookie(loginData.getUser(), request, response, 0);
            return new JsonResult(SUCCESS).put(LoginData.class.getSimpleName(), loginData);
        } catch (ValidateException e) {
            return new JsonResult(e.state);
        } catch (DaoException e) {
            return new JsonResult(e.state);
        } catch (UnsupportedEncodingException e) {
            return new JsonResult(SYSTEM_ERR);
        }
    }

    private void validateParam(String verifycode, String encodeVerifyCode, String pwd) throws ValidateException {
        if (!NumberUtils.isDigits(verifycode) || verifycode.trim().length() != 6) {
            throw new ValidateException(PARAM_ERR);
        }
        if (StringUtils.isEmpty(encodeVerifyCode)) {
            throw new ValidateException(PARAM_ERR);
        }
        if (StringUtils.isEmpty(pwd) || pwd.length() < 6) {
            throw new ValidateException(PARAM_ERR);
        }
    }

    private void validateCellphone(String cellphone) throws ValidateException {
        if (!NumberUtils.isDigits(cellphone) || cellphone.trim().length() != 11) {
            throw new ValidateException(PARAM_ERR);
        }
    }
}
