package com.island.gyy.adapter.viewholder;

import android.support.annotation.DrawableRes;
import android.support.annotation.IdRes;
import android.util.SparseArray;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.island.gyy.R;
import com.island.gyy.factory.imageloader.ImageLoaderFactory;
import com.island.gyy.factory.imageloader.ImageLoaderWrapper;


public class ViewHolder {

	@SuppressWarnings("unchecked")
	public static <T extends View> T get(View parent, @IdRes int id) {
		SparseArray<View> viewHolder = (SparseArray<View>) parent.getTag(R.id.sparryView);
		if (viewHolder == null) {
			viewHolder = new SparseArray<View>();
			parent.setTag(R.id.sparryView, viewHolder);
		}
		View childView = viewHolder.get(id);
		if (childView == null) {
			childView = parent.findViewById(id);
			viewHolder.put(id, childView);
		}
		return (T) childView;
	}

	public static TextView getText(View view, @IdRes int id) {
		TextView tv = get(view, id);
		return tv;
	}

	public static EditText getEdit(View view, @IdRes int id) {
		EditText tv = get(view, id);
		return tv;
	}

	public static ImageView getImg(View view, @IdRes int id) {
		ImageView tv = get(view, id);
		return tv;
	}

	public static TextView setText(View view, @IdRes int id, CharSequence charSequence) {
		TextView tv = get(view, id);
		tv.setText(charSequence);
		return tv;
	}

	public static ImageView setImage(View parent, @IdRes int id, @DrawableRes int resId) {
		ImageView iv = get(parent, id);
		iv.setImageResource(resId);
		return iv;
	}

	public static ImageView setNetHead(View parent, @IdRes int id, String url) {
		ImageView iv = get(parent, id);
		// TODO 网络加载图片框架
		ImageLoaderWrapper.DisplayOption lDisplayOption = new ImageLoaderWrapper.DisplayOption();
		lDisplayOption.loadingResId = id;
		lDisplayOption.loadImageSize = ImageLoaderWrapper.DisplayOption.ImageSize.small;
		ImageLoaderFactory.LOAD.getLoader().displayImage(iv,url,lDisplayOption);
		return iv;
	}

	public static ImageView setNetImage(View parent, @IdRes int id, String url) {
		ImageView iv = get(parent, id);
		// TODO 网络加载图片框架
		ImageLoaderWrapper.DisplayOption lDisplayOption = new ImageLoaderWrapper.DisplayOption();
		lDisplayOption.loadingResId = id;
		lDisplayOption.loadImageSize = ImageLoaderWrapper.DisplayOption.ImageSize.middle;
		ImageLoaderFactory.LOAD.getLoader().displayImage(iv,url,lDisplayOption);
		return iv;
	}
}
