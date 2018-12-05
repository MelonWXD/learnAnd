package com.dongua.interview.livecycle;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class Utils {

    public static Method getMethod(Class clazz, String methodName,
                                   final Class[] classes) throws Exception {
        Method method = null;
        try {
            method = clazz.getDeclaredMethod(methodName, classes);
        } catch (NoSuchMethodException e) {
            try {
                method = clazz.getMethod(methodName, classes);
            } catch (NoSuchMethodException ex) {
                if (clazz.getSuperclass() == null) {
                    return method;
                } else {
                    method = getMethod(clazz.getSuperclass(), methodName,
                            classes);
                }
            }
        }
        return method;
    }

    public static Object invokeMethod(Class fromClz, Object obj, String methodName, Object... args)
            throws InvocationTargetException,
            IllegalAccessException,
            NoSuchMethodException {
        Method method = fromClz.getDeclaredMethod(methodName);
        method.setAccessible(true);
        return method.invoke(obj, args);

    }

    public static Method getMethodFromObj(Object obj, String methodName, Class<?>... parameterTypes)
            throws NoSuchMethodException {
        Method method = obj.getClass().getDeclaredMethod(methodName, parameterTypes);
        method.setAccessible(true);
        return method;

    }


    public static Field getFiled(Class fromClz,   String filedName)
            throws NoSuchFieldException {
        Field field = fromClz.getDeclaredField(filedName);
        field.setAccessible(true);
        return field;

    }

    public static Object invokeReflectMethod(Object obj, String methodName, final Class[] classes)
            throws Exception {
        Method method = getMethod(obj.getClass(), methodName, classes);
        method.setAccessible(true);
        return method.invoke(obj, classes);
    }
}
