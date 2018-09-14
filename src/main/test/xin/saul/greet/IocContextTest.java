package xin.saul.greet;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class IocContextTest{

    @Test
    void testGetBean() throws InstantiationException, IllegalAccessException {

        IocContextImpl context = new IocContextImpl();
        context.registerBean(MyBean.class);
        MyBean bean = context.getBean(MyBean.class);

        assertNotNull(bean);

    }

    @Test
    void test_should_throw_IllegalArgumentException_when_beanClazz_is_null() {


        Executable executable =() -> {
            IocContextImpl context = new IocContextImpl();
            context.registerBean(null);
        };

        assertThrows(IllegalArgumentException.class, executable,"beanClazz is manatory");

    }


    @Test
    void test_should_throw_IllegalArgumentException_when_beanClazz_is_abstract_class() {


        Executable executable =() -> {
            IocContextImpl context = new IocContextImpl();
            context.registerBean(AbstractClass.class);
        };

        assertThrows(IllegalArgumentException.class, executable,"xin.saul.greet.AbstractClass is abstract");

    }

    @Test
    void test_should_throw_IllegalArgumentException_when_beanClazz_is_class_without_default_construct() {


        Executable executable =() -> {
            IocContextImpl context = new IocContextImpl();
            context.registerBean(ClassWithoutDefaultConstructor.class);
        };

        assertThrows(IllegalArgumentException.class, executable,"xin.saul.greet.ClassWithoutDefaultConstructor has no default constructor");

    }
}