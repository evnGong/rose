package com.island.gyy.adapter.base;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.island.gyy.adapter.viewholder.RecycleViewHolder;

public class BaseRecycleViewAdapter<T> extends RecyclerView.Adapter<RecycleViewHolder> {

	private List<T> mLists;
	private Context mContext;
	private int mLayout;
	private IAdapterRecycleViewDelegate<T> mAdapterViewDelegate;

	public BaseRecycleViewAdapter() {
		if (null == mLists) {
			mLists = new ArrayList<T>();
		}
	}

	public BaseRecycleViewAdapter(Context context, List<T> lists, @LayoutRes int id) {
		this();
		setLayoutId(id);
		setLists(lists);
		setContext(context);
	}

	public BaseRecycleViewAdapter(Context context, List<T> lists, IAdapterRecycleViewDelegate mDelegate) {
		this();
		setLists(lists);
		setContext(context);
		setAdapterViewDelegate(mDelegate);
	}

	public void setLists(List<T> mLists) {
		this.mLists.clear();
		if (null == mLists)
			return;
		this.mLists.addAll(mLists);
	}

	public List<T> getLists() {
		return mLists;
	}

	public void setContext(Context mContext) {
		if (null == mContext)
			throw new NullPointerException("context is null");
		this.mContext = mContext;
	}

	public void setAdapterViewDelegate(IAdapterRecycleViewDelegate mAdapterViewDelegate) {
		if (null == mAdapterViewDelegate)
			throw new NullPointerException("AdapterViewDelegate is null");
		this.mAdapterViewDelegate = mAdapterViewDelegate;
		setLayoutId(mAdapterViewDelegate.getLayoutId());
	}

	protected Context getContext() {
		return mContext;
	}

	public void setLayoutId(int mLayoutId) {
		if (0 == mLayoutId)
			throw new NullPointerException("set layoutId");
		this.mLayout = mLayoutId;
	}

	public T getItem(int position) {
		// TODO Auto-generated method stub
		return mLists.get(position);
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

	public void setItem(int position, T t) {
		if (null == t)
			return;
		if (position > getItemCount())
			return;
		mLists.set(position, t);
	}

	public void addItem(int position, T t) {
		if (null == t)
			return;
		if (position > getItemCount())
			return;
		mLists.add(position, t);
	}

	public void addItem(T t) {
		if (null == t)
			return;
		mLists.add(t);
	}

	public void setItems(int position, List<T> list) {
		if (null == list || 0 == list.size())
			return;
		if (position > getItemCount())
			return;
		mLists.addAll(position, list);
	}

	public void addItems(int position, List<T> list) {
		if (null == list || 0 == list.size())
			return;
		if (position > getItemCount())
			return;
		mLists.addAll(position, list);
	}

	public void addItems(List<T> list) {
		if (null == list || 0 == list.size())
			return;
		mLists.addAll(list);
	}

	@Override
	public int getItemCount() {
		// TODO Auto-generated method stub
		return mLists.size();
	}

	@Override
	public RecycleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
		// TODO Auto-generated method stub
		View view = LayoutInflater.from(mContext).inflate(mLayout, null);
		RecycleViewHolder rHolder = new RecycleViewHolder(view);
		return rHolder;
	}

	@Override
	public final void onBindViewHolder(RecycleViewHolder viewHolder, int position) {
		// TODO Auto-generated method stub

		if (mAdapterViewDelegate != null) {
			mAdapterViewDelegate.showViewData(viewHolder, this, position);
		} else {
			showViewData(viewHolder, position);
		}
	}

	/**
	 * 数据实体绑定 建议使用，使用ViewHolder{@link com.hnfresh.adapter.viewholder.ViewHolder}
	 * 绑定数据与view {@code
	 *      //get 方法已经使用 T extent View 泛型
	 * 		TextView t =	ViewHolder.get(convertView,R.id.textview);
	 * } 简单使用BaseRecycleViewAdapter。不超过5代码设置view请复写此方法
	 * 复杂的view设置请重写{@link com.hnfresh.base.IAdapterRecycleViewDelegate}}
	 * 
	 * @param
	 **/
	public void showViewData(RecycleViewHolder viewHolder, int postion) {

	}

}
