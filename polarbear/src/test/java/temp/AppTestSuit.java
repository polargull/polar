package temp;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses( { JodaTimeTest.class,FastJsonTest.class })
public class AppTestSuit {
    // the class remains empty,
    // used only as a holder for the above annotations
}
