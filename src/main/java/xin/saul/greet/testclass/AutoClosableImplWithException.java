package xin.saul.greet.testclass;

public class AutoClosableImplWithException implements AutoCloseable {
    @Override
    public void close() throws Exception {
        throw new IllegalStateException("Close fail");
    }
}
