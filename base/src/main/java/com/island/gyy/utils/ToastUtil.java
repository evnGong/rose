package com.island.gyy.utils;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

/**
 * 吐司工具类
 * 
 * @author BD
 * 
 */
public class ToastUtil {

	
	public static Toast toast;
	
	/**
	 * 显示长时间吐司
	 * @param mContext
	 * @param text
	 */
	public static void shortToast(Context context, String text) {
		cancelToast(toast);
		if(!TextUtils.isEmpty(text)) {
			toast = Toast.makeText(context, text, Toast.LENGTH_SHORT);
			toast.show();
		}
	}

	/**
	 * 显示短时间吐司
	 * @param mContext
	 * @param text
	 */
	public static void longToast(Context context, String text) {
		cancelToast(toast);
		if(!TextUtils.isEmpty(text)) {
			toast = Toast.makeText(context, text, Toast.LENGTH_LONG);
			toast.show();
		}
	}

	/**
	 * 显示自定义吐司
	 * @param mContext
	 * @param resView : 显示的视图
	 * @param resText : 显示的文本
	 */
	public static void diyToast(Context context, int resView, int resText) {
		cancelToast(toast);
		Toast toast = new Toast(context);
		toast.setView(View.inflate(context, resView, null));
		toast.setDuration(Toast.LENGTH_SHORT);
		toast.setText(resText);
		toast.show();
	}
	
	public static void cancelToast(Toast toast) {
		if(toast != null)
			toast.cancel();
	}
}
