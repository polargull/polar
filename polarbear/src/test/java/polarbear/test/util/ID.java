package polarbear.test.util;

import java.util.UUID;

public class ID {
    public static long generate() {
        return UUID.randomUUID().getLeastSignificantBits();
    }
}
