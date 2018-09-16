package xin.saul.greet.testclass;

import xin.saul.greet.IoCContextImpl;

public class ClassWithContructRegisterBean implements MotherInterface{
    public ClassWithContructRegisterBean() throws InstantiationException, IllegalAccessException {
        IoCContextImpl iocContext = new IoCContextImpl();
        iocContext.registerBean(MyBean.class);
    }
}
