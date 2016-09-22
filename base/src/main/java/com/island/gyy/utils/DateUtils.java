package com.island.gyy.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 时间工具类
 * 
 * @Author BD
 */
public class DateUtils {

	public final static int second = 1000;
	public final static int minute = 60;
	public final static int hour = 60;
	public final static int day = 24;
	/**
	 * 获取当前时间
	 * 
	 * @return
	 */
	public static String getData() {
		return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
	}

	public static String getData(long time) {
		return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date(time));
	}
	
	
	/**
	 * 获取当前时间
	 * 
	 * @return
	 */
	public static String getShortData() {
		return new SimpleDateFormat("yyyy-MM-dd").format(new Date());
	}
}