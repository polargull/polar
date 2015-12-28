package temp;

import org.joda.time.DateTime;
import org.junit.Test;

import com.polarbear.util.date.ExpiryClock;

public class JodaTimeTest {

    @Test
    public void testSimple() {
        DateTime now = new DateTime();
        DateTime then = now.plusDays(-1);
        System.out.println(now.toString("yyyy-MM-dd HH:mm"));
    }
    
    @Test
    public void test() {
        ExpiryClock expiryClock = new ExpiryClock().plusMinutes(-60);
        System.out.println(expiryClock.now().toString("yyyy-MM-dd HH:mm"));
    }

}