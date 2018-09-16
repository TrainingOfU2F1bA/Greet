package xin.saul.greet;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import xin.saul.greet.testclass.*;

import static org.junit.jupiter.api.Assertions.*;

public class IoCContextNewRegisterBeanTest {

    @Test
    void test_should_throw_IllegalArgumentException_when_beanClazz_and_resolveClazz_are_both_null() {


        IoCContextImpl context = new IoCContextImpl();
        Executable executable =() -> {
            context.registerBean(null,null);
        };

        assertThrows(IllegalArgumentException.class, executable,"beanClazz is manatory");

    }


    @Test
    void test_should_throw_IllegalArgumentException_when_beanClazz_is_abstract_class() {


        IoCContextImpl context = new IoCContextImpl();
        Executable executable =() -> {
            context.registerBean(GrandMotherClass.class,AbstractMotherClass.class);
        };

        assertThrows(IllegalArgumentException.class, executable,"xin.saul.xin.saul.greet.testclass.MotherClass is abstract");

    }

    @Test
    void test_should_throw_IllegalArgumentException_when_resolveClazz_is_null() {


        IoCContextImpl context = new IoCContextImpl();
        Executable executable =() -> {
            context.registerBean(null,SonClass.class);
        };

        assertThrows(IllegalArgumentException.class, executable,"beanClazz is manatory");

    }

    @Test
    void test_should_throw_IllegalArgumentException_when_beanClazz_is_null() {


        IoCContextImpl context = new IoCContextImpl();
        Executable executable =() -> {
            context.registerBean(MotherInterface.class,null);
        };

        assertThrows(IllegalArgumentException.class, executable,"beanClazz is manatory");

    }

    @Test
    void test_should_throw_IllegalArgumentException_when_beanClazz_is_class_without_default_construct() {


        IoCContextImpl context = new IoCContextImpl();
        Executable executable =() -> {
            context.registerBean(MotherInterface.class,ClassWithoutDefaultConstructor.class);
        };

        assertThrows(IllegalArgumentException.class, executable,"xin.saul.xin.saul.greet.testclass.ClassWithoutDefaultConstructor has no default constructor");

    }



    @Test
    void test_should_throw_exception_that_constructor_throw() {

        IoCContextImpl context = new IoCContextImpl();
        context.registerBean(MotherInterface.class,ClassWithBadConstruct.class);
        Executable executable =() -> {
            context.getBean(MotherInterface.class);
        };

        assertThrows(ConstructException.class, executable);

    }

    @Test
    void test_should_throw_IllegalArgumentException_call_register_bean_while_get_bean_have_been_running() {

        IoCContextImpl context = new IoCContextImpl();
        Executable executable =() -> {
            context.getBean(ClassWithContructRegisterBean.class);
            context.registerBean(MotherInterface.class,ClassWithContructRegisterBean.class);
        };

        assertThrows(IllegalStateException.class, executable);
    }

    @Test
    void should_can_get_different_instance_of_different_interface() throws InstantiationException, IllegalAccessException {

        IoCContextImpl context = new IoCContextImpl();
        context.registerBean(MotherInterface.class,SonClass.class);
        context.registerBean(SecondMotherInterface.class,SonClassWithSecondMotherInterface.class);
        MotherInterface bean = context.getBean(MotherInterface.class);
        SecondMotherInterface mySecondBean = context.getBean(SecondMotherInterface.class);

        assertEquals(SonClassWithSecondMotherInterface.class,mySecondBean.getClass());
        assertEquals(SonClass.class,bean.getClass());
    }

    @Test
    void should_get_new_instance_when_getbean() throws InstantiationException, IllegalAccessException {
        IoCContextImpl context = new IoCContextImpl();

        context.registerBean(MotherInterface.class,SonClass.class);

        MotherInterface bean = context.getBean(MotherInterface.class);

        MotherInterface myBean = context.getBean(MotherInterface.class);

        assertNotSame(bean,myBean);
    }

    @Test
    void test_should_can_get_instance_with_base_interface() throws InstantiationException, IllegalAccessException {
        IoCContextImpl context = new IoCContextImpl();

        context.registerBean(MotherInterface.class,SonClass.class);
        MotherInterface bean = context.getBean(MotherInterface.class);

        assertEquals(SonClass.class,bean.getClass());

    }

    @Test
    void test_should_cover_after_register_new_class_with_same_interface() throws InstantiationException, IllegalAccessException {
        IoCContextImpl context = new IoCContextImpl();

        context.registerBean(MotherInterface.class,SonClass.class);
        context.registerBean(MotherInterface.class,SecondSonClass.class);
        MotherInterface bean = context.getBean(MotherInterface.class);

        assertEquals(SecondSonClass.class,bean.getClass());

    }


    @Test
    void test_should_can_get_instance_with_base_class() throws InstantiationException, IllegalAccessException {
        IoCContextImpl context = new IoCContextImpl();

        context.registerBean(MotherClass.class,SubClass.class);
        MotherClass bean = context.getBean(MotherClass.class);

        assertEquals(SubClass.class,bean.getClass());

    }

    @Test
    void test_should_can_get_instance_with_different_base_class_or_interface() throws InstantiationException, IllegalAccessException {
         IoCContextImpl context = new IoCContextImpl();

        context.registerBean(MotherClass.class,SubClass.class);
        context.registerBean(MotherInterface.class,SubClass.class);

        MotherClass bean = context.getBean(MotherClass.class);
        MotherInterface myBean= context.getBean(MotherInterface.class);

        assertEquals(SubClass.class,bean.getClass());
        assertEquals(SubClass.class,myBean.getClass());
        assertNotSame(bean,myBean);
    }
}
