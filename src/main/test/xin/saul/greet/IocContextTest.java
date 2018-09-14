package xin.saul.greet;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class IocContextTest{
    @Test
    void testGetBean() {

        IocContextImpl context = new IocContextImpl();
        context.registerBean(MyBean.class);
        MyBean bean = context.getBean(MyBean.class);

        assertNotNull(bean);
    }
}