package com.island.gyy.thread;

import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;

public class BackgroundHandlerThread {
	/**
	 * 使用内部类，解决线程安全问题
	 * @author moo
	 */
	private static class Holder{
		private static BackgroundHandlerThread _instance = new BackgroundHandlerThread();
		static{
			_instance.init();
		}
	}

	public static BackgroundHandlerThread getInstance(){
		return Holder._instance;
	}
	
	private HandlerThread mHandlerThread;
	private Handler mHandler;

	private void init(){
		mHandlerThread = new HandlerThread(BackgroundHandlerThread.class.getSimpleName());
		mHandlerThread.start();
		
		mHandler = new Handler(mHandlerThread.getLooper()){
			@Override
			public void handleMessage(Message msg) {
			}
		};
	}
	
	public Looper getLooper(){
		return mHandlerThread.getLooper();
	}
	
	public Handler getHandler(){
		return this.mHandler;
	}
}
