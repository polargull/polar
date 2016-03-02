package com.polarbear.util;


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
     * 商品状态
     */
    public enum PRODUCT_STATE {
        PUT_ON(1), PULL_OFF(2);
        private final int value;

        PRODUCT_STATE(int value) {
            this.value = value;
        }

        public int value() {
            return this.value;
        }
    }

    public enum BUY_MODE {
        IMMEDIDATE(1), SHOPCART(2);
        private int value;

        BUY_MODE(int value) {
            this.value = value;
        }

        public int value() {
            return this.value;
        }

    }

    public enum PAY_CODE {
        HOU_DAO_FU_KUAN(1), ZHI_FU_BAO(2), WEI_XIN(3);
        private int value;

        PAY_CODE(int value) {
            this.value = value;
        }

        public int value() {
            return this.value;
        }

    }

    public enum ORDER_STATE {
        UNPAY(1, "创建"), PAYED(2, "支付"), DELIVERY(3, "发货"), SUCCESS(5, "成交"), CANCLE(9, "取消");
        private int value;
        private String op;

        ORDER_STATE(int value, String op) {
            this.value = value;
            this.op = op;
        }

        public int value() {
            return this.value;
        }

        public String op() {
            return this.op;
        }
    }

    /**
     * 操作结果,不够了
     */
    public enum ResultState {
        SUCCESS("E00", "操作成功")
        , NETWORK_FAIL("E01", "网络异常,请稍后重试")
        , SYSTEM_BUSY("E02", "系统繁忙，请稍后再试")
        , NEED_LOGIN("E03", "您需要登录后才能进行此操作")
        , INVALIDATE_AUTHORITY("E04","没有权限进行此操作")
        , SMS_SEND_FAIL("E05", "短信发送失败")
        , PARAM_ERR("E06", "操作失败，参数有错误")
        , SERVICE_NOT_IMPLEMNET("E07", "服务未实现")
        , VERIFY_CODE_INVIDIT("E08", "验证码失效,请重新获得验证码")
        , DB_ERR("E09", "数据库错误")
        , DB_DATA_NOT_UNIQUE_ERR("E10", "数据库数据不唯一错误")   
        , SYSTEM_ERR("E11", "系统错误，请稍后重试")
        , R_MOBILE_ERR("E12", "该号码已经注册")
        , COOKIE_ERR("E13", "Cookie中的数据校验错误")
        , LOGIN_NAME_PWD_ERR("E14", "用户或密码不正确")
        , URL_FORMATE_ERR("E15", "url格式错误")
        , UPLOAD_SUCCESS("E16", "上传成功")
        , UPLOAD_FAILURE("E17", "上传失败")
        , SHOPCART_PARSE_ERR("E18", "购物车数据解析错误")
        , PRODUCT_NOT_EXIST("E19", "商品不存在")
        , R_20("E20", "时间选择超出设置范围")
        , PRODUCT_NUM_IS_0("E21", "抱歉,您挑选该商品已被别人抢购完,不能购买了")
        , PRODUCT_PULL_OFF("E22", "抱歉,您挑选该商品刚下架,不能购买了")
        , SERVER_ERR("E23", "服务器错误")
        , NULLPOINTER("E24", "空指针")
        , RESOURCE_NOT_EXIST("E25", "未发现资源")
        , NON_IMPLEMENT("E99", "还未实现");
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

}