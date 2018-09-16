package xin.saul.greet;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import xin.saul.greet.testclass.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.*;
public class IoCContextDependencyTest {
    @Test
    void test_should_initilize_base_class_first_in_while_inject_object() throws InstantiationException, IllegalAccessException {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        System.setOut(new PrintStream(out));
        IoCContextImpl iocContext = new IoCContextImpl();
        iocContext.registerBean(Cow.class);
        iocContext.registerBean(LittleCow.class);

        iocContext.getBean(LittleCow.class);

        assertEquals("It is a CowIt is a LittleCow",out.toString());
    }

    @Test
    void test_should_create_instance_with_dependency_with_had_been_register() throws InstantiationException, IllegalAccessException {

        IoCContextImpl iocContext = new IoCContextImpl();
        iocContext.registerBean(DependencyClass.class);
        iocContext.registerBean(ClassWithDependency.class);
        ClassWithDependency bean = iocContext.getBean(ClassWithDependency.class);

        assertEquals(ClassWithDependency.class, bean.getClass());
        assertEquals(DependencyClass.class, bean.getDependencyClass().getClass());

    }

    @Test
    void test_should_create_class_with_dependency_with_had_not_been_register() throws InstantiationException, IllegalAccessException {

        IoCContextImpl iocContext = new IoCContextImpl();
        iocContext.registerBean(ClassWithDependency.class);
        Executable executable= () -> {
            ClassWithDependency bean = iocContext.getBean(ClassWithDependency.class);
        };

        assertThrows(IllegalStateException.class, executable);
    }

    @Test
    void test_should_throw_IllegalStateException_when_a_dependency_cause_mutual_cyclic_dependence() {

        IoCContextImpl iocContext = new IoCContextImpl();
        iocContext.registerBean(Status.class);
        iocContext.registerBean(Snack.class);

        Executable executable = () -> {
            Snack bean = iocContext.getBean(Snack.class);
        };

        assertThrows(IllegalStateException.class, executable);
    }

    @Test
    void test_should_throw_IllegalStateException_when_a_dependency_cause_self_cyclic_dependency() {
        IoCContextImpl context = new IoCContextImpl();
        context.registerBean(Node.class);


        Executable executable = () -> {
            Node bean = context.getBean(Node.class);
        };

        assertThrows(IllegalStateException.class, executable);
    }

    @Test
    void test_should_throw_IllegalStateException_when_a_dependency_cause_indirect_cyclic_dependence() {
         IoCContextImpl context = new IoCContextImpl();
        context.registerBean(CycleBody.class);
        context.registerBean(CycleHead.class);
        context.registerBean(CycleTail.class);


        Executable executable = () -> {
            CycleHead bean = context.getBean(CycleHead.class);
        };

        assertThrows(RuntimeException.class, executable);
    }

    @Test
    void test_should_create_instance_when_no_inject_annotation() throws InstantiationException, IllegalAccessException {
          IoCContextImpl context = new IoCContextImpl();
        context.registerBean(CycleBody.class);
        context.registerBean(CycleHeadWithoutInjectAnnotation.class);
        context.registerBean(CycleTail.class);


        CycleHeadWithoutInjectAnnotation bean = context.getBean(CycleHeadWithoutInjectAnnotation.class);

        assertEquals(CycleHeadWithoutInjectAnnotation.class,bean.getClass());
        assertEquals(null,bean.getBody());
    }

}
