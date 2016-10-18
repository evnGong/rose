package com.island.gyy.http;

import android.accounts.NetworkErrorException;
import android.os.Handler;

import com.alibaba.fastjson.JSONObject;
import com.island.gyy.utils.FileUtil;
import com.island.gyy.utils.LogUtil;

import java.io.OutputStream;
import java.lang.ref.WeakReference;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.MalformedURLException;
import java.net.Proxy;
import java.net.URL;
import java.net.URLConnection;
import java.util.zip.GZIPInputStream;

public class HttpTasker implements Runnable {
	
	@SuppressWarnings("unused")
	private int mId;
	
	private String mUrl;
	
	private String mData;
	
	private WeakReference<Handler> mWeakHandler;
	
	private HttpCallback mHttpCallback;
	
	private HttpURLConnection mConnection;
	
	private int mResponseCode;
	
	
	public static final HttpTasker getInstance(String url, JSONObject jsonObject, Handler handler, HttpCallback httpCallback) {
		return getInstance(url, jsonToString(jsonObject), handler, httpCallback);
	}
	
	public static final HttpTasker getInstance(String url, String data, Handler handler, HttpCallback httpCallback) {
		return getInstance(0, url, data, handler, httpCallback);
	}
	
	public static HttpTasker getInstance(int id, String url, String data, Handler handler, HttpCallback httpCallback) {
		try {
			return new HttpTasker(id, url, data, handler, httpCallback);
		} catch (Exception e) {
			httpCallback.onFailure(e, -1, e.getMessage());
			httpCallback.onFinish(-1);
		}
		return null;
	}
	
	
	public HttpTasker(String url, String data, Handler handler, HttpCallback httpCallback) throws Exception {
		this(0, url, data, handler, httpCallback);
	}
	
	
	public HttpTasker(int id, String url, String data, Handler handler, HttpCallback httpCallback) throws Exception {
		this.mId = id;
		this.mUrl = url;
		this.mData = data;
		this.mWeakHandler  = new WeakReference<Handler>(handler);
		this.mHttpCallback = httpCallback;
		this.mConnection   = (HttpURLConnection) new URL(mUrl).openConnection();
//		Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress("192.168.1.124", 7779));  // TODO 创建代理, 调试使用
//		Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress("192.168.1.113", 8888));  // TODO 创建代理, 调试使用
//		this.mConnection   = (HttpURLConnection) new URL(mUrl).openConnection(proxy);
	}
	
	public final void addHeader(String field, String newValue) {
		mConnection.addRequestProperty(field, newValue);
	}
	
	public final URLConnection getUrlConnection() {
		return mConnection;
	}
	

	
	@Override
	public void run() {
		OutputStream outputStream = null;
		try {
			byte[] data = mData.getBytes("UTF-8");
			mConnection.setRequestMethod("POST");
			mConnection.setConnectTimeout(10000);
			mConnection.setReadTimeout(5000);
			mConnection.setRequestProperty("Accept-Encoding", "gzip");    // GZIP 压缩
			mConnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");  // 表单
			mConnection.setRequestProperty("Content-Length", String.valueOf(data.length));                       // 数据长度
			mConnection.setRequestProperty("Connection", "Keep-Alive");   // 长链接
			mConnection.setDoInput(true);    // 允许输入输出
			mConnection.setDoOutput(true);
			mConnection.setUseCaches(false); // 不允许缓存
			mConnection.connect();
			
			outputStream = mConnection.getOutputStream();
			outputStream.write(data);
			outputStream.flush();
			outputStream.close();
			
			mResponseCode = mConnection.getResponseCode();
			LogUtil.http("\r\nresponseCode = ", mResponseCode, "\r\nURL = ", mUrl, "\r\nRequestData : ", mData, "\r\n");
			
			if(mResponseCode >= 200 && mResponseCode < 300) {
				String encoding = mConnection.getContentEncoding();   // 判断响应编码, 是否对数据进行过GZIP压缩
				sendSucceedMsg(FileUtil.inputToString(encoding != null && encoding.length() > 0 && encoding.toLowerCase().contains("gzip")
									? new GZIPInputStream(mConnection.getInputStream()) : mConnection.getInputStream(), "UTF-8"));
				
//				if(encoding != null && encoding.toLowerCase().contains("gzip")) {
//					sendSucceedMsg(FileUtil.inputToString(new GZIPInputStream(mConnection.getInputStream()), "UTF-8"));
//					o
//				}else {
//					sendSucceedMsg(FileUtil.inputToString(mConnection.getInputStream(), "UTF-8"));
//				}
			}else if(mResponseCode >= 400 && mResponseCode < 500){
				sendFailureMsg(new MalformedURLException("请求地址不存在"), "数据获取失败");
				
			}else if(mResponseCode >= 500 && mResponseCode < 600) {
				sendFailureMsg(new NetworkErrorException("服务器异常"), "服务器异常");
			}
		} catch (Exception e) {
			e.printStackTrace();
			sendFailureMsg(e, "数据获取失败");
		}finally {
			FileUtil.closeStream(outputStream);
			if(mConnection != null) mConnection.disconnect();
			sendExecuteFinishMsg();
		}
	}


	/**
	 * 发送成功消息
	 * @param result
	 */
	public void sendSucceedMsg(final String result) {
		if(mWeakHandler != null && mWeakHandler.get() != null) {
			mWeakHandler.get().post(new Runnable() {
				@Override
				public void run() {
					if(mHttpCallback != null) {
						if(result != null && result.length() > 0) {
							mHttpCallback.onSucceed(result, mResponseCode, HttpCallback.NETWORK);
						}else {
							mHttpCallback.onFailure(new NullPointerException("JSON 数据获取失败"), mResponseCode, "数据获取失败");
						}
					}
				}
			});
		}
	}
	

	/**
	 * 发送失败消息
	 * @param t
	 */
	private final void sendFailureMsg(final Throwable t, final String msg) {
		if(mWeakHandler != null && mWeakHandler.get() != null) {
			mWeakHandler.get().post(new Runnable() {
				@Override
				public void run() {
					if(mHttpCallback != null) {
						mHttpCallback.onFailure(t, mResponseCode, msg);
					}
				}
			});
		}
	}
	
	
	/**
	 * 发送执行完毕消息
	 */
	private void sendExecuteFinishMsg() {
		if(mWeakHandler != null && mWeakHandler.get() != null) {
			mWeakHandler.get().post(new Runnable() {
				@Override
				public void run() {
					if(mHttpCallback != null) {
						mHttpCallback.onFinish(mResponseCode);
					}
				}
			});
		}
	}
	
	
	/**
	 * JSONObject 转 String  TODO 复合类型JSONObject不知道能否转换成功
	 * @param jsonObject
	 * @return
	 */
	public static String jsonToString(JSONObject jsonObject) {
		final StringBuilder sb = new StringBuilder();
		try {
			for(String key : jsonObject.keySet()) {
				sb.append(key).append("=").append(jsonObject.get(key)).append("&");
			}
			sb.deleteCharAt(sb.length() - 1);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return sb.toString();
	}

	
	
	
	public final HttpCallback getHttpCallback() {
		return mHttpCallback;
	}
}
