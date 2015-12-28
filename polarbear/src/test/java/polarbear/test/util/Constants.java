package polarbear.test.util;

import com.polarbear.util.MD5Util;

/**
 * 应用的全局常量
 */
public class Constants {
    public static final String UNAME = "极地鸥";
    public static final String PWD = "123456";
    public static final long CELLPHONE = 13717686218l;
    public static final long ERR_CELLPHONE = 1717686218l;
    public static final int VERIFY_CODE = 666888;
    public static final int ERR_VERIFY_CODE = 666999;
    public static final String MD5_PWD = MD5Util.encode2hex(PWD);
    public static final String NEW_REGISTER = "极地鸥1";
    
    public static final String LOGIN_URL = Loader.getInstance().getProps("login.url");
    public static final String REGIST_STEP1_URL = Loader.getInstance().getProps("regist.step1.url");
    public static final String REGIST_STEP2_URL = Loader.getInstance().getProps("regist.step2.url");

    public enum ALLOWTYPE {
        FOLLOW(0), ATTEND(1);
        private final int value;

        ALLOWTYPE(int value) {
            this.value = value;
        }

        public int intValue() {
            return this.value;
        }
    }
}
