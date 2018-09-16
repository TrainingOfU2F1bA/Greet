package xin.saul.greet.testclass;

import xin.saul.greet.annotation.CreateOnTheFly;

public class CycleTailWithTwoDependency extends ThirdClassWithDependecy{

    @CreateOnTheFly
    MyBean myBean;

    @CreateOnTheFly
    CycleHeadWithNewCycleBody head;
}
