package polarbear.testdata.user;

import polarbear.test.util.ID;

import com.polarbear.domain.User;

public class UserBuilder {
    private String uname;
    private String pwd;
    private long cellphone;
    private int createTime;
    
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
    

    public User build() {
        User user = new User();
        if (id != 0 ) {
            user.setId(id);
        }
        user.setName(uname);
        user.setPwd(pwd);
        user.setCellphone(cellphone);
        user.setCreateTime(createTime);
        return user;
    }
}
