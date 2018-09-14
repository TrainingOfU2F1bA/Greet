package xin.saul.greet;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import static org.junit.jupiter.api.Assertions.*;

public class IocContextTest{

    @Test
    void testGetBean() throws InstantiationException, IllegalAccessException {

        IocContextImpl context = new IocContextImpl();
        context.registerBean(MyBean.class);
        MyBean bean = context.getBean(MyBean.class);

        assertNotNull(bean);
        assertEquals(MyBean.class,bean.getClass());

    }

    @Test
    void test_should_throw_IllegalArgumentException_when_beanClazz_is_null() {


        IocContextImpl context = new IocContextImpl();
        Executable executable =() -> {
            context.registerBean(null);
        };

        assertThrows(IllegalArgumentException.class, executable,"beanClazz is manatory");

    }


    @Test
    void test_should_throw_IllegalArgumentException_when_beanClazz_is_abstract_class() {


        IocContextImpl context = new IocContextImpl();
        Executable executable =() -> {
            context.registerBean(AbstractClass.class);
        };

        assertThrows(IllegalArgumentException.class, executable,"xin.saul.greet.AbstractClass is abstract");

    }

    @Test
    void test_should_throw_IllegalArgumentException_when_beanClazz_is_class_without_default_construct() {


        IocContextImpl context = new IocContextImpl();
        Executable executable =() -> {
            context.registerBean(ClassWithoutDefaultConstructor.class);
        };

        assertThrows(IllegalArgumentException.class, executable,"xin.saul.greet.ClassWithoutDefaultConstructor has no default constructor");

    }

    @Test
    void test_should_success_when_put_a_class_which_already_exsit() {

        boolean successFlag = true;
        IocContextImpl context = new IocContextImpl();
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


        IocContextImpl context = new IocContextImpl();
        Executable executable =() -> {
            context.getBean(null);
        };

        assertThrows(IllegalArgumentException.class, executable);

    }

    @Test
    void test_should_throw_IllegalArgumentException_when_resolveClazz_had_not_be_register() {

        IocContextImpl context = new IocContextImpl();
        Executable executable =() -> {
            context.getBean(MyBean.class);
        };

        assertThrows(IllegalStateException.class, executable);

    }

    @Test
    void test_should_throw_exception_that_constructor_throw() {

        IocContextImpl context = new IocContextImpl();
        context.registerBean(ClassWithBadConstruct.class);
        Executable executable =() -> {
            context.getBean(ClassWithBadConstruct.class);
        };

        assertThrows(ConstructException.class, executable);

    }

    @Test
    void test_should_throw_IllegalArgumentException_call_register_bean_while_get_bean_have_been_running() {

        IocContextImpl context = new IocContextImpl();
        Executable executable =() -> {
            context.getBean(ClassWithContructRegisterBean.class);
            context.registerBean(ClassWithContructRegisterBean.class);
        };

        assertThrows(IllegalStateException.class, executable);
    }

    @Test
    void should_can_get_different_instance_of_different_class() throws InstantiationException, IllegalAccessException {

        IocContextImpl context = new IocContextImpl();
        context.registerBean(MySecondBean.class);
        context.registerBean(MyBean.class);
        MyBean bean = context.getBean(MyBean.class);
        MySecondBean mySecondBean = context.getBean(MySecondBean.class);

        assertEquals(MySecondBean.class,mySecondBean.getClass());
        assertEquals(MyBean.class,bean.getClass());
    }

    @Test
    void should_get_new_instance_when_getbean() throws InstantiationException, IllegalAccessException {
        IocContextImpl context = new IocContextImpl();

        context.registerBean(MyBean.class);

        MyBean bean = context.getBean(MyBean.class);

        MyBean myBean = context.getBean(MyBean.class);

        assertNotSame(bean,myBean);
    }


    @Test
    void test_should_throw_IllegalArgumentException_when_beanClazz_is_interface() {

        IocContextImpl context = new IocContextImpl();
        Executable executable =() -> {
            context.registerBean(IoCContext.class);
        };

        assertThrows(IllegalArgumentException.class, executable,"xin.saul.greet.IoCContext is abstract");

    }

    @Test
    void test_should_can_get_instance_with_base_interface() throws InstantiationException, IllegalAccessException {
        IocContextImpl context = new IocContextImpl();

        context.registerBean(MotherInterface.class,SonClass.class);
        MotherInterface bean = context.getBean(MotherInterface.class);

        assertEquals(SonClass.class,bean.getClass());

    }
}