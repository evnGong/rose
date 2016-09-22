package com.island.gyy.http;

import java.lang.ref.WeakReference;

import android.support.v4.app.FragmentActivity;

import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.island.gyy.utils.LogUtil;


/** 
 * @Description: 默认回调接口
 * @author 罗深志
 * @version V1.0 
 */ 
public abstract class BaseHttpCallback implements HttpCallback {
	
	private WeakReference<FragmentActivity> mWeakActivity;
	
	
	public final void setFragmentActivity(FragmentActivity fragmentActivity) {
		this.mWeakActivity = new WeakReference<FragmentActivity>(fragmentActivity);
	}
	
	public FragmentActivity getFragmentActivity() {
		return mWeakActivity != null ? mWeakActivity.get() : null;
	}
	
	
	@Override
	public void onPrepare() {
		
	}

	@Override
	public final void onSucceed(String result, int responseCode, byte origin) {
		try {
			LogUtil.response(" \r\n响应数据 : \r\n" + result + "\r\n\r\n");
			final JSONObject jsonObject = ResultCodeFactory.RESULT_CODE_FACTORY.getIntance().decode(getFragmentActivity(), result);
			if(jsonObject == null) {
				onFailure(new RuntimeException("服务器响应数据为空"), responseCode, "服务器响应数据为空");
			}else {
				onSucceed(jsonObject, responseCode, origin);
			}
		} catch (JSONException e) {
			onFailure(e, responseCode, "数据解析失败");
		}
	}
	
	
	public abstract void onSucceed(JSONObject jsonObject, int responseCode, byte origin);

	@Override
	public final void onFailure(Throwable t, int responseCode, String msg, Object...obj) {
		t.printStackTrace();
		onFailure(t, responseCode, msg);
	}
	
	public abstract void onFailure(Throwable t, int responseCode, String msg);

	@Override
	public void onFinish(int responseCode) {
		
	}
}
