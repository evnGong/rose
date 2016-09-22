package com.island.gyy.utils;


import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.text.TextUtils;
import android.view.View;

import com.island.gyy.R;

/**
 * 对话框工具类
 * 
 * @author BD
 */
public class DialogUtil {

	/**
	 * 自定义对话框
	 * 
	 * @param mContext
	 * @param layout
	 * @return
	 */
	public static Dialog showDiyDialog(Context context, View view) {
		// 使用自定义的主题
		Dialog dialog = new Dialog(context, R.style.MyDiyDialogTheme);
		dialog.setCancelable(false); // 设置抢占焦点
		dialog.setContentView(view);
		return dialog;
	}
	
	/**
	 * 关闭对话框
	 * @param dialog
	 */
	public static void dismissDialog(Dialog dialog) {
		if(dialog != null && dialog.isShowing()) {
			dialog.dismiss();
		}
	}

	public static android.support.v7.app.AlertDialog showOneDialog(Context context,
																   String title,String content,
																   String buttonText,
																   DialogInterface.OnClickListener onClick){
		android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(context);
		builder.setTitle(title);
		builder.setMessage(content);
		builder.setNegativeButton(buttonText,onClick);
		return builder.show();
	}
	public static android.support.v7.app.AlertDialog showTwoDialog(Context context,
																   String title,
																   String content,
																   String button1Text,
																   DialogInterface.OnClickListener btn1onClick,
																   String button2Text ,
																   DialogInterface.OnClickListener btn2onClick){
		android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(context);
		builder.setTitle(title);
		builder.setMessage(content);
		builder.setNegativeButton(button2Text,btn2onClick);
		builder.setNeutralButton(button1Text, btn1onClick);
		return builder.show();
	}
	public static android.support.v7.app.AlertDialog showNoDialog(Context context,
																   String title,
																   String content,
																   boolean cancel
																){
		android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(context);
		builder.setTitle(title);
		builder.setMessage(content);
		builder.setCancelable(cancel);
		return builder.show();
	}


	public static android.support.v7.app.AlertDialog showDiyDialog(Context context,
																   String title,
																   View v
																){
		android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(context);
		if(!TextUtils.isEmpty(title)) {
			builder.setTitle(title);
		}
		builder.setView(v);
		builder.setCancelable(true);
		return builder.show();
	}




}
