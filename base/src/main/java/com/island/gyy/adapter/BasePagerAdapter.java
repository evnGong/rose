package com.island.gyy.adapter;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

public class BasePagerAdapter<E extends View> extends PagerAdapter{

	private List<E> list;
	
	public BasePagerAdapter(List<E> list) {
		super();
		this.list = list;
	}

	@Override
	public int getCount() {
		return list != null ? list.size() : 0;
	}

	@Override
	public boolean isViewFromObject(View view, Object object) {
		return view == object;
	}

	@Override
	public Object instantiateItem(ViewGroup container, int position) {
		E e = list.get(position);
		container.addView(e);
		return e;
	}

	@Override
	public void destroyItem(ViewGroup container, int position, Object object) {
		container.removeView((View) object);
	}

}
