package com.island.gyy.http;

import android.support.v4.app.FragmentActivity;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;



/**
 * @Description: 请求响应解析器
 * @author 罗深志
 * @version V1.0
 */
public interface ResultCodeCallBack {
	 JSONObject decode(final FragmentActivity fragmentActivity, String text) throws JSONException;
//	{
//		try {
//			final JSONObject jsonObject = JSON.parseObject(text);
//
//			// 解析服务器返回的异常码
//			final String code = jsonObject.getString(Constant.code);
//			if (!TextUtils.isEmpty(code) && fragmentActivity != null) {
//				switch (code) {
//				case ErrorCode.INVILID_TOKEN:
//					LogUtil.e(text);
////					DialogFractory.showUserLogout(fragmentActivity);
//					return null;
//
//				case ErrorCode.NO_LOGIN:
//					LogUtil.e(text);
////					UserDataUtils.setPassword(fragmentActivity, "", true);
////					FragmentUtil.replace(fragmentActivity, new UserLogin());
//					return null;
//
//				case ErrorCode.ILLEGAL_ACCESS:
//					LogUtil.e(text);
////					DialogFractory.showUserLogout(fragmentActivity);
//					return null;
//				}
//			}
//			return jsonObject;
//		} catch (JSONException e) {
//			throw e;
//		}
//	}

}
