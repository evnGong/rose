package com.island.gyy.utils;

import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.util.Range;

public class NumberFormatUtils {

	public static int parseInt(String value) {
		int i = 0;
		try {
			i = NumberFormat.getIntegerInstance().parse(value).intValue();
		} catch (ParseException e) {
			// TODO: handle exception
			return 0;
		}
		return i;
	}

	public static float parseFloat(String gradeStr) {
		// TODO Auto-generated method stub
		float f = 0f;
		try {
			f = NumberFormat.getInstance().parse(gradeStr).floatValue();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			return 0;
		}
		return f;
	}

	/***
	 * 价格格式 00.00
	 **/
	public static String parsePriceStr(double price) {
		String priceStr = "0.00";
		price *= 1000;
		long priceLong = Math.round(price);
		price = priceLong / 1000.0d;

		NumberFormat format = NumberFormat.getInstance();
		format.setMinimumFractionDigits(2);
		return format.format(price);
	}

	public static final String yearMD = "yyyy-MM-dd";

	public static String parseData(String data, String formatStr) {
		String date = "";
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			Date d = formatter.parse(data);
			formatter = new SimpleDateFormat(formatStr);
			date = formatter.format(d);
		} catch (Exception e) {
			// TODO: handle exception
			return date;
		}

		return date;
	}
}
