package com.island.gyy.view.layout;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;

import com.island.gyy.R;
import com.island.gyy.utils.AnimUtils;
import com.island.gyy.utils.ViewUtils;
import com.island.gyy.view.impl.IBotomBar;
import com.island.gyy.view.impl.ITitleBar;
import com.island.gyy.view.impl.IViewContent;

/**
 * Created with Android Studio.
 * User: Admin
 * Date: 2016/8/31
 * Time: 14:12
 * Describe:
 */
public class BaseTitleBottomRelativelayout<T extends ITitleBar,B extends IBotomBar,C extends IViewContent> extends RelativeLayout{

    private View mTitleBar;
    private View mBottomBar;
    private View mContent;

    public BaseTitleBottomRelativelayout(Context context) {
        this((Context)null, (AttributeSet)null, 0);
    }

    public BaseTitleBottomRelativelayout(Context context, AttributeSet attrs) {
        this((Context)null, (AttributeSet)null, 0);
    }

    public BaseTitleBottomRelativelayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super((Context)null, (AttributeSet)null, 0);
        initView();
    }

    private void initView() {
        LayoutInflater.from(getContext()).inflate(R.layout.base_tb_relativelayout,this,true);
    }

    public BaseTitleBottomRelativelayout addTitleView(View title){
      RelativeLayout layout = ViewUtils.findViewById(this,R.id.title);
        RelativeLayout.LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
        layout.addView(title,params);
        mTitleBar = title;
        return this;
    }

    public BaseTitleBottomRelativelayout addContentView(View content){
      RelativeLayout layout = ViewUtils.findViewById(this,R.id.content);
        RelativeLayout.LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
        layout.addView(content,params);
        mContent = content;
        return this;
    }
    public BaseTitleBottomRelativelayout addBottomView(View bottom){
      RelativeLayout layout = ViewUtils.findViewById(this,R.id.bottom);
        RelativeLayout.LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
        layout.addView(bottom,params);
        mBottomBar = bottom;
        return this;
    }

    public BaseTitleBottomRelativelayout hideTitleView(){
        AnimUtils.putOn(ViewUtils.findViewById(this,R.id.title)).gone();
        return this;
    }


    public BaseTitleBottomRelativelayout hideBottomView(){
        AnimUtils.putOn(ViewUtils.findViewById(this,R.id.bottom)).gone();
        return this;
    }


    public View getBottomBar() {
        return mBottomBar;
    }

    public View getContent() {
        return mContent;
    }

    public View getTitleBar() {
        return mTitleBar;
    }
}
