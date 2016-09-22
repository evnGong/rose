package com.island.gyy.adapter.base;


import com.island.gyy.adapter.viewholder.RecycleViewHolder;

public interface IAdapterRecycleViewDelegate <T>{

	/**
	 * 数据实体绑定
	 * 建议使用，使用ViewHolder{@link com.hnfresh.adapter.viewholder.ViewHolder}
	 * 绑定数据与view {@code
	 *      //get 方法已经使用 T extent View 泛型
	 * 		TextView t =	ViewHolder.get(convertView,R.id.textview);
	 * }
	 * 
	 * @param
	 **/
	<RH extends RecycleViewHolder> void showViewData(RH recycleViewHolder, BaseRecycleViewAdapter<T> adapter, int position);

	public int getLayoutId();
}