package greet;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import xin.saul.greet.IoCContextImpl;
import xin.saul.greet.testclass.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

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

    @Test
    void test_should_throw_IllegalStateException_when_a_dependency_cause_locality_cyclic_dependence() {
        IoCContextImpl context = new IoCContextImpl();
        context.registerBean(CycleHeadWithNewCycleBody.class);
        context.registerBean(NewCycleBody.class);
        context.registerBean(CycleTailWithTwoDependency.class);

        context.registerBean(SecondClassWithDependecy.class);
        context.registerBean(ClassWithDependency.class);
        context.registerBean(DependencyClass.class);
        context.registerBean(ThirdClassWithDependecy.class);
        context.registerBean(MyBean.class);

        Executable executable = () -> {
            CycleHeadWithNewCycleBody bean = context.getBean(CycleHeadWithNewCycleBody.class);
        };

        assertThrows(IllegalStateException.class, executable);
    }

    @Test
    void test_should_distingush_a_cycle_dependency_even_if_dependency_is_a_interface() {
        IoCContextImpl context = new IoCContextImpl();
        context.registerBean(CycleBodyWithSecondTail.class);
        context.registerBean(CycleHeadInterface.class,CycleSecondHead.class);
        context.registerBean(CycleSecondTail.class);

        Executable executable = () -> {
            CycleHead bean = (CycleHead) context.getBean(CycleHeadInterface.class);
        };

        assertThrows(IllegalStateException.class, executable);
    }

}
