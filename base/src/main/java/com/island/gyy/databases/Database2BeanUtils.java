package com.island.gyy.databases;

import android.database.Cursor;
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
    /**
     * @method 获取数据库列名
     * @author Island_gyy 【island_yy@qq.com】
     * @date 2016/10/10 16:43
     * @describe 如果columnName value 值为非空使用别名，空则使用列名
     */
    public static String getColumnName(Field f) {
        ColumnName lColumnName = ReflectionUtil.getNotAccessibleAnnotationFieldValue(f, ColumnName.class);
        return lColumnName == null || TextUtils.isEmpty(lColumnName.value()) ? f.getName() : lColumnName.value();
    }

    /**
     * @method 是否数据库的列名
     * @author Island_gyy 【island_yy@qq.com】
     * @date 2016/10/10 16:44
     * @describe
     */
    public static boolean isColumnName(Field f) {
        return f.isAnnotationPresent(ColumnName.class);
    }

    /**
     * @method 获取数据库列原始成员变量名
     * @author Island_gyy 【island_yy@qq.com】
     * @date 2016/10/10 16:46
     * @describe 1.如果被别名就获取原始名
     * 2.如果没有被别名就使用原始的
     */
    public static <T> String getFieldName(T t, String columnName) {
        Field lField = null;
        try {
            Class<?> clazz = t.getClass();
            Field[] lFields = clazz.getFields();
            for (Field f : lFields) {
                if (isColumnName(f)) {
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

    /**
     * @method 获取带值的列与列数据
     * @author  Island_gyy 【island_yy@qq.com】
     * @date 2016/10/10 16:51
     * @describe
     */
    public static <T> HashMap<String, String> getBeanMap(T item) {

        HashMap<String, String> lMap = new HashMap<String, String>();
        Field f;
        Field[] field = item.getClass().getFields();
        String temp;
        double doubleTemp;
        int intTemp;
        long longTemp;

        for (int i = 0; i < field.length; i++) {
            //获取成员变量类型名
            String clsName = field[i].getType().getName();
            try {
                f = field[i];
                //获取成员变量所注解的列名
                String name = getColumnName(f);
                //校验某些注解
                //忽略android InsRun 可能在class最前加入$change注解，
                if (name.equalsIgnoreCase("$change")) continue;
                //忽略自增列，忽略没有ColumnName注解的成员变量
                if (f.isAnnotationPresent(IncreateColumn.class) || !isColumnName(f)) continue;
                //按照成员变量的类型注入数据
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
                } else if (long.class.getName().equals(clsName)) {
                    longTemp = f.getLong(item);
                    if (longTemp >= 0l) {
                        lMap.put(name, String.valueOf(longTemp));
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }
        return lMap;
    }

    /**
     * @method 当前类获取所有的列名
     * @author  Island_gyy 【island_yy@qq.com】
     * @date 2016/10/10 16:58
     * @describe
     */
    public static <T> String[] getAllColumen(Class<T> tClass) {
        List<String> keys = new ArrayList<>();
        Field f;
        Field[] field = tClass.getFields();
        String temp;
        double doubleTemp;
        int intTemp;
        for (int i = 0; i < field.length; i++) {
            try {
                //没有columnname的被忽略
                if (!isColumnName(field[i])) continue;
                //获取所有列名
                String name = getColumnName(field[i]);
                //包含$change被忽略
                if (name.equalsIgnoreCase("$change")) continue;
                keys.add(name);
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }
        String[] str = new String[keys.size()];
        return keys.toArray(str);
    }

    /**
     * @method 创建数列=》实体（可指定列）
     * @author  Island_gyy 【island_yy@qq.com】
     * @date 2016/10/10 16:59
     * @describe
     */
    public static <T> T createBean(Cursor cursor, Class<T> tClass, String[] str) {
        int cursorIndex;
        //泛型创建类
        T t = new ClassAsFactory<T>(tClass).createObj();
        Object obj;
        if (null == str) {
            //获取列名
            str = cursor.getColumnNames();
        }
        //根据列名和数据库原始类型获取值
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
                    try {
                        long longtemp = cursor.getLong(cursorIndex);
                        obj = longtemp;
                    } catch (Exception e) {
                        try {
                            obj = cursor.getString(cursorIndex);
                        } catch (Exception e2) {
                            obj = null;
                        }
                    }
                    break;
            }

            if (obj == null) continue;
            try {
                //根据列名获取成员变量
                String fieldName = getFieldName(t, columnName);
                //给实体
                ReflectionUtil.setNotAccessibleProperty(t, fieldName, obj);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return t;
    }

    /**
     * @method 根据默认列创建实体
     * @author  Island_gyy 【island_yy@qq.com】
     * @date 2016/10/10 17:04
     * @describe
     */
    public static <T> T createBean(Cursor cursor, Class<T> clas) {
        return createBean(cursor, clas, null);
    }


    /**
     * @method 创建一列数据
     * @author  Island_gyy 【island_yy@qq.com】
     * @date 2016/10/10 17:04
     * @describe
     * @param cursor {@link android.database.Cursor}
     * @param  tClass 制定的实体类型
     * @param  limit 限制创建的list的长度
     * @param  offset cursor的下标
     */
    public static <T> List<T> createListBean(Cursor cursor, Class<T> tClass, int limit, int offset) {
        String[] str;
        ClassAsFactory<T> lClassAsFactory;
        T t = null;
        List<T> list = null;
        if (cursor != null) {
            str = cursor.getColumnNames();
            //泛型工厂创建list
            lClassAsFactory = new ClassAsFactory<T>(tClass);
            list = lClassAsFactory.createList();
            int size = cursor.getCount();
            if (limit > 0 && size > limit) {
                size = limit;
            }
            cursor.moveToPosition(offset);
            for (int i = 0; i < size; i++) {
                t = createBean(cursor, tClass, str);
                list.add(t);
                if (!cursor.moveToNext()) break;
            }
        }
        return list;
    }
}
