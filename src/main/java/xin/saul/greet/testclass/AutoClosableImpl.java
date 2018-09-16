package xin.saul.greet.testclass;

public class AutoClosableImpl implements AutoCloseable {
    @Override
    public void close() throws Exception {
        System.out.print("AutoCloseableImpl has been closed");
    }
}
