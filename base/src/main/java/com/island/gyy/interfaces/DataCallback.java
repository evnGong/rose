package com.island.gyy.interfaces;

/** 
* @Description: JSON 回调接口
* @author BD
* @version V1.0 
*/ 
public interface DataCallback {
	
	/**
	 * 请求成功回调函数(本地和网络数据请求成功都会回调)
	 * @param result  : 请求结果
	 * @param isLoack : 是否为本地数据
	 * @return : 是否继续下一步操作, isLoack = true : 进行网络数据请求, isLoack = flase : 保存数据到本地
	 */
	boolean succeed(String result, boolean isLoack);
	
	/**
	 * 失败回调函数
	 * @param exception
	 */
	void failure(Exception exception);
}
