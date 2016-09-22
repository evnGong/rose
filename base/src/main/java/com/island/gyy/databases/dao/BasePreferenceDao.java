package com.island.gyy.databases.dao;

import android.text.TextUtils;

import com.alibaba.fastjson.JSON;
import com.island.gyy.base.BaseApplication;
import com.island.gyy.utils.ConfigUtils;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created with Android Studio.
 * User: Admin
 * Date: 2016/9/13
 * Time: 14:30
 * Describe:
 */
public class BasePreferenceDao<T> extends BaseDao<T>{

    protected List<T> mList;
    private Class<T> mTClass;

    void doGetClass() {
        Type genType = this.getClass().getGenericSuperclass();
        Type[] params = ((ParameterizedType) genType).getActualTypeArguments();
        this.mTClass = (Class<T>) params[0];
    }

    protected BasePreferenceDao() {
        doGetClass();
        initList();
    }


    public void initList() {
        mList = new ArrayList<T>();
        String s = ConfigUtils.getString(
                BaseApplication.getApplication(),
                mTClass.getSimpleName(), "");
        if (!TextUtils.isEmpty(s)) {
            mList.addAll(JSON.parseArray(s, mTClass));
        }
    }

    public void insert(List<T> item) {
        mList.addAll(item);
    }

    public void insert(T item) {
        if (mList.contains(item)) {
            int pos = mList.indexOf(item);
            mList.set(pos, item);
        } else {
            mList.add(item);
        }
    }


    public void save() {
        ConfigUtils.putString(BaseApplication.getApplication()
                , mTClass.getSimpleName()
                , JSON.toJSONString(mList));
    }

    public void clearData() {
        mList.clear();
    }
}
