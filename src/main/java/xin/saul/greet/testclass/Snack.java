package xin.saul.greet.testclass;

import xin.saul.greet.annotation.CreateOnTheFly;

public class Snack {
    @CreateOnTheFly
    private Status status;

    public Status getStatus() {
        return status;
    }
}
