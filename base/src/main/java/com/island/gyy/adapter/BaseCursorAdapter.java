package com.island.gyy.adapter;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;

import com.island.gyy.databases.Database2BeanUtils;
import com.island.gyy.databases.DatabaseUtils;

/**
 * Created with Android Studio.
 * User: Admin
 * Date: 2016/9/8
 * Time: 11:00
 * Describe:
 */
public abstract class BaseCursorAdapter<T> extends CursorAdapter {

    private Class<T> type;
    private int resId;

    public BaseCursorAdapter(Context context,boolean autoRequery) {
        super(context, null, autoRequery);
    }

    public void setType(Class<T> type) {
        this.type = type;
    }

    public void setResId(int resId) {
        this.resId = resId;
    }


    @Override
    public T getItem(int position) {
        Cursor lCursor = (Cursor) super.getItem(position);
        if (lCursor == null) return null;
        if (null == type) throw new NullPointerException("未设置class");
        return Database2BeanUtils.createBean(lCursor, type);
    }

    @Override
    public  View newView(Context context, Cursor cursor, ViewGroup parent) {
        View v = LayoutInflater.from(context).inflate(resId, null, false);
        return v;
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        showViewData(view, Database2BeanUtils.createBean(cursor,type),cursor);
    }


    /**
     * 数据实体绑定 建议使用，使用ViewHolder{@link com.hnfresh.adapter.viewholder.ViewHolder}
     * 绑定数据与view {@code
     *      //get 方法已经使用 T extent View 泛型
     * 		TextView t =	ViewHolder.get(convertView,R.id.textview);
     * } 简单使用baseadapter。不超过5代码设置view请复写此处
     * 复杂的view设置请重写{@link com.hnfresh.base.IAdapterViewDelegate}}
     *
     * @param
     **/
    public abstract void showViewData(View convertView, T item, Cursor cursor);
}
