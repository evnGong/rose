package com.island.gyy.utils;

import java.io.Serializable;
import java.lang.annotation.Annotation;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Comparator;

import static android.icu.lang.UCharacter.GraphemeClusterBreak.T;

/**
 * @author 罗深志
 * @version V1.0
 * @Description: 反射工具类
 */
public class ReflectionUtil {

    /**
     * 对给定对象obj的propertyName指定的成员变量赋值为value所指定的值
     * 注 : 该方法不能访问私有成员
     */
    public static void setProperty(Object obj, String propertyName, Object value) throws Exception {
        Class<?> clazz = obj.getClass();
        Field field = clazz.getField(propertyName);
        field.set(obj, value);
    }


    /**
     * 对给定对象obj的propertyName指定的成员变量赋值为value所指定的值
     * 该方法可以访问私有成员
     */
    public static void setNotAccessibleProperty(Object obj, String propertyName, Object value) throws Exception {
        Class<?> clazz = obj.getClass();
        Field field = clazz.getDeclaredField(propertyName);
        // 赋值前将该成员变量的访问权限打开  
        field.setAccessible(true);
        field.set(obj, value);
        // 赋值后将该成员变量的访问权限关闭  
        field.setAccessible(false);
    }

    /**
     * 对给定对象obj的propertyName指定的成员变量赋值为value所指定的值
     * 该方法可以访问私有成员
     */
    public static int getNotAccessiblePropertyValue(Object obj, String propertyName) throws Exception {
        Class<?> clazz = obj.getClass();
        Field field = clazz.getDeclaredField(propertyName);
        // 赋值前将该成员变量的访问权限打开  
        field.setAccessible(true);
        final int value = field.getInt(obj);
        // 赋值后将该成员变量的访问权限关闭  
        field.setAccessible(false);
        return value;
    }

    public static <T extends Annotation> T getNotAccessibleAnnotationFieldValue(Field f, Class<T> annotationClass) {
        try {
            T t = null;
            if(f.isAnnotationPresent(annotationClass)){
                t = f.getAnnotation(annotationClass);
            }
            return t;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static  Class getClasses(Class tClass,Class parentClass){
        Class[] lClasses = tClass.getClasses();
        Arrays.sort(lClasses);
        int index = -1;
        Class taget = null;
        for(Class lClass : lClasses){
            try {
                taget = lClass.asSubclass(parentClass);
                if(taget != null){
                    break;
                }
            }catch (ClassCastException e){
                continue;
            }
        }
        return  taget;
    }

}
