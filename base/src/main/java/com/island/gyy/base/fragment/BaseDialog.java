package com.island.gyy.base.fragment;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;


public abstract class BaseDialog extends DialogFragment {
	
	private View rootView;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		Window window = getDialog().getWindow();
			   window.requestFeature(Window.FEATURE_NO_TITLE);
			   window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
			   
		rootView = initView(inflater, container, savedInstanceState);
		initWidget(rootView, savedInstanceState);
		initData(savedInstanceState);
		return rootView;
	}

	protected abstract View initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState);

	protected abstract void initWidget(View rootView, Bundle savedInstanceState);
	
	protected void initData(Bundle savedInstanceState) {}
	
	
	@SuppressWarnings("unchecked")
	public <T extends View> T findView(int id) {
		return this.rootView != null ? (T) this.rootView.findViewById(id) : null;
	}

	@SuppressWarnings("unchecked")
	public <T extends View> T findView(String tag) {
		return this.rootView != null ? (T) this.rootView.findViewWithTag(tag) : null;
	}
}
