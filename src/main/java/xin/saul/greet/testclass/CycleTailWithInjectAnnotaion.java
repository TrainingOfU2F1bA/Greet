package xin.saul.greet.testclass;

import xin.saul.greet.annotation.CreateOnTheFly;

public class CycleTailWithInjectAnnotaion {
    @CreateOnTheFly
    private CycleHeadWithoutInjectAnnotation head;
}
