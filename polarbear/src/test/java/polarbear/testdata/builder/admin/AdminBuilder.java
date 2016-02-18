package polarbear.testdata.builder.admin;

import com.polarbear.domain.Admin;

public class AdminBuilder {
    private String uname;
    private String pwd;
    private Long id;

    public static AdminBuilder anAdmin() {
        return new AdminBuilder();
    }

    public AdminBuilder withID(long id) {
        this.id = id;
        return this;
    }

    public AdminBuilder withUname(String uname) {
        this.uname = uname;
        return this;
    }

    public AdminBuilder withPassword(String pwd) {
        this.pwd = pwd;
        return this;
    }

    public Admin build() {
        return new Admin(id, uname, pwd);
    }
}