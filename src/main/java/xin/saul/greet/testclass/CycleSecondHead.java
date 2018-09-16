package xin.saul.greet.testclass;

import xin.saul.greet.annotation.CreateOnTheFly;

public class CycleSecondHead implements  CycleHeadInterface{
    @CreateOnTheFly
    CycleBodyWithSecondTail body;
}
