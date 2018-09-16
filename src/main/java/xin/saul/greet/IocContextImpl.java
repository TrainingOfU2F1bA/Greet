package xin.saul.greet;

import xin.saul.greet.annotation.CreateOnTheFly;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.Map;

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

        Map<Class<?>, Object> map = new HashMap<>();
        T t = createInstance(resolveClazz,map);

        isBeenGetingBean = false;

        return t;
    }

    public <T> T createInstance(Class<T> resolveClazz, Map<Class<?>, Object> map) throws IllegalAccessException, InstantiationException {
        T bean = ((Class<T>) hashMap.get(resolveClazz)).newInstance();

        map.put(resolveClazz, bean);

        for (Field field : resolveClazz.getDeclaredFields()) {
            field.setAccessible(true);
            if (field.isAnnotationPresent(CreateOnTheFly.class)) {
                Class<?> type = field.getType();
                if (map.containsKey(type)) {
                    throw new IllegalStateException("There is some field casuse cyclic dependence");
                }
                if (!hashMap.containsKey(type))
                    throw new IllegalStateException("There is a field which type have not been register");
                field.set(bean, createInstance(type,map));
            }
        }
        return bean;
    }

    @Override
    public <T> void registerBean(Class<? super T> resolveClazz, Class<T> beanClazz) {
        if (isBeenGetingBean) {
            throw new IllegalStateException("can call register while getBean");
        }

        if (beanClazz == null || resolveClazz == null) {
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

        hashMap.put(resolveClazz,beanClazz);
    }
}
