package xin.saul.greet;

public interface IoCContext extends AutoCloseable{

    void registerBean(Class<?> beanClazz) throws IllegalAccessException, InstantiationException, NoSuchMethodException;

    <T> void registerBean(Class<? super T> resolveClazz,Class<T> beanClazz) throws IllegalAccessException, InstantiationException, NoSuchMethodException;

    <T> T getBean(Class<T> resolveClazz) throws IllegalAccessException, InstantiationException;
}
