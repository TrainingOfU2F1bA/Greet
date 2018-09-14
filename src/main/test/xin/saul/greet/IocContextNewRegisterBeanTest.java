package xin.saul.greet;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import xin.saul.greet.testclass.*;

import static org.junit.jupiter.api.Assertions.*;

public class IocContextNewRegisterBeanTest {

    @Test
    void test_should_throw_IllegalArgumentException_when_beanClazz_and_resolveClazz_are_both_null() {


        IocContextImpl context = new IocContextImpl();
        Executable executable =() -> {
            context.registerBean(null,null);
        };

        assertThrows(IllegalArgumentException.class, executable,"beanClazz is manatory");

    }

    @Test
    void test_should_throw_IllegalArgumentException_when_resolveClazz_is_null() {


        IocContextImpl context = new IocContextImpl();
        Executable executable =() -> {
            context.registerBean(null,SonClass.class);
        };

        assertThrows(IllegalArgumentException.class, executable,"beanClazz is manatory");

    }

    @Test
    void test_should_throw_IllegalArgumentException_when_beanClazz_is_null() {


        IocContextImpl context = new IocContextImpl();
        Executable executable =() -> {
            context.registerBean(MotherInterface.class,null);
        };

        assertThrows(IllegalArgumentException.class, executable,"beanClazz is manatory");

    }

    @Test
    void test_should_throw_IllegalArgumentException_when_beanClazz_is_class_without_default_construct() {


        IocContextImpl context = new IocContextImpl();
        Executable executable =() -> {
            context.registerBean(MotherInterface.class,ClassWithoutDefaultConstructor.class);
        };

        assertThrows(IllegalArgumentException.class, executable,"xin.saul.greet.testclass.ClassWithoutDefaultConstructor has no default constructor");

    }



    @Test
    void test_should_throw_exception_that_constructor_throw() {

        IocContextImpl context = new IocContextImpl();
        context.registerBean(MotherInterface.class,ClassWithBadConstruct.class);
        Executable executable =() -> {
            context.getBean(MotherInterface.class);
        };

        assertThrows(ConstructException.class, executable);

    }

    @Test
    void test_should_throw_IllegalArgumentException_call_register_bean_while_get_bean_have_been_running() {

        IocContextImpl context = new IocContextImpl();
        Executable executable =() -> {
            context.getBean(ClassWithContructRegisterBean.class);
            context.registerBean(MotherInterface.class,ClassWithContructRegisterBean.class);
        };

        assertThrows(IllegalStateException.class, executable);
    }

    @Test
    void should_can_get_different_instance_of_different_interface() throws InstantiationException, IllegalAccessException {

        IocContextImpl context = new IocContextImpl();
        context.registerBean(MotherInterface.class,SonClass.class);
        context.registerBean(SecondMotherInterface.class,SonClassWithSecondMotherInterface.class);
        MotherInterface bean = context.getBean(MotherInterface.class);
        SecondMotherInterface mySecondBean = context.getBean(SecondMotherInterface.class);

        assertEquals(SonClassWithSecondMotherInterface.class,mySecondBean.getClass());
        assertEquals(SonClass.class,bean.getClass());
    }

    @Test
    void should_get_new_instance_when_getbean() throws InstantiationException, IllegalAccessException {
        IocContextImpl context = new IocContextImpl();

        context.registerBean(MotherInterface.class,SonClass.class);

        MotherInterface bean = context.getBean(MotherInterface.class);

        MotherInterface myBean = context.getBean(MotherInterface.class);

        assertNotSame(bean,myBean);
    }

    @Test
    void test_should_can_get_instance_with_base_interface() throws InstantiationException, IllegalAccessException {
        IocContextImpl context = new IocContextImpl();

        context.registerBean(MotherInterface.class,SonClass.class);
        MotherInterface bean = context.getBean(MotherInterface.class);

        assertEquals(SonClass.class,bean.getClass());

    }

    @Test
    void test_should_cover_after_register_new_class_with_same_interface() throws InstantiationException, IllegalAccessException {
        IocContextImpl context = new IocContextImpl();

        context.registerBean(MotherInterface.class,SonClass.class);
        context.registerBean(MotherInterface.class,SecondSonClass.class);
        MotherInterface bean = context.getBean(MotherInterface.class);

        assertEquals(SecondSonClass.class,bean.getClass());

    }
}
