package com.island.gyy.factory.dialog;

import android.app.Dialog;
import android.content.Context;

/**
 * Created with Android Studio.
 * User: Admin
 * Date: 2016/8/31
 * Time: 10:50
 * Describe:
 */
public interface IDialogFactory {
    Dialog createBufferDialog(Context context,String content);

    void dismissDialog(Dialog dialog);
}
