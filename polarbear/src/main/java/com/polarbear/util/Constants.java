package com.polarbear.util;

import com.polarbear.util.Loader;

public class Constants {
    public final static String WEB_KEY = "polargull@polarbear.com";
    
    public static final boolean isReleaseVersion = Boolean.valueOf(Loader.getInstance().getProps("isReleaseVersion"));
    
    /**
     * 普通request请求后缀
     */
    public static String REQ_SUF_HTML = ".htm";
    /**
     * JSON请求后缀
     */
    public static String REQ_SUF_JSON = ".json";

    /**
     * 标签类型
     */
    public static enum TAG_TYPE {
        /**
         * 文字标签(默认标签)
         */
        DEFAULT(1),
        /**
         * 地点标签
         */
        PLACE(2),
        /**
         * 人物标签
         */
        PEOPLE(3),
        /**
         * 语音标签
         */
        VOICE(4);

        private final int value;

        TAG_TYPE(int value) {
            this.value = value;
        }

        public int value() {
            return this.value;
        }
    }

    /**
     * 排序类型
     */
    public static enum SORT_TYPE {
        ASC("ASC"), DESC("DESC");

        private final String value;

        SORT_TYPE(String value) {
            this.value = value;
        }

        public String value() {
            return value;
        }
    }

    /**
     * 排序类型，升序 OR 降序
     * 
     * @author james
     * 
     */
    public enum ComparaType {
        DESC("DESC"), ASC("ASC");

        private final String value;

        ComparaType(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }
    }

    /**
     * 操作结果,不够了
     */
    public enum ResultState {
        SUCCESS("E00000000", "操作成功")
        , NETWORK_FAIL("E00000001", "网络异常,请稍后重试")
        , SYSTEM_BUSY("E00000002", "系统繁忙，请稍后再试")
        , NEED_LOGIN("E00000003", "您需要登录后才能进行此操作")
        , INVALIDATE_AUTHORITY("E00000004","没有权限进行此操作")
        , SMS_SEND_FAIL("E00000005", "短信发送失败")
        , PARAM_ERR("E00000006", "操作失败，参数有错误")
        , SERVICE_NOT_IMPLEMNET("E00000007", "服务未实现")
        , VERIFY_CODE_INVIDIT("E00001002", "验证码失效,请重新获得验证码")
        , DB_ERR("E00001000", "数据库错误")
        , SYSTEM_ERR("E00001001", "系统错误，请稍后重试")
        , R_MOBILE_ERR("E00001003", "该号码已经注册")
        , R_12("E00001004", "用户名格式错误")
        , LOGIN_NAME_PWD_ERR("E00001005", "用户或密码不正确")
        , R_14("E00001006", "密码错误")
        , UPLOAD_SUCCESS("E00001007", "上传成功")
        , UPLOAD_FAILURE("E00001008", "上传失败")
        , R_17("E00001009", "无效Tooken")
        , R_18("E0000100A", "登录失败")
        , R_19("E0000100B", "该用户已经被绑定")
        , R_20("E0000100C", "时间选择超出设置范围")
        , R_21("E0000100D", "订单与用户不匹配")
        , R_22("E0000100E", " 查询订单与支付订单不匹配")
        , R_23("E0000100F", " 支付方式不正确 ");

        private String ecode;
        private String emsg;

        private ResultState(String ecode, String emsg) {
            this.ecode = ecode;
            this.emsg = emsg;
        }

        public String ecode() {
            return this.ecode;
        }

        public String emsg() {
            return this.emsg;
        }
    }

    public enum SessionKey {
        /**
         * 用户信息UserVO
         */
        USER("SESSION_USER"),
        /**
         * 购物车信息
         */
        SHOPCART("NA_IOS_SHOPCART"),
        /**
         * 收件箱
         */
        INBOX("SESSION_INBOX"),
        /**
         * 收件箱消息阅读器
         */
        INBOX_MSG_READER("SESSION_INBOX_MSG_READER"),
        /**
         * 登录后获取到的token
         */
        TOKEN("TOKEN");

        private String value;

        private SessionKey(String value) {
            this.value = value;
        }

        public String value() {
            return value;
        }
    }

    public static class RspInfo {

        public static final String KEY_ERROR_CODE = "ecode";

        /**
         * 消息
         */
        public static final String KEY_ERROR_MSG = "msg";
        /**
         * 消息外层包装
         */
        public static final String KEY_INFO = "info";
    }

    /**
     * 关注 OR 粉丝
     */
    public static enum FOLLOW_TYPE {
        /**
         * 关注
         */
        FOLLOWER_ATTRNTION(1),
        /**
         * 粉丝
         */
        FOLLOWER_FANS(2);

        private final int value;

        FOLLOW_TYPE(int value) {
            this.value = value;
        }

        public int value() {
            return this.value;
        }
    }

    /**
     * ios json key
     * 
     * @author zhangxing 2015-3-13
     */
    public static enum IOS_JSON_KEY {
        USER("UserVo"), SHOPCART("shopcart"), QQLOGINURL("qqloginURL"), QQLOGINCALLBACK("qqloginCallback"), WEIBOLOGIN("weibologin"), WEIBOLOGINCALLBACK("weibologinCallback"), AVAILTICKETNUM(
                "availTicketNum"), COLDTICKETNUM("coldTicketNum"), UNPERFORMORDERNUM("unperformOrderNum"), WAITCOMMENTNUM("waitCommentNum"), UNPAYNUM("unpayNum"), UNRECEIVENUM(
                "unreceiveNum"), APPEALNUM("appealNum"), ORDERLISTINFO("orderListInfo");

        private String value;

        private IOS_JSON_KEY(String value) {
            this.value = value;
        }

        public String value() {
            return value;
        }
    }
}
