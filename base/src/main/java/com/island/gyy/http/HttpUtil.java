package com.island.gyy.http;

import android.text.TextUtils;


import com.island.gyy.utils.FileUtil;
import com.island.gyy.utils.LogUtil;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.Socket;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

/**
 * HTTP 请求工具类
 * 注意所需权限 : <uses-permission android:name="android.permission.INTERNET" />
 * @author BD
 *
 */
public class HttpUtil {
	
	/** 边界值 */
	private static final String BOUNDARY = "--WebKitFormBoundaryT1HoybnYeFOGFlBR";
	
	public static final String START = "$START";
	public static final String END = "$END";
	
	
	public static byte[] get(final String url) throws Exception {
		
		if(TextUtils.isEmpty(url)) {
			LogUtil.http("URL 为空, URL = " + url); 
			return null;
		}
		HttpURLConnection conn = null;
		try {
			conn = (HttpURLConnection) new URL(url).openConnection();
		    conn.setRequestMethod("GET");
		    conn.setConnectTimeout(6000);
		    conn.setReadTimeout(6000);
		    conn.connect();
		    
			int responseCode = conn.getResponseCode();
			LogUtil.http("responseCode = " + responseCode + ", URL = " + url);
			if(responseCode >= 200 && responseCode < 300) {
				conn.getResponseMessage();
				return FileUtil.inputToByteArray(conn.getInputStream());
			}
		} catch (Exception e) {
			e.printStackTrace();  throw e;
		}finally {
			if(conn != null) conn.disconnect();
		}
		return null;
	}
	
	
	
	public static byte[] post(final String url, Map<String, String> params) throws Exception {
		
		if(TextUtils.isEmpty(url)) {
			LogUtil.http("URL 为空, URL = " + url); 
			return null;
		}
		
		if(params == null || params.size() < 1) {
			LogUtil.http("请求参数为空, params = " + params);
			return null;
		}
		
		
		HttpURLConnection conn  = null;
		OutputStream outputStream = null;
		Entry<String, String> entry = null;
		StringBuilder sb = new StringBuilder();
		Iterator<Entry<String, String>> iterator = params.entrySet().iterator();
		
		while(iterator.hasNext()) {
			entry = iterator.next();
			sb.append("--" + BOUNDARY + "\r\n");
			sb.append("Content-Disposition: form-data; name=\"" + entry.getKey() + "\"\r\n");
			sb.append("Content-Type: text/plain; charset=UTF-8\r\n"); // 普通文本
			sb.append("Content-Transfer-Encoding: 8bit\r\n");         // 消息请求(request)和响应(response)所附带的实体对象(entity)的传输形式
			sb.append("\r\n\r\n");
			sb.append(entry.getValue() + "\r\n");
		}
		sb.append("--" + BOUNDARY + "--\r\n");
		
		try {
			byte[] datas = sb.toString().getBytes("UTF-8");
			
			conn = (HttpURLConnection) new URL(url).openConnection();
			conn.setRequestMethod("POST");
			conn.setConnectTimeout(10000);
			conn.setReadTimeout(5000);
			conn.setRequestProperty("Accept-Encoding", "gzip");  // GZIP 压缩
			conn.setRequestProperty("Content-Length", String.valueOf(datas.length));     // 数据长度
			conn.addRequestProperty("Content-Type", "multipart/form-data; boundary=" + BOUNDARY);  // 边界值
			conn.setRequestProperty("connection", "Keep-Alive"); // 长链接
			conn.setRequestProperty("Charsert", "UTF-8");        // 编码
			conn.setDoInput(true);     // 允许输入输出
			conn.setDoOutput(true);
			conn.setUseCaches(false);  // 不允许缓存
			conn.connect();
		    
			outputStream = conn.getOutputStream();
			outputStream.write(datas);
			outputStream.flush();
			outputStream.close();

			int responseCode = conn.getResponseCode();
			LogUtil.http("responseCode = " + responseCode + ", URL = " + url);
			
			if(responseCode >= 200 && responseCode < 300) {
				return FileUtil.inputToByteArray(conn.getInputStream());
			}
			
		} catch (Exception e) {
			e.printStackTrace();  throw e;
		}finally {
			FileUtil.closeStream(outputStream);
			if(conn != null) conn.disconnect();
		}
		return null;
	}
	
	

