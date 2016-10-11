package com.island.gyy.base.fragment;


import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;

import com.island.gyy.base.presender.IPresender;
import com.island.gyy.base.viewProxy.BaseViewProxy;
import com.island.gyy.utils.FragmentUtil;

public abstract class BaseViewProxyFragment<T extends BaseViewProxy> extends BaseFragment implements IPresender{

	protected T mViewProxy;

	public T getViewProxy() {
		return mViewProxy;
	}


	@Override
	public void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if(null != getArguments()){
			initData(getArguments());
		}
	}

	protected void initData(Bundle arguments){
	}


	@Override
	public final View createView(LayoutInflater inflater, Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		createViewProxy();
		mViewProxy.setPresender(this);
		mViewProxy.create(inflater, null, savedInstanceState);
		return mViewProxy.getRootView();
	}

	@Override
	protected void initView(View view, Bundle savedInstanceState) {
		mViewProxy.initWidget();
		mViewProxy.initListener();
		mViewProxy.initData(savedInstanceState);
		_init();
	}

	protected void _init() {
	}

	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
	}

	@Override
	public void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		mViewProxy.onPause();
	}

	public void popBack() {
		mViewProxy.hideSoftInputMethod();
		FragmentUtil.popBackImmediate(getActivity());
	}

	public <T extends FragmentActivity> T getActivityClass(Class<T> tClass){
		return (T)getActivity();
	}

}
