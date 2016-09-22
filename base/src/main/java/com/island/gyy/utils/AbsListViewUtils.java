package com.island.gyy.utils;

import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ListAdapter;
import android.widget.ListView;

public class AbsListViewUtils {

	/**
	 * 手动计算并设置 ListView 的高度, 解决 ScrollView 和 ListView 滑动事件冲突导致只显示一行的问题
	 * @param listView
	 */
	public static void setListViewHeightBasedOnChildren(ListView listView) {
		ListAdapter listAdapter = listView.getAdapter();
		if (listAdapter == null) {
			return;
		}
		int totalHeight = 0;
		for (int i = 0; i < listAdapter.getCount(); i++) {
			View listItem = listAdapter.getView(i, null, listView);
			listItem.measure(0, 0);
			totalHeight += listItem.getMeasuredHeight();
		}
		ViewGroup.LayoutParams params = listView.getLayoutParams();
		params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
		listView.setLayoutParams(params);
	}
	
	
	public static View getVisibleView(AbsListView absListView, int itemIndex) {
		 // 第一个可显示控件的位置，
		 int visiblePosition = absListView.getFirstVisiblePosition();
		 if (itemIndex - visiblePosition >= 0) {
			 return absListView.getChildAt(itemIndex - visiblePosition);
		 }
		return absListView;
	}
	
	
	
	
	
	/**
	 * 展开所有扩展列表
	 */
	public static void expandAllList(ExpandableListView expandableListView, ExpandableListAdapter expandableListAdapter) {
		
		if(expandableListAdapter == null) {
			throw new NullPointerException("适配器为空");
		}
		
		if(expandableListView == null) {
			throw new NullPointerException("扩展列表为空");
		}
		
		for(int i = 0; i < expandableListAdapter.getGroupCount(); i++)  
			expandableListView.expandGroup(i);
	}
}
