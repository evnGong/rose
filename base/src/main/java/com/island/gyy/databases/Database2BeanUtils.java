package com.island.gyy.databases;

import android.database.Cursor;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.text.TextUtils;

import com.island.gyy.databases.annotation.ColumnName;
import com.island.gyy.databases.annotation.IncreateColumn;
import com.island.gyy.factory.ClassAsFactory;
import com.island.gyy.utils.ReflectionUtil;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created with Android Studio.
 * User: Admin
 * Date: 2016/9/8
 * Time: 11:08
 * Describe:
 */
public class Database2BeanUtils {

    public static String getColumnName(Field f) {
        ColumnName lColumnName = ReflectionUtil.getNotAccessibleAnnotationFieldValue(f, ColumnName.class);
        return null == lColumnName ? f.getName() : lColumnName.value();
    }

    public static <T> String getFieldName(T t, String columnName) {
        Field lField = null;
        try {
            Class<?> clazz = t.getClass();
            Field[] lFields = clazz.getFields();
            for (Field f : lFields) {
                if (f.isAnnotationPresent(ColumnName.class)) {
                    if (columnName.equals(getColumnName(f))) {
                        lField = f;
                        break;
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            return lField == null ? columnName : lField.getName();
        }
    }


    public static <T> HashMap<String, String> getBeanMap(T item) {
        HashMap<String, String> lMap = new HashMap<String, String>();
        Field f;
        Field[] field = item.getClass().getFields();
        String temp;
        double doubleTemp;
        int intTemp;
        long longTemp;
        for (int i = 0; i < field.length; i++) {
            String clsName = field[i].getType().getName();
            try {
                f = field[i];
                String name = getColumnName(f);
                if(name.equalsIgnoreCase("$change"))continue;
                if (f.isAnnotationPresent(IncreateColumn.class)) continue;
                if (String.class.getName().equals(clsName)) {
                    temp = (String) f.get(item);
                    if (!TextUtils.isEmpty(temp)) {
                        lMap.put(name, "'" + temp + "'");
                    }
                } else if (double.class.getName().equals(clsName)) {
                    doubleTemp = f.getDouble(item);
                    if (doubleTemp >= 0) {
                        lMap.put(name, String.valueOf(doubleTemp));
                    }
                } else if (int.class.getName().equals(clsName)) {
                    intTemp = f.getInt(item);
                    if (intTemp >= 0) {
                        lMap.put(name, String.valueOf(intTemp));
                    }
                }else if(long.class.getName().equals(clsName)){
                    longTemp = f.getLong(item);
                    if(longTemp >= 0l){
                        lMap.put(name,String.valueOf(longTemp));
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }
        return lMap;
    }

    public static <T> String[] getAllColumen(Class<T> tClass) {
        List<String> keys = new ArrayList<>();
        Field f;
        Field[] field = tClass.getFields();
        String temp;
        double doubleTemp;
        int intTemp;
        for (int i = 0; i < field.length; i++) {
            try {
                String name = getColumnName(field[i]);
                if(name.equalsIgnoreCase("$change"))continue;
                keys.add(name);
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }
        String[] str = new String[keys.size()];
        return keys.toArray(str);
    }


    @RequiresApi(Build.VERSION_CODES.HONEYCOMB)
    public static <T> T createBean(Cursor cursor, Class<T> tClass, String[] str) {
        int cursorIndex;
        T t = new ClassAsFactory<T>(tClass).createObj();
        Object obj;
        if (null == str) {
            str = cursor.getColumnNames();
        }
        for (String columnName : str) {
            cursorIndex = cursor.getColumnIndex(columnName);
            switch (cursor.getType(cursorIndex)) {
                case Cursor.FIELD_TYPE_STRING:
                    obj = cursor.getString(cursorIndex);
                    break;
                case Cursor.FIELD_TYPE_FLOAT:
                    obj = cursor.getFloat(cursorIndex);
                case Cursor.FIELD_TYPE_INTEGER:
                    obj = cursor.getInt(cursorIndex);
                    break;
                case Cursor.FIELD_TYPE_NULL:
                    obj = null;
                    break;
                case Cursor.FIELD_TYPE_BLOB:
                default:
                   long longtemp = cursor.getLong(cursorIndex);
                   if(longtemp != -1){
                       obj = longtemp;
                   }else{
                       obj = null;
                   }
                    break;
            }

            if (obj == null) continue;
            try {
                String fieldName = getFieldName(t, columnName);
                ReflectionUtil.setNotAccessibleProperty(t, fieldName, obj);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return t;
    }

    public static <T> T createBean(Cursor cursor, Class<T> clas) {
        return createBean(cursor, clas, null);
    }


    public static <T> List<T> createListBean(Cursor cursor, Class<T> tClass,int limit,int offset) {
        String[] str;
        ClassAsFactory<T> lClassAsFactory;
        T t = null;
        List<T> list = null;
        if (cursor != null) {
            str = cursor.getColumnNames();
            lClassAsFactory = new ClassAsFactory<T>(tClass);
            list = lClassAsFactory.createList();
            int size = cursor.getCount();
            if(limit > 0 && size >limit){
                size = limit;
            }
            cursor.moveToPosition(offset);
            for (int i = 0; i < size; i++) {
                t = createBean(cursor, tClass, str);
                list.add(t);
                if(!cursor.moveToNext())break;
            }
        }
        return list;
    }
}
