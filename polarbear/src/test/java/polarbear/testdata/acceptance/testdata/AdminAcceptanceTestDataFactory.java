package polarbear.testdata.acceptance.testdata;

import com.polarbear.domain.Admin;
import com.polarbear.util.date.DateUtil;

public class AdminAcceptanceTestDataFactory {
    public static Admin createAdmin() {
        return new Admin(1l, "admin", "e10adc3949ba59abbe56e057f20f883e", DateUtil.getCurrentSeconds(), DateUtil.getCurrentSeconds());
    }
}