package xin.saul.greet;

import org.junit.jupiter.api.Test;
import xin.saul.greet.testclass.ClassWithDependency;
import xin.saul.greet.testclass.DependencyClass;
import xin.saul.greet.testclass.MyBean;
import xin.saul.greet.testclass.SecondClassWithDependecy;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class IocContextDepencyWithInheritTest {
    @Test
    void test_should_create_instance_of_a_class_and_its_base_class_both_have_dependency() throws InstantiationException, IllegalAccessException {
        IocContextImpl context = new IocContextImpl();
        context.registerBean(SecondClassWithDependecy.class);
        context.registerBean(ClassWithDependency.class);
        context.registerBean(MyBean.class);
        context.registerBean(DependencyClass.class);

        SecondClassWithDependecy bean = context.getBean(SecondClassWithDependecy.class);

        assertEquals(SecondClassWithDependecy.class, bean.getClass());
        assertEquals(MyBean.class,bean.getMyBean().getClass());
        assertEquals(DependencyClass.class,bean.getDependencyClass().getClass());
    }
}
