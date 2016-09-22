package com.island.gyy.http;

/**
 * @Description: JSON 请求回调接口
 * @author 罗深志
 * @version V1.0
 */
public interface HttpCallback {

	byte NETWORK = 3;

	void onPrepare();
	void onSucceed(String result, int responseCode, byte origin);

	void onFailure(Throwable t, int responseCode, String msg, Object... obj);
	
	void onFinish(int responseCode);
}
