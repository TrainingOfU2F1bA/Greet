package xin.saul.greet;

import org.junit.jupiter.api.Test;
import xin.saul.greet.testclass.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ClosableIocContextTest {
    @Test
    void test_should_close_all_autoclosable_that_context_create() throws Exception {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        System.setOut(new PrintStream(out));
        IocContextImpl iocContext = new IocContextImpl();
        iocContext.registerBean(AutoClosableImpl.class);
        iocContext.registerBean(SecondAutoClosableImpl.class);

        iocContext.getBean(AutoClosableImpl.class);
        iocContext.getBean(SecondAutoClosableImpl.class);

        iocContext.close();

        assertEquals("AutoCloseableImpl has been closedSecondAutoCloseableImpl has been closed",out.toString());
    }

}
