package xin.saul.greet.testclass;

import xin.saul.greet.annotation.CreateOnTheFly;

public class ClassWithDependency {
    @CreateOnTheFly
    private DependencyClass dependencyClass;

    public DependencyClass getDependencyClass() {
        return dependencyClass;
    }
}
