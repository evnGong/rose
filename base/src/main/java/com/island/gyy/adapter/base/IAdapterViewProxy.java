package com.island.gyy.adapter.base;

import android.view.View;

public interface IAdapterViewProxy<T> {

	/**
	 * 数据实体绑定 建议使用，使用ViewHolder{@link com.hnfresh.adapter.viewholder.ViewHolder}
	 * 绑定数据与view {@code
	 *      //get 方法已经使用 T extent View 泛型
	 * 		TextView t =	ViewHolder.get(convertView,R.id.textview);
	 * }
	 * 
	 * @param
	 **/
	void showViewData(View v, BaseAdapter<T> adapter, int position);

	int getLayoutId();
}
