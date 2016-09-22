package com.island.gyy.utils;

import android.content.Context;
import android.telephony.TelephonyManager;

/**
 * 设备工具类
 * @author BD
 *
 */
public class DevicesUtils {
	
	/**
	 * 获取设备IMEI
	 *    注意 : 需要 android.permission.READ_PHONE_STATE 权限
	 * @param mContext
	 * @return
	 */
	public static String getIMEI(Context context) {
		return ((TelephonyManager)context.getSystemService(Context.TELEPHONY_SERVICE)).getDeviceId();
	}
}
