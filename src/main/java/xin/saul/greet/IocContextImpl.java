package xin.saul.greet;

import java.lang.reflect.Modifier;
import java.util.HashMap;

public class IocContextImpl implements IoCContext{

    private HashMap<Class<?>, Object> hashMap = new HashMap<>();
    private boolean isBeenGetingBean;

    @Override
    public void registerBean(Class<?> beanClazz) {
        if (isBeenGetingBean) {
            throw new IllegalStateException("can call register while getBean");
        }

        if (beanClazz == null) {
            throw new IllegalArgumentException("beanClazz is mandatory");
        }
        if (beanClazz.isInterface()||Modifier.isAbstract(beanClazz.getModifiers())){
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

        isBeenGetingBean = true;

        if (resolveClazz == null) {
            throw new IllegalArgumentException("resolveClazz cant be null");
        }

        if (!hashMap.containsKey(resolveClazz)){
            throw new IllegalStateException("resolveClazz had not be register first");
        }

        T t = ((Class<T>) hashMap.get(resolveClazz)).newInstance();

        isBeenGetingBean = false;

        return t;
    }

    @Override
    public <T> void registerBean(Class<? super T> resolveClazz, Class<T> beanClazz) {
        if (isBeenGetingBean) {
            throw new IllegalStateException("can call register while getBean");
        }

        if (beanClazz == null || resolveClazz == null) {
            throw new IllegalArgumentException("beanClazz is mandatory");
        }

        try {
            beanClazz.getConstructor();
        } catch (NoSuchMethodException e) {
            throw new IllegalArgumentException(String.format("%s has no default constructor", beanClazz.getName()));
        }

        hashMap.put(resolveClazz,beanClazz);
    }
}
