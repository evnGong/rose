package com.island.gyy.utils;

import java.util.List;

import android.text.Editable;
import android.text.TextUtils;

public class NullUtils {

	public static boolean isEmptyList(List list) {
		return list == null || list.size() == 0;
	}

	public static String avoidEmptyString(String str) {
		return TextUtils.isEmpty(str) ? "" : str;
	}


	
}
