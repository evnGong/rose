package com.island.gyy.factory;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

/**
 * Created with Android Studio.
 * User: Admin
 * Date: 2016/9/8
 * Time: 11:16
 * Describe:
 */
public class ClassAsFactory<T> {
    private Class<T> type;

    public ClassAsFactory(Class<T> t) {
        type = t;
    }

    public T createObj() {
        T tObj = null;
        try {
            tObj = type.newInstance();
            System.out.println(tObj.getClass().getSimpleName() + " object!");
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } finally {
            return tObj;
        }
    }

    public T[] createArray(int size) {
        return (T[]) Array.newInstance(type, size);
    }

    public List<T> createList() {
        return new ArrayList<T>();
    }

    public List<T> createList(int size){
        List<T> list = new ArrayList<T>();
        for(int i = 0;i<size;i++){
            list.add(createObj());
        }
        return  list;
    }
}