	/**
	 * POST 请求提交普通文本数据
	 * @param url    : 请求地址
	 * @param key    : 键
	 * @param values : 值
	 * @return
	 * @throws Exception
	 */
	public static byte[] post(final String url, String key, String values) throws Exception {
		
		if (url == null || url.length() == 0) {
			throw new NullPointerException("URL 为 null 或空值");
		}
		
		if (key == null || key.length() == 0) {
			throw new NullPointerException("key 为 null 或空值");
		}
		
		if (values == null || values.length() == 0) {
			throw new NullPointerException("values 为 null 或空值");
		}
		
		

		int responseCode = 0;
		HttpURLConnection conn  = null;
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
		
		try {
			byte[] datas = sb.toString().getBytes("UTF-8");
			conn = (HttpURLConnection) new URL(url).openConnection();
			// 设置请求头
			conn.setRequestMethod("POST");
			conn.setConnectTimeout(10000);
			conn.setReadTimeout(5000);
			conn.setRequestProperty("Accept-Encoding", "gzip");  // GZIP 压缩
			conn.setRequestProperty("Content-Length", String.valueOf(datas.length));     // 数据长度
			conn.addRequestProperty("Content-Type", "multipart/form-data; boundary=" + BOUNDARY);  // 边界值
			conn.setRequestProperty("connection", "Keep-Alive"); // 长链接
			conn.setRequestProperty("Charsert", "UTF-8");        // 编码
			conn.setDoInput(true);    // 允许输入输出
			conn.setDoOutput(true);
			conn.setUseCaches(false); // 不允许缓存
			conn.connect();
			
			outputStream = conn.getOutputStream();
			outputStream.write(datas);
			outputStream.flush();
			outputStream.close();

			responseCode = conn.getResponseCode();
			LogUtil.http("responseCode = " + responseCode + ", URL = " + url);
			
			System.out.println(" datas = " + datas);
			if(responseCode >= 200 && responseCode < 300) {
				return FileUtil.inputToByteArray(conn.getInputStream());
			}
		} catch (Exception e) {
			e.printStackTrace(); 
			throw e;
		}finally {
			FileUtil.closeStream(outputStream);
			if(conn != null) conn.disconnect();
		}
		return null;
	}
	
	
	
