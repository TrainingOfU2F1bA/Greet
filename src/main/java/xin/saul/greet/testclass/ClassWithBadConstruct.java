package xin.saul.greet.testclass;

public class ClassWithBadConstruct implements MotherInterface{
    public ClassWithBadConstruct() {
        throw new ConstructException();
    }
}
