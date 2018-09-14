package xin.saul.greet;

public class ClassWithContructRegisterBean {
    public ClassWithContructRegisterBean() throws InstantiationException, IllegalAccessException {
        IocContextImpl iocContext = new IocContextImpl();
        iocContext.registerBean(MyBean.class);
    }
}
