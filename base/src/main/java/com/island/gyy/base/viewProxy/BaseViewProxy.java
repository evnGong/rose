package com.island.gyy.base.viewProxy;

import android.app.Activity;
import android.app.Dialog;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.res.ResourcesCompat;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import com.island.gyy.base.presender.IPresender;
import com.island.gyy.factory.dialog.DialogFactory;
import com.island.gyy.interfaces.IViewProxy;
import com.island.gyy.utils.InputMethodKeyboardUtils;
import com.island.gyy.utils.ToastUtil;

/**
 * View delegate base class 视图层代理的基类
 *
 * @author island_gyy
 */
public abstract class BaseViewProxy<P extends IPresender> implements IViewProxy {

	/*************** INPUT METHOD **************************/
	InputMethodManager imm;

	/***************** 组件view ********************************************/
	protected final SparseArray<View> mViews = new SparseArray<View>();
	protected View rootView;

	/******************* dialog *************************************/
	private Dialog bufferDialog;

	public abstract int getRootLayoutId();
	
	/********** PRESENDER *************/
	protected P mPresender;
	
	public void setPresender(P mPresender) {
		this.mPresender = mPresender;
	}

	@Override
	public void create(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		int rootLayoutId = getRootLayoutId();
		rootView = inflater.inflate(rootLayoutId, container, false);
	}

	@Override
	public int getOptionsMenuId() {
		return 0;
	}

	public void onCreate() {
	};

	public void onResume() {
	};

	public void onPause() {
	};

	public void onDestroy() {
	};

	@Override
	public View getRootView() {
		return rootView;
	}

	public void setRootView(View rootView) {
		this.rootView = rootView;
	}

	@Override
	public void initWidget() {
	}

	@Override
	public void initListener() {
	}

	@Override
	public void initData(Bundle savedInstanceState) {

	}

	public <T extends View> T findViewById(int id) {
		T view = (T) mViews.get(id);
		if (view == null) {
			view = (T) rootView.findViewById(id);
			mViews.put(id, view);
		}
		return view;
	}

	public <T extends View> T findViewByTag(String tag) {
		return this.rootView != null ? (T) this.rootView.findViewWithTag(tag) : null;
	}

	public void setOnClickListener(View.OnClickListener listener, int... ids) {
		if (ids == null) {
			return;
		}
		for (int id : ids) {
			findViewById(id).setOnClickListener(listener);
		}
	}

	public <T extends Activity> T getActivity() {
		return (T) rootView.getContext();
	}
	public <T extends FragmentActivity> T getFragActivity() {
		return (T) rootView.getContext();
	}

	public <T extends FragmentActivity> T getActivity(Class<T> tClass) {
		return (T) rootView.getContext();
	}

	public Resources getRes() {
		return getActivity().getResources();
	}

	public void toast(String msg) {
		ToastUtil.shortToast(getActivity(),msg);
	}

	public void cancelToast(){
		ToastUtil.cancelToast(ToastUtil.toast);
	}

	public void hideSoftInputMethod() {
		InputMethodKeyboardUtils.hideKeyboard(getActivity());
		InputMethodKeyboardUtils.forceHideKeyboard(getActivity(),rootView);
	}


	
	public void showSoftInputMethod() {
		InputMethodKeyboardUtils.showKeyboard(getActivity());
	}

	/**
	 * loading显示请稍候
	 */
	public void showLoadingDialog() {
		dismissLoadingDialog();
		bufferDialog =DialogFactory.FACTORY.getIntance().createBufferDialog(getActivity(),"加载中...");
		bufferDialog.show();
	}

	/**
	 * loading
	 */
	public void showLoadingDialog(String content) {
		bufferDialog = DialogFactory.FACTORY.getIntance().createBufferDialog(getActivity(),content);
		bufferDialog.show();
	}

	/**
	 * 结束稍候框
	 */
	public void dismissLoadingDialog() {
		if (bufferDialog != null) {
			DialogFactory.FACTORY.getIntance().dismissDialog(bufferDialog);
		}
	}

}
