package com.island.gyy.base.activity;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import com.island.gyy.base.presender.IPresender;
import com.island.gyy.base.viewProxy.BaseViewProxy;

/**
 * Created with Android Studio.
 * User: Admin
 * Date: 2016/8/31
 * Time: 16:30
 * Describe:
 */
public abstract class  BaseViewProxyFragmentActivity<T extends BaseViewProxy> extends BaseFragmentActivity implements IPresender {

    protected T mViewProxy;

    public T getViewProxy() {
        return mViewProxy;
    }

    @Override
    public View initView() {
        createViewProxy();
        mViewProxy.setPresender(this);
        mViewProxy.create(LayoutInflater.from(this),null,null);
        return mViewProxy.getRootView();
    }

    @Override
    protected void initViewData(Bundle savedInstanceState) {
        mViewProxy.initWidget();
        mViewProxy.initListener();
        mViewProxy.initData(savedInstanceState);
    }
}
