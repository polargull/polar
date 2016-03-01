package polarbear.testdata.acceptance.testdata;

import static polarbear.testdata.acceptance.testdata.UserAcceptanceTestDataFactory.*;

import com.polarbear.domain.Address;

public class AddressAcceptanceTestDataFactory {
    final static long ID = 1;
    public static final String RECEIVER = "小玮";
    public static final long PHONE = 84047201l;
    public static final String DISTRICT = "北京&北京&东城";
    public static final String ADDRESS = "安定门街道";

    public static final String RECEIVER2 = "大玮";
    public static final long PHONE2 = 84047206l;
    public static final String DISTRICT2 = "北京&北京&东城";
    public static final String ADDRESS2 = "皇城根街道";

    public static Address createUser1Address1() {
        return new Address(ID, RECEIVER, CELLPHONE, PHONE, DISTRICT, ADDRESS, createUser1());
    }

    public static Address createUser2Address2() {
        return new Address(ID + 1, RECEIVER2, CELLPHONE, PHONE2, DISTRICT2, ADDRESS2, createUser2());
    }
}