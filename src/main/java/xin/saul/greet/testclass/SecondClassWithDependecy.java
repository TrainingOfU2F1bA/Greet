package xin.saul.greet.testclass;

import xin.saul.greet.annotation.CreateOnTheFly;

public class SecondClassWithDependecy extends ClassWithDependency {

    @CreateOnTheFly
    private MyBean myBean;

    public MyBean getMyBean() {
        return myBean;
    }

}
