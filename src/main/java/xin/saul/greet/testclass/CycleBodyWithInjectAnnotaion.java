package xin.saul.greet.testclass;

import xin.saul.greet.annotation.CreateOnTheFly;

public class CycleBodyWithInjectAnnotaion {
    @CreateOnTheFly
    CycleTailWithInjectAnnotaion tail;
}
