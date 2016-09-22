package com.island.gyy.base.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.island.gyy.interfaces.FragmentCallback;

public abstract class BaseFragment extends Fragment {

	/**
	 * 当前类TAG(类名)
	 */
	public String TAG = this.getClass().getSimpleName();
	
	/**
	 * 该Fragment绑定的Activity
	 */
	public volatile Activity activity;

	/**
	 * Fragment 显示视图
	 */
	protected View rootView;

	/**
	 * 当前Fragment是否已经退出标记
	 */
	public volatile boolean isFragmentExit = false;

	public Handler mHandler;

	@Override
	@SuppressWarnings("deprecation")
	public final void onAttach(Activity activity) {
		if (activity instanceof FragmentCallback) {
			this.activity = activity;
		} else {
			new Exception("该Activity必须实现Fragment的回调接口 : FragmentCallback");
		}
		super.onAttach(activity);
	}

	@Override
	public final View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		rootView = createView(inflater, savedInstanceState);
		return rootView;
	}

	protected abstract View createView(LayoutInflater inflater, Bundle savedInstanceState);

	@Override
	public final void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		initView(view,savedInstanceState);
	}

	protected abstract void initView(View view, Bundle savedInstanceState);

	@Override
	public void setUserVisibleHint(boolean isVisibleToUser) {
		super.setUserVisibleHint(isVisibleToUser);
		if(isVisibleToUser) isFragmentExit = false; 
	}

	@Override
	public void onDestroyView() {
		isFragmentExit = true;
		super.onDestroyView();
	}

	
	public View getRootView() {
		return rootView;
	}

	public Handler getHandler() {
		if(mHandler == null){
			mHandler= new Handler(Looper.getMainLooper());
		}

		return mHandler;
	}
}
