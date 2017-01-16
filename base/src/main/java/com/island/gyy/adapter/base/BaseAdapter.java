package com.island.gyy.adapter.base;


import java.util.ArrayList;
import java.util.List;


import android.content.Context;
import android.support.annotation.LayoutRes;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.island.gyy.base.viewProxy.BaseAdapterViewProxy;

public class BaseAdapter<T> extends android.widget.BaseAdapter {

	private List<T> mLists;
	private Context mContext;
	private int mLayoutId;
	private BaseAdapterViewProxy<T> mAdapterViewDelegate;

	public BaseAdapter() {
		if (null == mLists) {
			mLists = new ArrayList<T>();
		}
	}

	public BaseAdapter(Context context) {
		this();
		setContext(context);
	}

	public BaseAdapter(Context context, @LayoutRes int id) {
		this();
		setLayoutId(id);
		setContext(context);
	}

	public BaseAdapter(Context context, List<T> lists, @LayoutRes int id) {
		this();
		setLists(lists);
		setLayoutId(id);
		setContext(context);
	}

	public BaseAdapter(Context context, List<T> lists, BaseAdapterViewProxy<T> mDelegate) {
		this();
		setLists(lists);
		setContext(context);
		setAdapterViewDelegate(mDelegate);
	}

	public List<T> getLists() {
		return mLists;
	}

	/**
	 * 设置数据
	 *
	 * @author island
	 **/
	public void setLists(List<T> mLists) {
		this.mLists.clear();
		if (mLists == null)
			return;
		this.mLists = mLists;
	}

	public Context getContext() {
		return mContext;
	}

	public void setContext(Context mContext) {
		if (null == mContext)
			throw new NullPointerException("context is null");
		this.mContext = mContext;
	}

	public int getLayoutId() {
		return mLayoutId;
	}

	public void setLayoutId(int mLayoutId) {
		if (0 == mLayoutId)
			throw new NullPointerException("set layoutId");
		this.mLayoutId = mLayoutId;
	}

	@Override
	public int getViewTypeCount() {
		if (mAdapterViewDelegate != null) {
			return mAdapterViewDelegate.getViewCount();
		}
		return super.getViewTypeCount();
	}

	@Override
	public int getItemViewType(int position) {
		// TODO Auto-generated method stub
		if (mAdapterViewDelegate != null) {
			return mAdapterViewDelegate.getViewType(position);
		}
		return super.getItemViewType(position);
	}

	public void setAdapterViewDelegate(BaseAdapterViewProxy<T> mAdapterViewDelegate) {
		if (null == mAdapterViewDelegate)
			throw new NullPointerException("AdapterViewDelegate is null");
		this.mAdapterViewDelegate = mAdapterViewDelegate;
		this.mAdapterViewDelegate.setAdapter(this);
		this.mLayoutId = mAdapterViewDelegate.getLayoutId();
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return mLists.size();
	}

	@Override
	public T getItem(int position) {
		// TODO Auto-generated method stub
		return mLists.get(position);
	}

	public int findItem(T t){
		return mLists.indexOf(t);
	}
	/***
	 * 清除数据
	 * 
	 * @param position
	 *            位置
	 * @param T
	 *            实体
	 */
	public void clearLists() {
		this.mLists.clear();
	}

	/***
	 * 设置某位置的实体
	 * 
	 * @param position
	 *            位置
	 * @param T
	 *            实体
	 */
	public void setItem(int position, T t) {
		if (null == t)
			return;
		if (position > getCount())
			return;
		mLists.set(position, t);
	}

	/***
	 * 添加某位置的实体
	 * 
	 * @param position
	 *            位置
	 * @param T
	 *            实体
	 */
	public void addItem(int position, T t) {
		if (null == t)
			return;
		if (position > getCount())
			return;
		mLists.add(position, t);
	}

	/***
	 * 末尾添加的实体
	 * 
	 * @param T
	 *            实体
	 */
	public void addItem(T t) {
		if (null == t)
			return;
		mLists.add(t);
	}

	/***
	 * 设置某位置的开始的实体被覆盖
	 * 
	 * @param position
	 *            位置
	 * @param T
	 *            实体
	 */
	public void setItems(int position, List<T> list) {
		if (null == list || 0 == list.size())
			return;
		if (position > getCount())
			return;
		mLists.addAll(position, list);
	}

	/***
	 * 某位置的添加多个实体
	 * 
	 * @param position
	 *            位置
	 * @param T
	 *            实体
	 */
	public void addItems(int position, List<T> list) {
		if (null == list || 0 == list.size())
			return;
		if (position > getCount())
			return;
		mLists.addAll(position, list);
	}

	/***
	 * 末尾添加多个实体
	 * 
	 * @param position
	 *            位置
	 * @param T
	 *            实体
	 */
	public void addItems(List<T> list) {
		if (null == list || 0 == list.size())
			return;
		mLists.addAll(list);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		if (getViewTypeCount() ==1) {

			// 单个layout支持
			if (null == convertView) {
				convertView = LayoutInflater.from(mContext).inflate(getLayoutId(), null);
			}
			if (mAdapterViewDelegate != null) {
				mAdapterViewDelegate.showViewData(convertView, this, position);
			} else {
				showViewData(convertView, getItem(position), position);
			}

		} else {
			// 多个layout支持,仅仅支持使用view delegate的重写方式；
			if (null == convertView) {
				convertView = LayoutInflater.from(mContext).inflate(mAdapterViewDelegate.getLayoutId(position), null);
			}
			mAdapterViewDelegate.showViewData(convertView, this, position);
		}

		return convertView;
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
	public void showViewData(View convertView, T item, int position) {
	};

}
