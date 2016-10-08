package com.island.gyy.utils;





import android.text.format.DateFormat;

import com.island.gyy.base.BaseApplication;

import java.text.SimpleDateFormat;
import java.util.Calendar;
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

	public final static String Format_month="yyyy-MM-01 00:00:00";
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

	public static String getLastMonth(String format){
		Calendar lCalendar = Calendar.getInstance();
		lCalendar.add(Calendar.MONTH,-1);
		return DateFormat.format(format,lCalendar).toString();
	}

	public static String getCurrent(String format){
		Calendar lCalendar = Calendar.getInstance();
		return DateFormat.format(format,lCalendar).toString();
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