package com.island.gyy.http;

/**
 * Created with Android Studio.
 * User: Admin
 * Date: 2016/8/31
 * Time: 13:45
 * Describe:
 */
public enum  ResultCodeFactory {
    RESULT_CODE_FACTORY;
    private  ResultCodeCallBack mCallBack;


    public void setmCallBack(ResultCodeCallBack mCallBack) {
        this.mCallBack = mCallBack;
    }

    public ResultCodeCallBack getIntance() {
        return mCallBack;
    }
}
