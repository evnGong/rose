package com.island.gyy.base.activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.Window;

import com.island.gyy.base.activity.ActivityCollector;
import com.island.gyy.interfaces.FragmentCallback;


public abstract class BaseFragmentActivity extends FragmentActivity implements FragmentCallback {

	/**
	 * 当前类TAG(类名)
	 */
	public String TAG = this.getClass().getSimpleName();
	
	/**
	 * 当前Activity是否已经退出
	 */
	public boolean isExit;

	/**
	 * 当前Activity显示的视图
	 */
	public View rootView;

	public Handler mHandler;


	@Override
	protected final void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE); // 去除标题
		try {
			init();
			rootView = initView(); // 初始化视图
			setContentView(rootView); // 设置视图
			initViewData(savedInstanceState);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 初始化Activity视图
	 *
	 * @return 显示的视图
	 */
	public abstract View initView();


	protected abstract void initViewData(Bundle savedInstanceState);


	public void init(){
		ActivityCollector.addActivity(TAG, this); // 添加当前类到Activity管理器中
	}

	@Override
	protected void onDestroy() {
		isExit = true; // 标识当前 Activity 已经退出
		super.onDestroy();
	}

	public Handler getHandler() {
		if(mHandler == null){
			mHandler = new Handler(Looper.getMainLooper());
		}
		return mHandler;
	}


}
