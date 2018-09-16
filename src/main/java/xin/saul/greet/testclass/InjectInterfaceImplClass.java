package xin.saul.greet.testclass;

import xin.saul.greet.annotation.CreateOnTheFly;

public class InjectInterfaceImplClass implements SecondMotherInterface{
    @CreateOnTheFly
    MotherInterface motherInterface;

    public MotherInterface getMotherInterface() {
        return motherInterface;
    }
}
