package com.island.gyy.interfaces;

import android.os.Bundle;

/**
 * 任务执行结果回调接口
 * 
 * @author BD
 * 
 */
public interface ExecutorResult {

	/**
	 * 成功回调方法
	 * 
	 * @param obj
	 */
	public void onSuccess(Object... obj);

	/**
	 * 失败回调方法
	 * 
	 * @param throwable
	 * @param bundle
	 */
	public void onFailure(Throwable throwable, Bundle... bundle);

}
