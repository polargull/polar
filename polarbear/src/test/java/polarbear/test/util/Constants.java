package polarbear.test.util;

/**
 * 应用的全局常量
 */
public class Constants {
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
