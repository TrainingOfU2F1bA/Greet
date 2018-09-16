package xin.saul.greet.testclass;

import xin.saul.greet.annotation.CreateOnTheFly;

public class ThirdClassWithDependecy extends SecondClassWithDependecy {
    @CreateOnTheFly
    private DependencyClass sameDependencyWithGradFather;

    public DependencyClass getSameDependencyWithGradFather() {
        return sameDependencyWithGradFather;
    }
}
