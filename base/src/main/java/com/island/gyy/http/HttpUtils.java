package com.island.gyy.http;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.text.TextUtils;

import com.island.gyy.utils.FileUtil;
import com.island.gyy.utils.LogUtil;
import com.island.gyy.utils.ToastUtil;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;

/**
 * 网络访问工具类
 * 
 * @author BD 注意所需权限 : <uses-permission
 *         android:name="android.permission.INTERNET" />
 */
public class HttpUtils {

	/**
	 * 边界值
	 */
	private static final String BOUNDARY = "--WebKitFormBoundaryT1HoybnYeFOGFlBR";

	/**
	 * 从网络获取字符串数据
	 * 
	 * @param url
	 * @param params
	 * @return
	 * @throws Exception 
	 */
	public static String httpGetString(String url, Map<String, String> params) throws Exception {
		InputStream is = httpGet(url, params);
		if (is == null)
			return null;

		final String text = FileUtil.inputToString(is, null);
		FileUtil.closeStream(is);
		return text;
	}
	
	
	/**
	 * 使用 get 方式提交请求
	 * @param url   : 请求地址
	 * @param data  : 数据
	 * @return      : 服回的结果或null
	 * @throws Exception 
	 */
	public static InputStream httpGet(String url, Map<String, String> params) throws Exception {
		
		HttpURLConnection conn = null;
		int responseCode = -1;
		url = assemblyURL(url, params);
		conn = (HttpURLConnection) new URL(url).openConnection();
		conn.setRequestMethod("GET");
		conn.setConnectTimeout(10000);
		conn.setReadTimeout(5000);

		responseCode = conn.getResponseCode();
		LogUtil.response("responseCode = " + responseCode);
		final InputStream inputStream = responseCode == 200 ? conn.getInputStream() : null;
		if (conn != null) conn.disconnect();
		return inputStream;
	}
	

	/**
	 * POST 请求提交普通文本数据
	 * @param url : 请求地址
	 * @param key : 键
	 * @param values : 值
	 * @return
	 * @throws Exception
	 */
	public static String httpPost(final String url, String key, String values) throws Exception {
		
		if (!judgeUrl(url)) {
			throw new NullPointerException("URL 为 null 或空值");
		}
		
		if (key == null || key.length() < 1) {
			throw new NullPointerException("key 为 null 或空值");
		}
		
		if (values == null || values.length() < 1) {
			throw new NullPointerException("values 为 null 或空值");
		}
		
		

		int responseCode = 0;
		HttpURLConnection conn = null;
		OutputStream outputStream = null;
		StringBuilder sb = new StringBuilder();

//		 设置请求实体
		sb.append("--" + BOUNDARY + "\r\n");
		sb.append("Content-Disposition: form-data; name=\"" + key + "\"\r\n");
		sb.append("Content-Type: text/plain; charset=UTF-8\r\n"); // 普通文本
		sb.append("Content-Transfer-Encoding: 8bit\r\n"); // 消息请求(request)和响应(response)所附带的实体对象(entity)的传输形式
		sb.append("\r\n\r\n");
		sb.append(values + "\r\n");
		sb.append("--" + BOUNDARY + "--\r\n");

		LogUtil.request(sb.toString());
		conn = (HttpURLConnection) new URL(url).openConnection();
		// 设置请求头
		conn.setRequestMethod("POST");
		conn.setConnectTimeout(10000);
		conn.setReadTimeout(5000);
		conn.setRequestProperty("Accept-Encoding", "gzip");  // GZIP 压缩
		conn.setRequestProperty("Content-Length", String.valueOf(sb.toString().length()));     // 数据长度
		conn.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + BOUNDARY);  // 边界值
		conn.setRequestProperty("connection", "Keep-Alive"); // 长链接
		conn.setRequestProperty("Charsert", "UTF-8");        // 编码
		conn.setDoInput(true);    // 允许输入输出
		conn.setDoOutput(true);
		conn.setUseCaches(false); // 不允许缓存
		conn.connect();

		outputStream = conn.getOutputStream();
		outputStream.write(sb.toString().getBytes("UTF-8"));
		outputStream.close();

