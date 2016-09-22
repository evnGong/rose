package com.island.gyy.adapter.viewholder;


import android.support.annotation.DrawableRes;
import android.support.annotation.IdRes;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.widget.ImageView;
import android.widget.TextView;

import com.island.gyy.R;

public class RecycleViewHolder extends RecyclerView.ViewHolder{

	private View mParent;

	public RecycleViewHolder(View itemView) {
		super(itemView);
		mParent = itemView;
	}

	public <T extends View> T get(@IdRes int id) {
		SparseArray<View> viewHolder = (SparseArray<View>) mParent.getTag(R.id.sparryView);
		if (viewHolder == null) {
			viewHolder = new SparseArray<View>();
			mParent.setTag(R.id.sparryView,viewHolder);
		}
		View childView = viewHolder.get(id);
		if (childView == null) {
			childView = mParent.findViewById(id);
			viewHolder.put(id, childView);
		}
		return (T) childView;
	}

	public TextView setText(@IdRes int id, String s) {
		TextView tv = get(id);
		tv.setText(s);
		return tv;
	}

	public ImageView setImage(@IdRes int id, @DrawableRes int resId) {
		ImageView iv = get(id);
		iv.setImageResource(resId);
		return iv;
	}

	public ImageView setNetHead(@IdRes int id, String url) {
		ImageView iv = get(id);
		// TODO 网络加载图片框架
		return iv;
	}

	public ImageView setNetImage(@IdRes int id, String url) {
		ImageView iv = get(id);
		// TODO 网络加载图片框架
		return iv;
	}

	public void setOnItemClickListner(OnClickListener onClickListener) {
		mParent.setOnClickListener(onClickListener);
	}

	public void setOnItemLongClickListner(OnLongClickListener onLongClickListener) {
		mParent.setOnLongClickListener(onLongClickListener);
	}

}

