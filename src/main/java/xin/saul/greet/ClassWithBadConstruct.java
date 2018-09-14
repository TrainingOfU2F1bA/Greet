package xin.saul.greet;

public class ClassWithBadConstruct {
    public ClassWithBadConstruct() {
        throw new ConstructException();
    }
}
