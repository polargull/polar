package com.polarbear.util.cookie;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import sun.security.provider.MD5;

import com.polarbear.domain.User;
import com.polarbear.util.Constants;
import com.polarbear.util.MD5Util;
import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;

public class UserCookieUtil {

//    public final static String COOKIE_NAME = "Login_User";
    private final static int ONE_DAY_SECONDS = 24 * 60 * 60;

    private UserCookieUtil() {
    }

    /**
     * 保存用户登录信息到Cookie
     * 
     * @param user
     * @param request
     * @param response
     * @param days
     */
    public static void saveUserCookie(User user, HttpServletRequest request, HttpServletResponse response, int days) {
        // 计算cookie的有效期
        Long validTime = System.currentTimeMillis() + days * ONE_DAY_SECONDS * 1000;
        // MD5加密用户信息，作为校验串
        String verifyCode = getMD5(user.getId() + ":" + validTime + ":" + Constants.WEB_KEY);
        // 计算完整的Cookie值
        String cookieValue = user.getId() + ":" + validTime + ":" + verifyCode;
        // 对Cookie的值进行BASE64编码
        String cookieValueBase64 = Base64.encode(cookieValue.getBytes());
        // 获取访问的定级域名
        String domain = null;
        try {
            domain = getTopDomainWithoutSubdomain(request.getRequestURL().toString());
        } catch (MalformedURLException e) {
        }
        // 保存Cookie
//        CookieHelper.setCookie(response, COOKIE_NAME, cookieValueBase64, domain, days * ONE_DAY_SECONDS);
    }

    /**
     * 移除用户信息的cookie,根据cookie名字
     * @param response
     */
    public static void removeUserCookie(HttpServletResponse response) {
//        CookieHelper.removeCookie(response, COOKIE_NAME);
    }
    
    /**
     * 移除用户信息的cookie,根据cookie名字和域名
     * @param response
     * @param request
     */
    public static void removeUserCookie(HttpServletResponse response,HttpServletRequest request) {
        String domain = null;
        try {
            domain = getTopDomainWithoutSubdomain(request.getRequestURL().toString());
        } catch (MalformedURLException e) {
        }
//        CookieHelper.removeCookie(response, COOKIE_NAME, domain);
        
    }


    /**
     * 获取url中的顶级域名
     * 
     * @param url
     * @return
     * @throws MalformedURLException
     */
    public static String getTopDomainWithoutSubdomain(String url) throws MalformedURLException {
        String host = new URL(url).getHost().toLowerCase();// 此处获取值转换为小写
        Pattern pattern = Pattern
                .compile("[^\\.]+(\\.com\\.cn|\\.net\\.cn|\\.org\\.cn|\\.gov\\.cn|\\.com|\\.net|\\.cn|\\.org|\\.cc|\\.me|\\.tel|\\.mobi|\\.asia|\\.biz|\\.info|\\.name|\\.tv|\\.hk|\\.公司|\\.中国|\\.网络)");
        Matcher matcher = pattern.matcher(host);
        while (matcher.find()) {
            return matcher.group();
        }
        return null;
    }

    /**
     * MD5加密
     * 
     * @param value
     * @return
     */
    public static String getMD5(String value) {
        return MD5Util.encode2hex(value);
    }
    
    public static void main(String[] args) {
        System.out.println(getMD5("noarter@123.com"));
    }

}
