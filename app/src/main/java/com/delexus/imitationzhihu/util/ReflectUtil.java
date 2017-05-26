package com.delexus.imitationzhihu.util;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Created by delexus on 2016/11/16.
 */

public class ReflectUtil {

    public static Method reflectParentMethod(Object object, String clazzName, String methodName, Class<?>... parameterTypes) {
        Method method = null;
        try {
            for (Class clazz = object.getClass(); clazz != Object.class; clazz = clazz.getSuperclass()) {
                if (clazz.getName().equals(clazzName))
                    method = clazz.getDeclaredMethod(methodName, parameterTypes);
            }
            if (method != null) {
                method.setAccessible(true);
            }
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
        return method;
    }

    public static Method reflectMethod(Object object, String methodName, Class<?>... parameterTypes) {
        Method method = null;
        try {
            method = object.getClass().getDeclaredMethod(methodName, parameterTypes);
            method.setAccessible(true);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
        return method;
    }

    public static Method reflectHideClassMethod(String className, String methodName, Class<?>... parameterTypes) {
        Method method = null;
        try {
            Class clazz = Class.forName(className);
            method = clazz.getMethod(methodName, parameterTypes);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
        return method;
    }

    public static Object invokeMethod(Object object, Method method, Object... args) {
        Object ret = null;
        try {
            ret = method.invoke(object, args);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        return ret;
    }

    public static void reflectParentFieldAndSet(Object object, Object newValue, String clazzName, String fieldName) {
        Field field = null;
        try {
            for (Class clazz = object.getClass(); clazz != Object.class; clazz = clazz.getSuperclass()) {
                if (clazz.getName().equals(clazzName))
                    field = clazz.getDeclaredField(fieldName);
            }
            if (field != null) {
                field.setAccessible(true);
                field.set(object, newValue);
            }
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    public static Object reflectParentFieldAndReturn(Object object, String clazzName, String fieldName) {
        Field field = null;
        Object ret = null;
        try {
            for (Class clazz = object.getClass(); clazz != Object.class; clazz = clazz.getSuperclass()) {
                if (clazz.getName().equals(clazzName))
                    field = clazz.getDeclaredField(fieldName);
            }
            if (field != null) {
                field.setAccessible(true);
                ret = field.get(object);
            }
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return ret;
    }

    public static Object reflectFieldAndReturn(Object object, String className, String fieldName) {
        Object ret = null;
        Field field = null;
        try {
            field = object.getClass().getDeclaredField(fieldName);
            field.setAccessible(true);
            ret = field.get(object);
            return ret;
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Method reflectStaticMethod(String clazzName, String methodName, Class... parameterTypes) {
        try {
            Class clazz = Class.forName(clazzName);
            Method method = clazz.getMethod(methodName, parameterTypes);
            return method;
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
        return null;
    }

}
