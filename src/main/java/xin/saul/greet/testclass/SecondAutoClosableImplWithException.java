package xin.saul.greet.testclass;

public class SecondAutoClosableImplWithException implements AutoCloseable {
    @Override
    public void close() throws Exception {
        throw new IllegalThreadStateException("Close fail");
    }
}
