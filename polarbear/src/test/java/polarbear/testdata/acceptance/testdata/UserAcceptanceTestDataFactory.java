package polarbear.testdata.acceptance.testdata;

import com.polarbear.domain.User;
import com.polarbear.util.DateUtil;
import static polarbear.testdata.builder.user.UserBuilder.anUser;

public class UserAcceptanceTestDataFactory {
    public static User createUser() {
        return anUser()
                .withID(1l)
                .withUname("极地鸥")
                .withPassword("e10adc3949ba59abbe56e057f20f883e")
                .withCreateTime(DateUtil.getCurrentSeconds())
                .withCellphone(13717686218l)
                .build();
    }
}