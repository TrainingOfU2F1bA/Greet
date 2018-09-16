package greet;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import xin.saul.greet.IoCContext;
import xin.saul.greet.IoCContextImpl;
import xin.saul.greet.testclass.*;

import static org.junit.jupiter.api.Assertions.*;

public class IoCContextTest {

    @Test
    void testGetBean() throws InstantiationException, IllegalAccessException {

        IoCContextImpl context = new IoCContextImpl();
        context.registerBean(MyBean.class);
        MyBean bean = context.getBean(MyBean.class);

        assertNotNull(bean);
        assertEquals(MyBean.class,bean.getClass());

    }

    @Test
    void test_should_throw_IllegalArgumentException_when_beanClazz_is_null() {


        IoCContextImpl context = new IoCContextImpl();
        Executable executable =() -> {
            context.registerBean(null);
        };

        assertThrows(IllegalArgumentException.class, executable,"beanClazz is manatory");

    }


    @Test
    void test_should_throw_IllegalArgumentException_when_beanClazz_is_abstract_class() {


        IoCContextImpl context = new IoCContextImpl();
        Executable executable =() -> {
            context.registerBean(AbstractClass.class);
        };

        assertThrows(IllegalArgumentException.class, executable,"xin.saul.greet.testclass.AbstractClass is abstract");

    }

    @Test
    void test_should_throw_IllegalArgumentException_when_beanClazz_is_class_without_default_construct() {


        IoCContextImpl context = new IoCContextImpl();
        Executable executable =() -> {
            context.registerBean(ClassWithoutDefaultConstructor.class);
        };

        assertThrows(IllegalArgumentException.class, executable,"xin.saul.greet.testclass.ClassWithoutDefaultConstructor has no default constructor");

    }

    @Test
    void test_should_success_when_put_a_class_which_already_exsit() {

        boolean successFlag = true;
        IoCContextImpl context = new IoCContextImpl();
        try {
            context.registerBean(MyBean.class);
            context.registerBean(MyBean.class);
        } catch (Throwable e) {
            successFlag = false;
        }

        assertTrue(successFlag);
    }


    @Test
    void test_should_throw_IllegalArgumentException_when_resolveClazz_is_null() {


        IoCContextImpl context = new IoCContextImpl();
        Executable executable =() -> {
            context.getBean(null);
        };

        assertThrows(IllegalArgumentException.class, executable);

    }

    @Test
    void test_should_throw_IllegalArgumentException_when_resolveClazz_had_not_be_register() {

        IoCContextImpl context = new IoCContextImpl();
        Executable executable =() -> {
            context.getBean(MyBean.class);
        };

        assertThrows(IllegalStateException.class, executable);

    }

    @Test
    void test_should_throw_exception_that_constructor_throw() {

        IoCContextImpl context = new IoCContextImpl();
        context.registerBean(ClassWithBadConstruct.class);
        Executable executable =() -> {
            context.getBean(ClassWithBadConstruct.class);
        };

        assertThrows(ConstructException.class, executable);

    }

    @Test
    void test_should_throw_IllegalArgumentException_call_register_bean_while_get_bean_have_been_running() {

        IoCContextImpl context = new IoCContextImpl();
        Executable executable =() -> {
            context.getBean(ClassWithContructRegisterBean.class);
            context.registerBean(ClassWithContructRegisterBean.class);
        };

        assertThrows(IllegalStateException.class, executable);
    }

    @Test
    void should_can_get_different_instance_of_different_class() throws InstantiationException, IllegalAccessException {

        IoCContextImpl context = new IoCContextImpl();
        context.registerBean(MySecondBean.class);
        context.registerBean(MyBean.class);
        MyBean bean = context.getBean(MyBean.class);
        MySecondBean mySecondBean = context.getBean(MySecondBean.class);

        assertEquals(MySecondBean.class,mySecondBean.getClass());
        assertEquals(MyBean.class,bean.getClass());
    }

    @Test
    void should_get_new_instance_when_getbean() throws InstantiationException, IllegalAccessException {
        IoCContextImpl context = new IoCContextImpl();

        context.registerBean(MyBean.class);

        MyBean bean = context.getBean(MyBean.class);

        MyBean myBean = context.getBean(MyBean.class);

        assertNotSame(bean,myBean);
    }


    @Test
    void test_should_throw_IllegalArgumentException_when_beanClazz_is_interface() {

        IoCContextImpl context = new IoCContextImpl();
        Executable executable =() -> {
            context.registerBean(IoCContext.class);
        };

        assertThrows(IllegalArgumentException.class, executable,"xin.saul.greet.IoCContext is abstract");

    }

}