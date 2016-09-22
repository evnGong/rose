package com.island.gyy.base;

import android.os.Handler;
import android.os.Message;

import java.lang.ref.WeakReference;

/**
 * 防止内存泄露的 Handler
 * @Description: 由于在使用 Handler 时可能会存在耗时或异步操作，从而导致内存泄露
 * @author 罗深志
 * @version V1.0
 */ 
public class BaseHandler<T> extends Handler {

	private WeakReference<T> mWeakReference;
	private WeakReference<Callback> mWeakCallback;

	/**
	 * 默认构造
	 * @param t : 需要耗时或异步之后操作的对象，如 Activity、Fragment等
	 * @param callback : Handler 回调接口
	 */
	public BaseHandler(T t, Callback callback) {
		this.mWeakReference = new WeakReference<T>(t);
		this.mWeakCallback  = new WeakReference<Callback>(callback);
	}

	@Override
	public void handleMessage(Message msg) {
		if (mWeakReference != null && mWeakReference.get() != null && mWeakCallback != null && mWeakCallback.get() != null) {
			mWeakCallback.get().handleMessage(msg);
		}
	}
}
