package polarbear.testdata.user;

import polarbear.test.util.ID;

import com.polarbear.domain.User;

public class UserBuilder {
    private String uname;
    private String pwd;
    private long id;
    public static UserBuilder anUser() {
        return new UserBuilder();
    }

    public UserBuilder withID(long id) {
        this.id = ID.generate();
        return this;
    }
    
    public UserBuilder withUname(String uname) {
        this.uname = uname;
        return this;
    }
    
    public UserBuilder withPassword(String pwd) {
        this.pwd = pwd;
        return this;
    }

    public User build() {
        User user = new User();
        user.setId(id);
        user.setName(uname);
        user.setPwd(pwd);
        return user;
    }
}
