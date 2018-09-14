package xin.saul.greet;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import xin.saul.greet.testclass.ClassWithDependency;
import xin.saul.greet.testclass.DependencyClass;

import static org.junit.jupiter.api.Assertions.*;
public class IocContextDependencyTest {
    @Test
    void test_should_create_instance_with_dependency_with_had_been_register() throws InstantiationException, IllegalAccessException {

        IocContextImpl iocContext = new IocContextImpl();
        iocContext.registerBean(DependencyClass.class);
        iocContext.registerBean(ClassWithDependency.class);
        ClassWithDependency bean = iocContext.getBean(ClassWithDependency.class);

        assertEquals(ClassWithDependency.class, bean.getClass());
        assertEquals(DependencyClass.class, bean.getDependencyClass().getClass());

    }

    @Test
    void test_should_create_class_with_dependency_with_had_not_been_register() throws InstantiationException, IllegalAccessException {

        IocContextImpl iocContext = new IocContextImpl();
        iocContext.registerBean(ClassWithDependency.class);
        Executable executable= () -> {
            ClassWithDependency bean = iocContext.getBean(ClassWithDependency.class);
        };

        assertThrows(IllegalStateException.class, executable);
    }
}
