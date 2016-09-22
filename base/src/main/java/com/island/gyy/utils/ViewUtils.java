package com.island.gyy.utils;

import android.content.Context;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.WindowManager;

import com.island.gyy.base.BaseApplication;


public class ViewUtils {
	public static void removeParent(View v) {
		
		// 先找到爹 在通过爹去移除孩子
		ViewParent parent = v.getParent();
		// 所有的控件 都有爹 爹一般情况下 就是ViewGoup
		if (parent instanceof ViewGroup) {
			ViewGroup group = (ViewGroup) parent;
			group.removeView(v);
		}
	}

	public static View inflateView(int layoutId) {
		return View.inflate(BaseApplication.getApplication(), layoutId, null);
	}

	@SuppressWarnings("unchecked")
	public static <T extends View> T findViewById(View rootView, int id) {
		return rootView != null ? (T) rootView.findViewById(id) : null;
	}


	/**
	 * 获得屏幕高度
	 */
	public static DisplayMetrics getScreenScale(Context context) {
		WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
		DisplayMetrics outMetrics = new DisplayMetrics();
		wm.getDefaultDisplay().getMetrics(outMetrics);
		return outMetrics;
	}
}