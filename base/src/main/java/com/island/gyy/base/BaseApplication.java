package com.island.gyy.base;

import android.app.Application;
import android.content.Context;
import android.os.Handler;

/**
 * 代表当前应用程序
 * 
 * @author itcast
 * 
 */
public abstract class BaseApplication extends Application {

	private static BaseApplication application;

	private static int mainTid;

	@Override
	// 在主线程运行的
	public void onCreate() {
		super.onCreate();
		application = this;
		mainTid = android.os.Process.myTid();
	}


	public static Context getApplication() {
		return application;
	}

}
