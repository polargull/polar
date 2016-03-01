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
        UNPAY(1, "创建"), PAYED(2, "支付"), DELIVERY(3, "发货"), SUCCESS(4, "成交"), CANCLE(5, "取消");
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
        , DB_DATA_NOT_UNIQUE_ERR("E00001000", "数据库数据不唯一错误")   
        , SYSTEM_ERR("E00001001", "系统错误，请稍后重试")
        , R_MOBILE_ERR("E00001003", "该号码已经注册")
        , COOKIE_ERR("E00001004", "Cookie中的数据校验错误")
        , LOGIN_NAME_PWD_ERR("E00001005", "用户或密码不正确")
        , URL_FORMATE_ERR("E00001006", "url格式错误")
        , UPLOAD_SUCCESS("E00001007", "上传成功")
        , UPLOAD_FAILURE("E00001008", "上传失败")
        , SHOPCART_PARSE_ERR("E00001009", "购物车数据解析错误")
        , PRODUCT_NOT_EXIST("E0000100B", "商品不存在")
        , R_20("E0000100C", "时间选择超出设置范围")
        , PRODUCT_NUM_IS_0("E0000100A", "抱歉,您挑选该商品已被别人抢购完,不能购买了")
        , PRODUCT_PULL_OFF("E0000100F", "抱歉,您挑选该商品刚下架,不能购买了")
        , SERVER_ERR("E0000100D", "服务器错误")
        , NULLPOINTER("E0000100D", "空指针")
        , RESOURCE_NOT_EXIST("E0000100D", "未发现资源")
        , NON_IMPLEMENT("E99999999", "还未实现");
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