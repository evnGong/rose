package com.island.gyy.utils;

import android.graphics.drawable.BitmapDrawable;
import android.view.View;
import android.widget.PopupWindow;

/**
 * 弹出窗口工具类
 * 
 * @author BD
 * 
 */
public class PopupWindowUtil {

	/**
	 * 在指定的控件下显示窗口
	 * 
	 * @param rootView  : 显示的内容
	 * @param width     : 窗口宽
	 * @param height    : 窗口高
	 * @param childView : 指定的控件
	 * @param x         : X 轴的偏移量
	 * @param y         : Y 轴的偏移量
	 * @return
	 */
	@SuppressWarnings("deprecation")
	public static PopupWindow showAsDropDown(View rootView, View childView, int x, int y) {
		childView.measure(0, 0); // 手动测量后, getMeasuredWidth()和getMeasuredHeight()才会起效
		PopupWindow popupWindow = new PopupWindow(rootView, childView.getMeasuredWidth(), 300);
		popupWindow.setBackgroundDrawable(new BitmapDrawable());
		popupWindow.setFocusable(true); // 失去焦点隐藏
		popupWindow.setOutsideTouchable(true); // 支持返回键删除
		popupWindow.showAsDropDown(childView, x, y);
		return popupWindow;
	}

	
	/**
	 * 显示在屏幕的指定位置
	 * @param rootView : 显示视图
	 * @param parent   : 标识控件
	 * @param gravity  : 位置
	 * @param x        : X 轴的偏移量
	 * @param y        : Y 轴的偏移量
	 * @return
	 */
	@SuppressWarnings("deprecation")
	public static PopupWindow showAtLocation(View rootView, View parent, int gravity, int x, int y) {
		parent.measure(0, 0); // 手动测量后, getMeasuredWidth()和getMeasuredHeight()才会起效
		PopupWindow popupWindow = new PopupWindow(rootView, parent.getMeasuredWidth(), parent.getMeasuredHeight());
		popupWindow.setBackgroundDrawable(new BitmapDrawable());
		popupWindow.setFocusable(true); // 失去焦点隐藏
		popupWindow.setOutsideTouchable(true); // 支持返回键删除
		popupWindow.showAtLocation(parent, gravity, x, y);
		return popupWindow;
	}

	/**
	 * 关闭弹出窗口
	 * 
	 * @param popupWindow
	 */
	public static void dismiss(PopupWindow popupWindow) {
		if (popupWindow != null && popupWindow.isShowing()) {
			popupWindow.dismiss();
		}
	}
}
