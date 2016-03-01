package polarbear.testdata.acceptance.testdata;

import com.polarbear.domain.User;
import com.polarbear.util.date.DateUtil;
import com.polarbear.util.security.MD5Util;

import static polarbear.testdata.builder.user.UserBuilder.anUser;

public class UserAcceptanceTestDataFactory {
    public static final Long UID = 1l;
    public static final String UNAME = "极地鸥";
    public static final long CELLPHONE = 13717686218l;
    public static final String PWD = MD5Util.encode2hex("123456");
    public static final String UNAME2 = "极地鸥2";
    public static final long CELLPHONE2 = 13717686219l;
    
    public static User createUser1() {
        return anUser()
                .withID(UID)
                .withUname(UNAME)
                .withPassword(PWD)
                .withCreateTime(DateUtil.getCurrentSeconds())
                .withCellphone(CELLPHONE)
                .build();
    }
    
    public static User createUser2() {
        return anUser()
                .withID(UID + 1)
                .withUname(UNAME2)
                .withPassword(PWD)
                .withCreateTime(DateUtil.getCurrentSeconds())
                .withCellphone(CELLPHONE2)
                .build();
    }
}