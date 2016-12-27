package com.island.gyy.utils;

import android.content.Context;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

/** 
* @Description: 输入法软键盘工具类
* @author 罗深志
* @date 2015-12-22 上午10:52:24 
* @version V1.0 
*/ 
public class InputMethodKeyboardUtils {

	private static InputState mInputState = InputState.Null;

	/**
	 * 打开或隐藏软键盘
	 * @param mContext
	 */
	private static void hideOrShowKeyboard(Context context) {
		InputMethodManager inputMethodManager = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
		inputMethodManager.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
	}

	/**
	 * 隐藏软键盘
	 * @param mContext
	 */
	public static void hideKeyboard(Context context){
		getInputStatus(context);
		if(mInputState == InputState.True){
			hideOrShowKeyboard(context);
		}
		setInputState(InputState.False);
	}

	/**
	 * 显示软键盘
	 * @param mContext
	 */
	public static void showKeyboard(Context context){
		getInputStatus(context);
		if (mInputState == InputState.True) {
			hideOrShowKeyboard(context);
		}
		setInputState(InputState.True);
	}

	/**
	 * 强制显示软键盘
	 * @param mContext
	 * @param view
	 */
	public static void forceShowKeyboard(Context context, View view) {
		InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
		imm.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.SHOW_FORCED);
        setInputState(InputState.True);
	}
	
	/**
	 * 强制隐藏软键盘
	 * @param mContext
	 * @param view
	 */
	public static void forceHideKeyboard(Context context, View view) {
		InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
		imm.hideSoftInputFromWindow(view.getWindowToken(),0);
		hideKeyboard(context);
        setInputState(InputState.False);
	}
	
	/**
	 * 获取输入法状态
	 * @param mContext
	 * @return
	 */
	private static boolean getInputStatus(Context context) {
		if(mInputState == InputState.Null){
		InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
		mInputState = imm.isActive()? InputState.True: InputState.False;
		}
		return mInputState == InputState.True;     // 若返回true，则表示输入法打开
	}

	public static void setInputState(InputState mInputState) {
		InputMethodKeyboardUtils.mInputState = mInputState;
	}
	
	public enum InputState{
		Null,True,False;
	}
}
