package com.island.gyy.base.activity;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.Window;


import com.island.gyy.base.activity.ActivityCollector;
import com.island.gyy.interfaces.FragmentCallback;

public abstract class BaseActivity extends Activity implements FragmentCallback {

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


	@Override
	protected final void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE); // 去除标题
		try {
			ActivityCollector.addActivity(TAG, this);    // 添加当前类到Activity管理器中
			init(); // 初始化数据
			rootView = initView(savedInstanceState); // 初始化视图
			setContentView(rootView); // 设置视图
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/**
	 * 初始化方法
	 */
	protected final void init() {
	}

	@Override
	protected void onDestroy() {
		isExit = true;                        // 标识当前 Activity 已经退出
		super.onDestroy();
	}

	/**
	 * 初始化Activity视图
	 * 
	 * @return 显示的视图
	 */
	public abstract View initView(Bundle savedInstanceState);
}

