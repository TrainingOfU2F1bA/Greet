package xin.saul.greet.testclass;

public class ClassWithBadConstruct {
    public ClassWithBadConstruct() {
        throw new ConstructException();
    }
}
