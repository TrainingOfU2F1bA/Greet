package xin.saul.greet.testclass;

public class SecondAutoClosableImpl implements AutoCloseable {
    @Override
    public void close() throws Exception {
        System.out.print("SecondAutoCloseableImpl has been closed");
    }
}
