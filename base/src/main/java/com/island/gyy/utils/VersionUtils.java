package com.island.gyy.utils;

import android.os.Build;

/**
 * Created with Android Studio.
 * User: Admin
 * Date: 2016/9/5
 * Time: 13:40
 * Describe:
 */
public class VersionUtils {

    public static boolean isLowerVersion(int version){
        return Build.VERSION.SDK_INT < version;
    }
}
