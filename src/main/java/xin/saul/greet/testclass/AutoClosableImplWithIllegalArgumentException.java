package xin.saul.greet.testclass;

public class AutoClosableImplWithIllegalArgumentException implements AutoCloseable {
    @Override
    public void close() throws Exception {
        throw new IllegalArgumentException("Close fail");
    }
}
