package com.island.gyy.utils;

import android.util.Log;

/**
 * 日志工具类
 * @author BD
 */
public class LogUtil {

	/**
	 * 当前模式 : 是否是调试模式
	 */
	public static volatile boolean debugMode = true;

	private static final String LOGUTILSTAG = "BD";

	public static void i(Object msg) {
		if (debugMode) {
			Log.i(LOGUTILSTAG, "" + msg);
		}
	}

	public static void d(Object msg) {
		if (debugMode) {
			Log.d(LOGUTILSTAG, "" + msg);
		}
	}

	public static void e(Object msg) {
		if (debugMode) {
			Log.e(LOGUTILSTAG, "" + msg);
		}
	}

	public static void i(String TAG, Object msg) {
		if (debugMode) {
			Log.i(TAG, "" + msg);
		}
	}

	public static void d(String TAG, Object msg) {
		if (debugMode) {
			Log.d(TAG, "" + msg);
		}
	}

	public static void e(String TAG, Object msg) {
		if (debugMode) {
			Log.e(TAG, "" + msg);
		}
	}
	
	
	public static void json(Object msg) {
		if (debugMode) {
			Log.e("JSON", "" + msg);
		}
	}

	public static void request(Object msg) {
		if (debugMode) {
			Log.i("Request", "" + msg.toString());
		}
	}

	public static void response(Object...msg) {
		if (debugMode) {
			if(msg != null && msg.length > 1) {
				StringBuilder sb = new StringBuilder("");
				for (int i = 0; i < msg.length; i++) sb.append(msg[i]);
				Log.i("Response", sb.toString());
			}else {
				Log.i("Response", "" + (msg.length == 1 ? msg[0] : msg));
			}
		}
	}
	
	public static void other(Object...msg) {
		if (debugMode) {
			Log.d("Other", "" + msg);
		}
	}
	
	public static void http(Object... msg) {
		if(debugMode) {
			if(msg != null && msg.length > 1) {
				StringBuilder sb = new StringBuilder("");
				for (int i = 0; i < msg.length; i++) sb.append(msg[i]);
				Log.i("HTTP", sb.toString());
			}else {
				Log.i("HTTP", "" + (msg.length == 1 ? msg[0] : msg));
			}
		}
	}
	
	
	public static void throwable(Throwable t) {
		if(debugMode && t != null)  Log.e("Throwable",t.getMessage());
	}
	
	/**
	 * 是否开启Debug模式
	 * 
	 * @param debugMode
	 */
	public static void setDebugMode(boolean debugMode) {
		LogUtil.debugMode = debugMode;
	}
	
	

}
