package com.island.gyy.encryption;

import android.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/**
 * AES 加密工具类
 * 
 * @author BD
 * 
 */
public class AESEncryption {
	
	/**
	 * 令牌加密的Key和偏移量
	 */
	private static final String KEY    = "1234567898765432";
	private static final String OFFSET = "0392039203920300";
	
	/**
	 * 请求参数加密的Key和偏移量
	 */
	private static final String SUPERKEY    = "8888888888888888";
	private static final String SUPEROFFSET = "8888888888888888";

	/**
	 * 普通加密
	 * @param cleartext
	 * @param seed
	 * @param offset
	 * @return
	 */
	public static String encrypt(String cleartext) {
		if(cleartext == null) 
			return null;
		try {
			Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
			byte[] raw = KEY.getBytes();
			SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
			IvParameterSpec iv = new IvParameterSpec(OFFSET.getBytes()); // 使用CBC模式，需要一个向量iv，可增加加密算法的强度
			cipher.init(Cipher.ENCRYPT_MODE, skeySpec, iv);
			byte[] encrypted = cipher.doFinal(cleartext.getBytes("utf-8"));
			return Base64.encodeToString(encrypted, 0); // 此处使用BASE64做转码。
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 普通解密
	 *
	 * @param sSrc
	 * @param sKey
	 * @param ivParameter
	 * @return
	 */
	public static String decrypt(String sSrc) {
		if(sSrc == null)
			return null;
		try {
			byte[] raw = KEY.getBytes("ASCII");
			SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
			Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
			IvParameterSpec iv = new IvParameterSpec(OFFSET.getBytes());
			cipher.init(Cipher.DECRYPT_MODE, skeySpec, iv);
			byte[] encrypted1 = Base64.decode(sSrc, -1);// 先用base64解密
			byte[] original = cipher.doFinal(encrypted1);
			String originalString = new String(original, "utf-8");
			return originalString;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	// ==================================================================================================================

	/**
	 * 超级加密
	 *
	 * @param cleartext
	 * @param seed
	 * @param offset
	 * @return
	 */
	public static String superEncrypt(String cleartext) {
		if(cleartext == null)
			return null;
		try {
			Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
			byte[] raw = SUPERKEY.getBytes();
			SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
			IvParameterSpec iv = new IvParameterSpec(SUPEROFFSET.getBytes()); // 使用CBC模式，需要一个向量iv，可增加加密算法的强度
			cipher.init(Cipher.ENCRYPT_MODE, skeySpec, iv);
			byte[] encrypted = cipher.doFinal(cleartext.getBytes("utf-8"));
			return Base64.encodeToString(encrypted, 0); // 此处使用BASE64做转码。
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 超级解密
	 *
	 * @param sSrc
	 * @param sKey
	 * @param ivParameter
	 * @return
	 */
	public static String superDecrypt(String sSrc) {
		if(sSrc == null)
			return null;
		try {
			byte[] raw = SUPERKEY.getBytes("ASCII");
			SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
			Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
			IvParameterSpec iv = new IvParameterSpec(SUPEROFFSET.getBytes());
			cipher.init(Cipher.DECRYPT_MODE, skeySpec, iv);
			byte[] encrypted1 = Base64.decode(sSrc, -1);// 先用base64解密
			byte[] original = cipher.doFinal(encrypted1);
			String originalString = new String(original, "utf-8");
			return originalString;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	// ===================================================================================================================

	/**
	 * 加密
	 *
	 * @param cleartext
	 *            : 需要加密的文本
	 * @param seed
	 *            : 密钥
	 * @param offset
	 *            : 偏移量值
	 * @return
	 */
	public static String encrypt(String cleartext, String seed, String offset) {
		try {
			Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
			byte[] raw = seed.getBytes();
			SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
			IvParameterSpec iv = new IvParameterSpec(offset.getBytes()); // 使用CBC模式，需要一个向量iv，可增加加密算法的强度
			cipher.init(Cipher.ENCRYPT_MODE, skeySpec, iv);
			byte[] encrypted = cipher.doFinal(cleartext.getBytes("utf-8"));
			return Base64.encodeToString(encrypted, 0); // 此处使用BASE64做转码。
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 解密
	 * 
	 * @param encrypted
	 *            : 密文
	 * @param seed
	 *            : 密钥
	 * @param offset
	 *            : 偏移量值
	 * @return
	 */
	public static String decrypt(String encrypted, String seed, String offset) {
		try {
			byte[] raw = seed.getBytes("ASCII");
			SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
			Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
			IvParameterSpec iv = new IvParameterSpec(offset.getBytes());
			cipher.init(Cipher.DECRYPT_MODE, skeySpec, iv);
			byte[] encrypted1 = Base64.decode(encrypted, -1);// 先用base64解密
			byte[] original = cipher.doFinal(encrypted1);
			String originalString = new String(original, "utf-8");
			return originalString;
		} catch (Exception ex) {
			return null;
		}
	}
}
