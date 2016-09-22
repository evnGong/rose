package com.island.gyy.http;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.text.TextUtils;


import com.island.gyy.utils.ToastUtil;

import java.util.Locale;

public class NetConfigUtils {
	
	/**
	 * 检测网络是否可用
	 * @return
	 */
	public static boolean isNetworkConnected(Context context, String msg) {
		ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo ni = cm.getActiveNetworkInfo();
		boolean isNetworkConn = ni != null && ni.isConnectedOrConnecting();
		if (msg != null && !isNetworkConn) {
			ToastUtil.shortToast(context, msg);
		}
		return isNetworkConn;
	}

	/**
	 * 判断网络类型
	 * @param mContext
	 * @return : 0 没有网络, 1 WIFI网络, 2 WAP网络, 3 NET网络
	 */
	public static int getNetworkType(Context context) {
		int netType = 0;
		ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
		
		if (networkInfo == null) {
			return netType;
		}
		int nType = networkInfo.getType();
		if (nType == ConnectivityManager.TYPE_MOBILE) {
			String extraInfo = networkInfo.getExtraInfo();
			if (!TextUtils.isEmpty(extraInfo)) {
				if (extraInfo.toLowerCase(Locale.getDefault()).equals("cmnet")) {
					netType = 0x03;
				} else {
					netType = 0x02;
				}
			}
		} else if (nType == ConnectivityManager.TYPE_WIFI) {
			netType = 0x01;
		}
		return netType;
	}
	
	

}
