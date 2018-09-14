package xin.saul.greet;

import java.util.HashMap;

public class IocContextImpl implements IoCContext{

    private HashMap<Class<?>, Object> hashMap = new HashMap<>();

    @Override
    public void registerBean(Class<?> beanClazz) throws IllegalAccessException, InstantiationException {
        hashMap.put(beanClazz,beanClazz.newInstance());
    }

    @Override
    public <T> T getBean(Class<T> resolveClazz) {
        return (T) hashMap.get(resolveClazz);
    }
}
