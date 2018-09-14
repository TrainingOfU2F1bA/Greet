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
        try {
            beanClazz.getConstructor();
        } catch (NoSuchMethodException e) {
            throw new IllegalArgumentException(String.format("%s has no default constructor", beanClazz.getName()));
        }
        if (!hashMap.containsKey(beanClazz)) hashMap.put(beanClazz,beanClazz);
    }

    @Override
    public <T> T getBean(Class<T> resolveClazz) throws IllegalAccessException, InstantiationException {
        if (resolveClazz == null) {
            throw new IllegalArgumentException("resolveClazz cant be null");
        }
        if (!hashMap.containsKey(resolveClazz)){
            throw new IllegalStateException("resolveClazz had not be register first");
        }
        return ((Class<T>) hashMap.get(resolveClazz)).newInstance();
    }
}
