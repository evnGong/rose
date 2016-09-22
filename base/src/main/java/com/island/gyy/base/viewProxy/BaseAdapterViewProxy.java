package com.island.gyy.base.viewProxy;



import android.view.View;

import com.island.gyy.adapter.base.BaseAdapter;
import com.island.gyy.adapter.base.IAdapterViewProxy;
import com.island.gyy.utils.ToastUtil;

public class BaseAdapterViewProxy<T> implements IAdapterViewProxy<T> {

	private int mLayoutId;
	protected BaseAdapter<T> adapter;

	public BaseAdapterViewProxy(int layoutId) {
		// TODO Auto-generated constructor stub
		mLayoutId = layoutId;
	}

	public BaseAdapterViewProxy(){
	}
	

	public void setAdapter(BaseAdapter<T> adapter) {
		this.adapter = adapter;
	}

	/***
	 * 默认支持单一个layout
	 * 
	 * 若需要生成多个layout ，
	 * 重写 getviewType与getViewCount
	 * adapter，getLayoutId获取Id；
	 * **/
	public int getLayoutId() {
		return mLayoutId;
	}

	public void showViewData(View v, BaseAdapter<T> adapter, int position) {
		// TODO Auto-generated method stub
	}

	public int getViewType(int pos) {
		return 0;
	}
	
	public int getViewCount(){
		return 1;
	}
	
	public int getLayoutId(int pos) {
		return pos;
	}

	public void toast(String text) {
		ToastUtil.shortToast(adapter.getContext(), text);
	}
}