	/**
	 * 表单提交数据
	 * @param url    : 请求地址
	 * @param key    : 键
	 * @param data   : 值
	 * @return
	 * @throws Exception
	 */
	public static String post(final String url, String data, String header, String value) throws Exception {
		
		if (url == null || url.length() == 0) {
			throw new NullPointerException("URL 为 null 或空值");
		}
		
		
		if (data == null || data.length() == 0) {
			throw new NullPointerException("values 为 null 或空值");
		}
		
		int responseCode = 0;
		HttpURLConnection conn  = null;
		OutputStream outputStream = null;
		
		try {
			conn = (HttpURLConnection) new URL(url).openConnection();
			conn.setRequestMethod("POST");
			conn.setConnectTimeout(10000);
			conn.setReadTimeout(5000);
			if(header != null && header.length() > 0 && value != null && value.length() > 0) {
				conn.addRequestProperty(header, value);             
			}
			conn.setRequestProperty("Accept-Encoding", "gzip");    // GZIP 压缩
			conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");  // 边界值
			conn.setRequestProperty("Content-Length", String.valueOf(data.length()));                      // 数据长度
			conn.setRequestProperty("Connection", "Keep-Alive");   // 长链接
			conn.setDoInput(true);    // 允许输入输出
			conn.setDoOutput(true);
			conn.setUseCaches(false); // 不允许缓存
			conn.connect();
			
			outputStream = conn.getOutputStream();
			outputStream.write(data.getBytes("UTF-8"));
			outputStream.flush();
			outputStream.close();
			
			responseCode = conn.getResponseCode();
			LogUtil.http("responseCode = " + responseCode + "\r\nURL = " + url + "\r\ndata = " + data);
			
			if(responseCode >= 200 && responseCode < 300) {
				return FileUtil.inputToString(conn.getInputStream(), "UTF-8");
			}
		} catch (Exception e) {
			e.printStackTrace(); 
			throw e;
		}finally {
			FileUtil.closeStream(outputStream);
			if(conn != null) conn.disconnect();
		}
		return null;
	}
	
	
	
	
	
	
	/**
	 * @param urlStr
	 * @param datas
	 * @return
	 */
	public static byte[] socketPost(String urlStr, final String datas, Map<String, String> heads){
		
		Socket socket = null;
		InputStream is = null;
		PrintWriter outWriter = null;
		ByteArrayOutputStream baos = null;
		
		boolean isSend = false;
		try {
			byte[] data = datas.getBytes("UTF-8");
			
			URL url = new URL(urlStr);
			StringBuffer sb = new StringBuffer();  
			sb.append("POST ").append(url.getPath()).append(" HTTP/1.1\r\n");  
			sb.append("Host: ").append(url.getHost()).append(":").append(url.getPort()).append("\r\n");  
			sb.append("Accept: text/*\r\n");  
			sb.append("Connection: Close\r\n");  
			sb.append("Content-Length: ").append(data.length).append("\r\n");  
			sb.append("Content-Type: application/x-www-form-urlencoded; charset=UTF-8\r\n");
			if(heads != null && heads.size() > 0) {
				for (Entry<String, String> entry : heads.entrySet())
					sb.append(entry.getKey()).append(": ").append(entry.getValue()).append("\r\n");
			}
			sb.append("\r\n");
			sb.append(datas);

			socket = new Socket(url.getHost(), url.getPort() == -1 ? 80 : url.getPort());   // 创建Socket并连接
			socket.setSoTimeout(20000);
			outWriter = new PrintWriter(socket.getOutputStream());
			if(!isSend) {
				outWriter.write(sb.toString());
				outWriter.flush();
//				socket.shutdownOutput();
				isSend = true;
			}

			is = socket.getInputStream();
			baos = new ByteArrayOutputStream();

			int len = 0;
			byte[] buf = new byte[1024];
			while((len = is.read(buf)) != -1) {
				baos.write(buf, 0, len);
			}
			String result = decodeResponse(baos.toString());
			if(result != null) {
				baos.reset();
				baos.write(result.getBytes());
			}
			return baos != null && baos.size() > 0 ? baos.toByteArray() : null;
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			FileUtil.closeStream(is);
			FileUtil.closeStream(baos);
			if(socket != null)
				try {
					socket.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
		}
		return null;
	}


	private static String decodeResponse(String str) {
		String data = null;
		int startIndexOf = str.indexOf("{");
		if(startIndexOf > 0) {
			data = str.substring(startIndexOf, str.lastIndexOf("}") + 1);
		}
		return data;
	}


	/**
	 * POST 请求提交普通文本数据, 内部采用 socket 提交, 防止多次提交数据
	 * @param url    : 请求地址
	 * @param key    : 键
	 * @param values : 值
	 * @return
	 * @throws Exception
	 */
	public static byte[] forcePost(final String urlStr, String key, String data) throws Exception {

		if (urlStr == null || urlStr.length() == 0) {
			throw new NullPointerException("URL 为 null 或空值");
		}

		if (key == null || key.length() == 0) {
			throw new NullPointerException("key 为 null 或空值");
		}

		if (data == null || data.length() == 0) {
			throw new NullPointerException("data 为 null 或空值");
		}

//		Content-Type: multipart/form-data; boundary=OCqxMF6-JxtxoMDHmoG5W5eY9MGRsTBp    // 内容类型和边界值

		Socket socket = null;
		try {
			final URL url = new URL(urlStr);
			StringBuilder sb = new StringBuilder();
			sb.append("POST ").append(url.getPath()).append(" HTTP/1.1").append("\r\n")
			  .append("Accept-Encoding: gzip").append("\r\n")
			  .append("Content-Length: ").append(data.length()).append("\r\n")
			  .append("Content-Type: multipart/form-data; boundary=").append(BOUNDARY).append("\r\n")
			  .append("Connection: Keep-Alive").append("\r\n")
			  .append("Accept-Language: zh-cn").append("\r\n")
			  .append("Charsert: UTF-8").append("\r\n")
			  .append("User-Agent: Dalvik/1.6.0 (Linux; U; Android;)").append("\r\n")
			  .append("Host: ").append(url.getHost()).append(":").append(url.getPort()).append("\r\n")
			  .append("\r\n\r\n")
			  .append("--").append(BOUNDARY).append("\r\n")
			  .append("Content-Disposition: ").append("form-data; name=\"").append(key).append("\"").append("\r\n")
			  .append("Content-Type: text/plain; charset=UTF-8;").append("\r\n")
			  .append("Content-Transfer-Encoding: 8bit").append("\r\n")
			  .append("\r\n")
			  .append(data).append("\r\n")
			  .append("--").append(BOUNDARY).append("--");
			socket = new Socket(url.getHost(), url.getPort());
			OutputStream out = socket.getOutputStream();
			out.write(sb.toString().getBytes());
			out.flush();
			System.out.println(sb.toString());
			return FileUtil.inputToByteArray(socket.getInputStream());

		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}finally {
			FileUtil.closeStream(socket);
		}
		return null;
	}



	/**
	 * POST 请求上传普通数据和文件
	 * @param url          : 请求地址
	 * @param textData     : 数据(与uploadFiles可空一个)
	 * @param uploadFiles  : 上传的文件
	 * @return
	 * @throws Exception
	 */
	public static String httpPost(final String url, Map<String, String> textData, Map<String, File> uploadFiles) throws Exception {

		if (url == null || url.length() == 0) {
			LogUtil.http("URL 为 null 或空值");
			return null;
		}

		if ((textData == null || textData.size() == 0) && (uploadFiles == null || uploadFiles.size() == 0)) {
			LogUtil.http("textData 和  uploadFiles 都没有数据");
			return null;
		}

		int length = 0;
		int responseCode = 0;
		HttpURLConnection conn = null;
		OutputStream outputStream = null;
		StringBuilder sb = new StringBuilder();
		Entry<String, File> entry = null;
		Iterator<Entry<String, File>> iterator = null;

		if (textData != null && textData.size() > 0) {
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

		if (uploadFiles != null && uploadFiles.size() > 0) {
			iterator = uploadFiles.entrySet().iterator();

			StringBuilder builder = new StringBuilder();
			while (iterator.hasNext()) {
				entry = iterator.next();
				builder.append("--")
						.append(BOUNDARY)
						.append("\r\n")
						.append("Content-Disposition: form-data; name=\"" + "file" + "\"; filename=\"" + entry.getKey() + "\"" + "\r\n")
						.append("Content-Type:application/octet-stream" + "\r\n")   // 数据为字节流
						.append("\r\n\r\n")          // 请求实体与数据之间的换行
						.append("\r\n");             // 数据写完后的回车换行
				length += entry.getValue().length(); // 文件长度
			}
			length += builder.toString().getBytes().length; // 请求参数长度
		}

		byte[] headerInfo = sb.toString().getBytes("UTF-8");
		byte[] endInfo = ("--" + BOUNDARY + "--\r\n").getBytes("UTF-8");
		length += headerInfo.length + endInfo.length;

		// 设置请求头
		conn = (HttpURLConnection) new URL(url).openConnection();
		conn.setRequestMethod("POST");
		conn.setConnectTimeout(10000);
		conn.setReadTimeout(5000);
		conn.setRequestProperty("Accept-Encoding", "gzip"); // GZIP 压缩
		conn.setRequestProperty("Content-Length", String.valueOf(length));                    // 数据长度
		conn.addRequestProperty("Content-Type", "multipart/form-data; boundary=" + BOUNDARY); // 边界值
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
		LogUtil.http("responseCode = " + responseCode + ", URL = " + url);
		final String result = responseCode == 200 ? FileUtil.inputToString(conn.getInputStream(), null) : null;
		if(conn != null) conn.disconnect();
		return result;
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
			LogUtil.http("拼装后的URL = " + url);
		}
		return url;
	}
}
