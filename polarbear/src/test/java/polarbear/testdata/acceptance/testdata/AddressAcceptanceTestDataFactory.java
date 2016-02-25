package polarbear.testdata.acceptance.testdata;

import static polarbear.testdata.acceptance.testdata.UserAcceptanceTestDataFactory.*;

import com.polarbear.domain.Address;

public class AddressAcceptanceTestDataFactory {
    public static final String RECEIVER = "付玮";
    public static final long PHONE = 84047203l;
    public static final String DISTRICT = "北京&北京&东城";
    public static final String ADDRESS = "安定门街道";
    public static Address createUser1Address1() {
        return new Address(1l,RECEIVER, CELLPHONE, PHONE, DISTRICT, ADDRESS, createUser1());
    }
}