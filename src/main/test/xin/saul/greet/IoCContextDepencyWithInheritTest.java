package xin.saul.greet;

import org.junit.jupiter.api.Test;
import xin.saul.greet.testclass.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class IoCContextDepencyWithInheritTest {
    @Test
    void test_should_create_instance_of_a_class_and_its_base_class_both_have_dependency() throws InstantiationException, IllegalAccessException {
        IoCContextImpl context = new IoCContextImpl();
        context.registerBean(SecondClassWithDependecy.class);
        context.registerBean(ClassWithDependency.class);
        context.registerBean(MyBean.class);
        context.registerBean(DependencyClass.class);

        SecondClassWithDependecy bean = context.getBean(SecondClassWithDependecy.class);

        assertEquals(SecondClassWithDependecy.class, bean.getClass());
        assertEquals(MyBean.class,bean.getMyBean().getClass());
        assertEquals(DependencyClass.class,bean.getDependencyClass().getClass());
    }

    @Test
    void test_should_create_instance_of_a_class_and_its_base_class_both_have_same_dependency() throws InstantiationException, IllegalAccessException {
        IoCContextImpl context = new IoCContextImpl();
        context.registerBean(SecondClassWithDependecy.class);
        context.registerBean(ClassWithDependency.class);
        context.registerBean(MyBean.class);
        context.registerBean(DependencyClass.class);
        context.registerBean(ThirdClassWithDependecy.class);

        ThirdClassWithDependecy bean = context.getBean(ThirdClassWithDependecy.class);

        assertEquals(ThirdClassWithDependecy.class, bean.getClass());

        assertEquals(MyBean.class,bean.getMyBean().getClass());
        assertEquals(DependencyClass.class,bean.getDependencyClass().getClass());
        assertEquals(DependencyClass.class,bean.getSameDependencyWithGradFather().getClass());
    }
}
