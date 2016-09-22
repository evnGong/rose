package com.island.gyy.utils;

import android.text.Html;

/**
 * 字符串工具类
 * 
 * @author BD
 * 
 */
public class StringUtils {

	/**
	 * 获取文件扩展名
	 * @param name
	 * @return
	 */
	public static String getExtensionName(String name) {
		return name.substring(name.lastIndexOf(".") + 1);
	}
	
	/**
	 * 获取HTML文本
	 * @param text : 提示文本
	 * @param color : 特殊文本颜色
	 * @param coloursText : 特殊文本
	 * @return
	 */
	public static CharSequence getHtmlStr(String color, String coloursText, String ...hintText) {
		if(hintText != null && hintText.length == 0) {
			return Html.fromHtml(new StringBuilder().append("<font color='").append("#").append(color).append("'>").append(coloursText).append("</font>").toString());
			
		}else if(hintText != null && hintText.length == 1){
			return Html.fromHtml(new StringBuilder("<font color='#000000'>").append(hintText[0]).append("</font>")
					   .append("<font color='").append("#").append(color).append("'>").append(coloursText).append("</font>").toString());

		}else if(hintText != null && hintText.length == 2) {
			return Html.fromHtml(new StringBuilder("<font color='#000000'>").append(hintText[0]).append("</font>")
					   .append("<font color='").append("#").append(color).append("'>").append(coloursText).append("</font>")
					   .append("<font color='#000000'>").append(hintText[1]).append("</font>").toString());
		}
		return null;
	}
}