		responseCode = conn.getResponseCode();
		LogUtil.response("responseCode = " + responseCode);
		final String result = responseCode == 200 ? FileUtil.inputToString(conn.getInputStream(), null) : null;
		if(conn != null) conn.disconnect();
		return result;
	}
	
	
	/**
	 * POST 请求提交普通文本数据
	 * @param url   : 请求地址
	 * @param datas : 数据
	 * @return
	 * @throws Exception
	 */
	public static String httpPost(final String url, Map<String, String> datas) throws Exception {

		if (!judgeUrl(url)) {
			throw new NullPointerException("URL 为 null 或空值");
		}
		if (!judgeData(datas)) {
			throw new NullPointerException("datas 为 null 或没有数据");
		}

		int responseCode = 0;
		HttpURLConnection conn = null;
		OutputStream outputStream = null;
		StringBuilder sb = new StringBuilder();

		for (String key : datas.keySet()) {
			// 设置请求实体
			sb.append("--" + BOUNDARY + "\r\n");
			sb.append("Content-Disposition: form-data; name=\"" + key + "\"\r\n");
			sb.append("Content-Type: text/plain; charset=UTF-8\r\n"); // 普通文本
			sb.append("Content-Transfer-Encoding: 8bit\r\n"); // 消息请求(request)和响应(response)所附带的实体对象(entity)的传输形式
			sb.append("\r\n\r\n");
			sb.append(datas.get(key) + "\r\n");
		}
		sb.append("--" + BOUNDARY + "--\r\n");

		LogUtil.request(sb.toString());
		conn = (HttpURLConnection) new URL(url).openConnection();
		// 设置请求头
		conn.setRequestMethod("POST");
		conn.setConnectTimeout(10000);
		conn.setReadTimeout(5000);
		conn.setRequestProperty("Accept-Encoding", "gzip");  // GZIP 压缩
		conn.setRequestProperty("Content-Length", String.valueOf(sb.toString().length()));     // 数据长度
		conn.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + BOUNDARY);  // 边界值
		conn.setRequestProperty("connection", "Keep-Alive"); // 长链接
		conn.setRequestProperty("Charsert", "UTF-8");        // 编码
		conn.setDoInput(true);    // 允许输入输出
		conn.setDoOutput(true);
		conn.setUseCaches(false); // 不允许缓存
		conn.connect();

		outputStream = conn.getOutputStream();
		outputStream.write(sb.toString().getBytes("UTF-8"));
		outputStream.close();

		responseCode = conn.getResponseCode();
		LogUtil.response("responseCode = " + responseCode);
		final String result = responseCode == 200 ? FileUtil.inputToString(conn.getInputStream(), null) : null;
		if(conn != null) conn.disconnect();
		return result;
	}

	/**
	 * POST 请求上传普通数据和文件
	 * @param url      : 请求地址
	 * @param textData : 数据
	 * @param uploadFiles  : 上传的文件
	 * @return
	 * @throws Exception 
	 */
	public static String httpPost(final String url, Map<String, String> textData, Map<String, File> uploadFiles) throws Exception {
		if (!judgeUrl(url)) {
			throw new NullPointerException("URL 为 null 或空值");
		}
		if (!judgeData(textData) && !judgeData(uploadFiles)) {
			throw new NullPointerException(
					"textData 和  uploadFiles 为 null或没有数据");
		}

		int length = 0;
		int responseCode = 0;
		HttpURLConnection conn = null;
		OutputStream outputStream = null;
		StringBuilder sb = new StringBuilder();
		Entry<String, File> entry = null;
		Iterator<Entry<String, File>> iterator = null;

		if (judgeData(textData)) {
			for (String key : textData.keySet()) {
				// 设置请求实体
				sb.append("--" + BOUNDARY + "\r\n");
				sb.append("Content-Disposition: form-data; name=\"" + key + "\"\r\n");
				sb.append("Content-Type: text/plain; charset=UTF-8\r\n"); // 普通文本
				sb.append("Content-Transfer-Encoding: 8bit\r\n"); // 消息请求(request)和响应(response)所附带的实体对象(entity)的传输形式
				sb.append("\r\n");
				sb.append("\r\n");
				sb.append(textData.get(key) + "\r\n");
			}
		}

		if (judgeData(uploadFiles)) {
			iterator = uploadFiles.entrySet().iterator();

			StringBuilder builder = new StringBuilder();
			while (iterator.hasNext()) {
				entry = iterator.next();
				builder.append("--")
						.append(BOUNDARY)
						.append("\r\n")
						.append("Content-Disposition: form-data; name=\"" + "file" + "\"; filename=\"" + entry.getKey() + "\"" + "\r\n")
						.append("Content-Type:application/octet-stream" + "\r\n") // 数据为字节流
						.append("\r\n\r\n") // 请求实体与数据之间的换行
						.append("\r\n"); // 数据写完后的回车换行
				length += entry.getValue().length(); // 文件长度
			}
			length += builder.toString().getBytes().length; // 请求参数长度
		}

		byte[] headerInfo = sb.toString().getBytes("UTF-8");
		byte[] endInfo = ("--" + BOUNDARY + "--\r\n").getBytes("UTF-8");
		length += headerInfo.length + endInfo.length;

		LogUtil.request("headerInfo = " + headerInfo.length);
		LogUtil.request("endInfo = " + endInfo.length);
		LogUtil.request("length = " + length);
		// 设置请求头
		conn = (HttpURLConnection) new URL(url).openConnection();
		conn.setRequestMethod("POST");
		conn.setConnectTimeout(10000);
		conn.setReadTimeout(5000);
		conn.setRequestProperty("Accept-Encoding", "gzip"); // GZIP 压缩
		conn.setRequestProperty("Content-Length", String.valueOf(length));                    // 数据长度
		conn.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + BOUNDARY); // 边界值
		conn.setRequestProperty("connection", "Keep-Alive");  // 长链接
		conn.setRequestProperty("Charsert", "UTF-8");         // 编码
		conn.setDoInput(true); // 允许输入输出
		conn.setDoOutput(true);
		conn.setUseCaches(false); // 不允许缓存
		conn.connect();

		outputStream = conn.getOutputStream();
		outputStream.write(headerInfo);

		int len = 0;
		byte[] buffer = new byte[8192];
		FileInputStream is = null;
		if (iterator != null) {
			iterator = uploadFiles.entrySet().iterator();
			while (iterator.hasNext()) {
				entry = iterator.next();
				outputStream.write(new StringBuilder()
						.append("--")
						.append(BOUNDARY)
						.append("\r\n")
						.append("Content-Disposition: form-data; name=\"" + "file" + "\"; filename=\"" + entry.getKey() + "\"" + "\r\n")
						.append("Content-Type:application/octet-stream" + "\r\n") // 数据为字节流
						.append("\r\n\r\n").toString().getBytes("UTF-8"));

				is = new FileInputStream(entry.getValue());
				while ((len = is.read(buffer)) != -1) {
					outputStream.write(buffer, 0, len);
				}
				outputStream.write("\r\n".getBytes()); // 数据输出完后的换行
				is.close();
			}
		}
		outputStream.write(endInfo); // 写结束符
		outputStream.flush();
		outputStream.close();

		responseCode = conn.getResponseCode();
		LogUtil.response("responseCode = " + responseCode);
		final String result = responseCode == 200 ? FileUtil.inputToString(conn.getInputStream(), null) : null;
		if(conn != null) conn.disconnect();
		return result;
	}

	/**
	 * 判断 URL 是否为空
	 * 
	 * @param url
	 * @return : 数据是否合法
	 */
	private static boolean judgeUrl(String url) {
		return url == null || url.length() == 0 ? false : true;
	}

	/**
	 * 判断数据是否为空
	 * 
	 * @param datas
	 * @return : 数据是否合法
	 */
	private static boolean judgeData(Map<String, ?> datas) {
		return datas == null || datas.size() == 0 ? false : true;
	}

	/**
	 * 拼装URL
	 * @param url    : 原 URL 地址
	 * @param params : 数据参数
	 * @return
	 */
	public static String assemblyURL(String url, Map<String, String> params) {
		if (params != null && params.size() > 0) {
			StringBuilder sb = new StringBuilder().append(url);
			if (!url.contains("?")) {
				sb.append("?");
			} else if (!url.endsWith("&")) {
				sb.append("&");
			}
			for (Entry<String, String> entry : params.entrySet()) {
				sb.append(entry.getKey()).append("=").append(entry.getValue())
						.append("&");
			}
			sb.deleteCharAt(sb.length() - 1); // 删除最后一个字符 &
			url = sb.toString();
			LogUtil.other("url = " + url);
		}
		return url;
	}

	/**
	 * 检测网络是否可用
	 * @return
	 */
	public static boolean isNetworkConnected(Context context, String msg) {
		ConnectivityManager cm = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo ni = cm.getActiveNetworkInfo();
		boolean isNetworkConn = ni != null && ni.isConnectedOrConnecting();
		if (msg != null && !isNetworkConn) {
			ToastUtil.shortToast(context, msg);
		}
		return isNetworkConn;
	}

	/**
	 * 判断网络类型
	 * @param mContext
	 * @return : 0 没有网络, 1 WIFI网络, 2 WAP网络, 3 NET网络
	 */
	public static int getNetworkType(Context context) {
		int netType = 0;
		ConnectivityManager connectivityManager = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
		if (networkInfo == null) {
			return netType;
		}
		int nType = networkInfo.getType();
		if (nType == ConnectivityManager.TYPE_MOBILE) {
			String extraInfo = networkInfo.getExtraInfo();
			if (!TextUtils.isEmpty(extraInfo)) {
				if (extraInfo.toLowerCase(Locale.getDefault()).equals("cmnet")) {
					netType = 0x03;
				} else {
					netType = 0x02;
				}
			}
		} else if (nType == ConnectivityManager.TYPE_WIFI) {
			netType = 0x01;
		}
		return netType;
	}
}
