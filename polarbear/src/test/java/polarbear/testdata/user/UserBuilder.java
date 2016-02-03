package polarbear.testdata.user;

import com.polarbear.domain.User;

public class UserBuilder {
    private String uname;
    private String pwd;
    private String email;
    private Long cellphone;
    private Short gender;
    private Integer createTime;
    private Long id;

    public static UserBuilder anUser() {
        return new UserBuilder();
    }

    public UserBuilder withID(long id) {
        this.id = id;
        return this;
    }

    public UserBuilder withUname(String uname) {
        this.uname = uname;
        return this;
    }

    public UserBuilder withCreateTime(int createTime) {
        this.createTime = createTime;
        return this;
    }

    public UserBuilder withCellphone(long cellphone) {
        this.cellphone = cellphone;
        return this;
    }

    public UserBuilder withPassword(String pwd) {
        this.pwd = pwd;
        return this;
    }

    public UserBuilder withEmail(String email) {
        this.email = email;
        return this;
    }

    public UserBuilder withGender(short gender) {
        this.gender = gender;
        return this;
    }

    public User build() {
        return new User(id, uname, pwd, email, cellphone, gender, createTime);
    }
}
