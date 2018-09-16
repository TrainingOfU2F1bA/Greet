package xin.saul.greet;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import xin.saul.greet.testclass.AutoClosableImpl;
import xin.saul.greet.testclass.AutoClosableImplWithException;
import xin.saul.greet.testclass.AutoClosableImplWithIllegalArgumentException;
import xin.saul.greet.testclass.SecondAutoClosableImpl;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class ClosableIocContextTest {
    @Test
    void test_should_close_all_autoclosable_that_context_create() throws Exception {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        System.setOut(new PrintStream(out));
        IoCContextImpl iocContext = new IoCContextImpl();
        iocContext.registerBean(AutoClosableImpl.class);
        iocContext.registerBean(SecondAutoClosableImpl.class);

        iocContext.getBean(AutoClosableImpl.class);
        iocContext.getBean(SecondAutoClosableImpl.class);

        iocContext.close();

        assertEquals("AutoCloseableImpl has been closedSecondAutoCloseableImpl has been closed",out.toString());
    }

    @Test
    void test_should_try_to_close_all_autoclosable_even_if_a_exception_throw_in_while_certain_instance_close() throws Exception {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        System.setOut(new PrintStream(out));
        IoCContextImpl iocContext = new IoCContextImpl();
        iocContext.registerBean(AutoClosableImpl.class);
        iocContext.registerBean(SecondAutoClosableImpl.class);
        iocContext.registerBean(AutoClosableImplWithException.class);

        iocContext.getBean(AutoClosableImpl.class);
        iocContext.getBean(AutoClosableImplWithException.class);
        iocContext.getBean(SecondAutoClosableImpl.class);

        Executable executable = () ->{
            iocContext.close();
        };

        assertThrows(IllegalStateException.class,executable);
        assertEquals("AutoCloseableImpl has been closedSecondAutoCloseableImpl has been closed",out.toString());
    }

    @Test
    void test_should_try_to_close_all_autoclosable_even_if_exceptions_throw_in_while_certain_instances_close_and_only_throw_the_first_exception() throws Exception {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        System.setOut(new PrintStream(out));
        IoCContextImpl iocContext = new IoCContextImpl();
        iocContext.registerBean(AutoClosableImpl.class);
        iocContext.registerBean(SecondAutoClosableImpl.class);
        iocContext.registerBean(AutoClosableImplWithException.class);
        iocContext.registerBean(AutoClosableImplWithIllegalArgumentException.class);

        iocContext.getBean(AutoClosableImpl.class);
        iocContext.getBean(AutoClosableImplWithException.class);
        iocContext.getBean(SecondAutoClosableImpl.class);

        Executable executable = () ->{
            iocContext.close();
        };

        assertThrows(IllegalStateException.class,executable);
        assertEquals("AutoCloseableImpl has been closedSecondAutoCloseableImpl has been closed",out.toString());
    }
}
