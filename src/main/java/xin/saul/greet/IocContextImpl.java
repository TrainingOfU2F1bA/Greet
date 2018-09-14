package xin.saul.greet;

import java.lang.reflect.Modifier;
import java.util.HashMap;

public class IocContextImpl implements IoCContext{

    private HashMap<Class<?>, Object> hashMap = new HashMap<>();

    @Override
    public void registerBean(Class<?> beanClazz) throws IllegalAccessException, InstantiationException {
        if (beanClazz == null) {
            throw new IllegalArgumentException("beanClazz is mandatory");
        }
        if (Modifier.isAbstract(beanClazz.getModifiers())){
            throw new IllegalArgumentException(String.format("%s is abstract", beanClazz.getName()));
        }
        hashMap.put(beanClazz,beanClazz.newInstance());
    }

    @Override
    public <T> T getBean(Class<T> resolveClazz) {
        return (T) hashMap.get(resolveClazz);
    }
}
