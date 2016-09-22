package com.island.gyy.utils;

import android.app.Activity;
import android.content.res.Resources;
import android.graphics.drawable.GradientDrawable;
import android.view.Display;

import com.island.gyy.base.BaseApplication;

public class UiUtils {
	
	/**
	 * 获取指定样式的矩形
	 * @param roundRadius  : 圆角弧度
	 * @param fillColor    : 填充颜色
	 * @param strokeWidth  : 边框宽度
	 * @param strokeColor  : 边框颜色
	 */
	public static GradientDrawable getShapeRectangle(int roundRadius, int fillColor, int strokeWidth, int strokeColor) {
		final GradientDrawable drawable = new GradientDrawable();
		drawable.setShape(GradientDrawable.RECTANGLE);
		drawable.setColor(fillColor);

		if (roundRadius > 0)
			drawable.setCornerRadius(roundRadius);

		if (strokeWidth > 0 || strokeColor > 0)
			drawable.setStroke(strokeWidth, strokeColor);
		return drawable;
	}
	
	/**
	 * 获取指定样式的圆形
	 * @param roundRadius  : 圆角弧度
	 * @param fillColor    : 填充颜色
	 * @param strokeWidth  : 边框宽度
	 * @param strokeColor  : 边框颜色
	 */
	public static GradientDrawable getShapeOval(int width, int height, int fillColor, int strokeWidth, int strokeColor) {
		final GradientDrawable drawable = new GradientDrawable();
		drawable.setShape(GradientDrawable.OVAL);
		drawable.setUseLevel(false);    // 设置是否可拉伸
		drawable.setColor(fillColor);
		drawable.setSize(width, height);
		
		if (strokeWidth > 0 || strokeColor > 0) {
			drawable.setStroke(strokeWidth, strokeColor);
		}
		return drawable;
	}
	
	
	
	public static final int getDimension(Resources resources, int id) {
		return resources.getDimensionPixelSize(id);
	}
	
	
	public static final Display getWindowDisplay(Activity activity) {
		return activity.getWindowManager().getDefaultDisplay();		
	}

	public static Resources getResource() {
		return BaseApplication.getApplication().getResources();
	}

	/** dip转换px */
	public static int dip2px(int dip) {
		final float scale = getResource().getDisplayMetrics().density;
		return (int) (dip * scale + 0.5f);
	}

	/** pxz转换dip */
	public static int px2dip(int px) {
		final float scale = getResource().getDisplayMetrics().density;
		return (int) (px / scale + 0.5f);
	}






}
