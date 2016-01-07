package polarbear.test.util;

import com.polarbear.util.MD5Util;
import com.polarbear.util.RandomUtil;
import com.polarbear.util.date.SystemClock;

/**
 * 应用的全局常量
 */
public class Constants {    
    public static final String UNAME = "极地鸥";
    public static final String ERROR_UNAME = "test@123.com";
    public static final String PWD = "123456";
    public static final String ERROR_PWD = "123abc";
    public static final long CELLPHONE = 13717686218l;
    public static final long ERR_CELLPHONE = 1717686218l;
    public static final int VERIFY_CODE = 666888;
    public static final int ERR_VERIFY_CODE = 666999;
    public static final String MD5_PWD = MD5Util.encode2hex(PWD);
    public static final String NEW_REGISTER = "Test_"+new SystemClock().now().getMinuteOfDay();
    public static final long NEW_CELLPHONE = RandomUtil.getMockCellPhone();
    public static final long PRODUCT_ID = 1L;
    public static final String ERR_PRODUCT_ID = "adf";
    public static final String PRODUCT_NAME = "变形精钢";
    public static final int PRODUCT_COUNT = 1;
    public static final double PRODUCT_PRICE = 100D;
    
    public static final String LOGIN_URL = Loader.getInstance().getProps("login.url");
    public static final String REGIST_STEP1_URL = Loader.getInstance().getProps("regist.step1.url");
    public static final String REGIST_STEP2_URL = Loader.getInstance().getProps("regist.step2.url");
    public static final String SHOPCART_ADD_URL = Loader.getInstance().getProps("shopcart.add.url");

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
