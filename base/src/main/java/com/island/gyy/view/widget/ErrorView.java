package com.island.gyy.view.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.island.gyy.R;
import com.island.gyy.utils.AnimUtils;
import com.island.gyy.utils.ViewUtils;
import com.island.gyy.view.impl.IViewContent;

/**
 * Created with Android Studio.
 * User: Admin
 * Date: 2016/8/31
 * Time: 14:32
 * Describe:
 */
public class ErrorView extends RelativeLayout implements IViewContent{

    private RelativeLayout mErrorLayout;
    private RelativeLayout mNormalLayout;
    private View mEmptyView;
    private View mErrorView;

    public ErrorView(Context context) {
        this((Context)null, (AttributeSet)null, 0);
    }

    public ErrorView(Context context, AttributeSet attrs) {
        this((Context)null, (AttributeSet)null, 0);
    }

    public ErrorView(Context context, AttributeSet attrs, int defStyleAttr) {
        super((Context)null, (AttributeSet)null, 0);
        initView();
    }

    private void initView() {
        LayoutInflater.from(getContext()).inflate(R.layout.error_view,this,true);
        mErrorLayout = ViewUtils.findViewById(this,R.id.viewErrorContent);
        mNormalLayout = ViewUtils.findViewById(this,R.id.viewNormalContent);
    }

    public void addEmptyView(View emptyView){
        mEmptyView = emptyView;
    }

    public void addErrorView(View errorView){
        mErrorView = errorView;
    }


    public void addNormalView(View normalView){
        LayoutParams params = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        mNormalLayout.addView(normalView,params);
    }


    public void showEmptyView(){
      View view =  mErrorLayout.findViewWithTag(R.id.viewErrorContent);
        if(view == null || view != mEmptyView){
            mErrorLayout.removeAllViews();
            mErrorLayout.setTag(R.id.viewErrorContent,mEmptyView);
            mErrorLayout.addView(mEmptyView,LayoutParams.MATCH_PARENT,LayoutParams.MATCH_PARENT);
        }
        AnimUtils.putOn(mErrorLayout).visible();
    }

    public void showErrorView(){
      View view =  mErrorLayout.findViewWithTag(R.id.viewErrorContent);
        if(view == null || view != mErrorView){
            mErrorLayout.removeAllViews();
            mErrorLayout.setTag(R.id.viewErrorContent,mErrorView);
            mErrorLayout.addView(mErrorView,LayoutParams.MATCH_PARENT,LayoutParams.MATCH_PARENT);
        }
        AnimUtils.putOn(mErrorLayout).visible();
    }

    public void showContentView(){
        AnimUtils.putOn(mErrorLayout).gone();
    }





}
