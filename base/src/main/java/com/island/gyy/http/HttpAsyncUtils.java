package com.island.gyy.http;

import com.alibaba.fastjson.JSONObject;
import com.island.gyy.base.activity.BaseFragmentActivity;
import com.island.gyy.base.fragment.BaseFragment;
import com.island.gyy.thread.TaskerExecutor;


/**
 * @Description: 请求网络 JSON
 * @author 罗深志
 * @version V1.0
 */
public abstract class HttpAsyncUtils {
	
	public static void request(HttpTasker tasker) {
		if(tasker == null) {
			throw new NullPointerException("HttpTasker为null");
		}
		HttpCallback jsonCallback = tasker.getHttpCallback();
		if(jsonCallback != null) {
			jsonCallback.onPrepare();
		}
		
//		tasker.addHeader("Authorization", "Bearer " + UserDataUtils.getToken());
		TaskerExecutor.getInstance().execute(tasker);
	}
	
	
	public static HttpTasker request(BaseFragment fragment, String url, JSONObject jsonObject, BaseHttpCallback jsonCallback) {
		if(fragment == null) {
			throw new NullPointerException("Fragment 为null");
		}
		jsonCallback.setFragmentActivity(fragment.getActivity());
		jsonCallback.onPrepare();
		final HttpTasker tasker = HttpTasker.getInstance(url, jsonObject, fragment.getHandler(), jsonCallback);
		if(tasker != null) {
//			tasker.addHeader("Authorization", "Bearer " + UserDataUtils.getToken());
			TaskerExecutor.getInstance().execute(tasker);
		}
		return tasker;
	}
	
	public static HttpTasker request(final BaseFragmentActivity activity, final String url, final JSONObject jsonObject, final BaseHttpCallback jsonCallback) {
		if(activity == null) {
			throw new NullPointerException("activity 为 null");
		}
		jsonCallback.setFragmentActivity(activity);
		jsonCallback.onPrepare();
		final HttpTasker tasker = HttpTasker.getInstance(url, jsonObject, activity.getHandler(), jsonCallback);
		if(tasker != null) {
//			tasker.addHeader("Authorization", "Bearer " + UserDataUtils.getToken());
			TaskerExecutor.getInstance().execute(tasker);
		}
		return tasker;
	}
	
	
	public static HttpTasker request(final BaseFragmentActivity activity, final String url, final String data, final BaseHttpCallback jsonCallback) {
		if(activity == null) {
			throw new NullPointerException("activity 为null");
		}
		jsonCallback.setFragmentActivity(activity);
		jsonCallback.onPrepare();
		final HttpTasker tasker = HttpTasker.getInstance(url, data, activity.getHandler(), jsonCallback);
		if(tasker != null) {
//			tasker.addHeader("Authorization", "Bearer " + UserDataUtils.getToken());
			TaskerExecutor.getInstance().execute(tasker);
		}
		return tasker;
	}
	
	
	public static HttpTasker request(BaseFragment fragment, String url, String data, BaseHttpCallback jsonCallback) {
		if(fragment == null) {
			throw new NullPointerException("Fragment 为null");
		}
		jsonCallback.setFragmentActivity(fragment.getActivity());
		jsonCallback.onPrepare();
		final HttpTasker tasker = HttpTasker.getInstance(url, data, fragment.getHandler(), jsonCallback);
		if(tasker != null) {
//			tasker.addHeader("Authorization", "Bearer " + UserDataUtils.getToken());
			TaskerExecutor.getInstance().execute(tasker);
		}
		return tasker;
	}
	
	
	
}
