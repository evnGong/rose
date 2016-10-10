package com.island.gyy.utils;


import android.text.format.DateFormat;

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

    public final static String Format_month = "yyyy-MM-01 00:00:00";

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

    /***
     * 获取月份，
     *
     * @param format 格式
     * @param monthDuring 与当前月份的间隔
     * @return String 返回指定格式化月份时间字符串
     * **/
    public static String getMonth(String format, int monthDuring) {
        Calendar lCalendar = Calendar.getInstance();
        lCalendar.add(Calendar.MONTH, monthDuring);
        return DateFormat.format(format, lCalendar).toString();
    }

    /**
     * @method 获取当前时间
     * @author  Island_gyy 【island_yy@qq.com】
     * @date 2016/10/10 17:08
     * @describe
     * @param format 格式化类型 请参考{@link android.text.format.DateFormat}
     * @return String 被格式化的类型
     */
    public static String getCurrent(String format) {
        Calendar lCalendar = Calendar.getInstance();
        return DateFormat.format(format, lCalendar).toString();
    }

    /**
     * 获取当前年月日
     *
     * @return
     */
    public static String getShortData() {
        return new SimpleDateFormat("yyyy-MM-dd").format(new Date());
    }
}