package xin.saul.greet;

public interface IoCContext{
    void registerBean(Class<?> beanClazz) throws IllegalAccessException, InstantiationException, NoSuchMethodException;
    <T> T getBean(Class<T> resolveClazz);
}
