package com.island.gyy.utils;

import android.os.Handler;

public class AppUtil {

	/**
     * 获取一个Handler(注 : 该方法只能在主线程中调用)
     * @param mCallback
     * @return
     */
    public static Handler getHandler(Handler.Callback callback) {
    	if(!Thread.currentThread().getName().equals("main")) {
    		throw new RuntimeException("非法调用, 该函数只能在主线程中调用");
    	}
    	return callback == null ? new Handler() : new Handler(callback);
    }
}
