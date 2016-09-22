package com.island.gyy.interfaces;

import android.graphics.Bitmap;

/** 
* @Description: 获取位图的回调接口 
* @author 罗深志
* @date 2016-1-15 上午10:37:52 
* @version V1.0 
*/ 
public interface BitmapCallback {
	
	/**
	 * 成功回调函数
	 * @param bitmap   : 请求到的位图
	 * @param position : true 表示为本地数据. false 表示为网络数据
	 * @return         : 是否进行下一步操作
	 */
	boolean succeed(Bitmap bitmap, boolean isNetwork);
	
	/**
	 * 失败回调函数
	 * @param throwable    : 错误或异常
	 * @param responseCode : 响应码
	 * @param result       : 请求结果
	 */
	void failure(Throwable throwable, int responseCode, String msg);
}
