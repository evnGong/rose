package com.island.gyy.encryption;

import java.security.MessageDigest;
import java.util.Random;

/**
 * MD5 算法工具类
 * 
 * @author BD
 * 
 */
public class MD5 {

	private MD5() {
	}

	public final static String getMessageDigest(String text) {
		if (text == null || text.length() < 1) {
			throw new RuntimeException("加密字符串为空");
		}
		char hexDigits[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
				'a', 'b', 'c', 'd', 'e', 'f' };
		try {
			MessageDigest mdTemp = MessageDigest.getInstance("MD5");
			mdTemp.update(text.getBytes());
			byte[] md = mdTemp.digest();
			int j = md.length;
			char str[] = new char[j * 2];
			int k = 0;
			for (int i = 0; i < j; i++) {
				byte byte0 = md[i];
				str[k++] = hexDigits[byte0 >>> 4 & 0xf];
				str[k++] = hexDigits[byte0 & 0xf];
			}
			return new String(str);
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * 超级加密 MD5
	 * 
	 * @param text
	 * @return
	 */
	public static String superEncryption(String text) {
		for (int i = 0; i < 20; i++) {
			text = getMessageDigest(text);
		}
		return text.substring(0, 16); // 返回十六个字符
	}

	/**
	 * 获取一个 10 ~ 20 区间的随机数
	 * 
	 * @return
	 */
	public static int getRandom() {
		return (new Random().nextInt(10)) + 10;
	}
	
	/**
	 * 使用32位MD5加密多少次
	 * @param content
	 * @param frequency
	 * @return
	 */
	public static String get32Md5WithContentAndFrequency(String content,int frequency){
		String newString  = content;
		for (int i = 0; i < frequency; i++) {
			newString = MD5.getMessageDigest(newString);
		}
		return newString;
	}
}