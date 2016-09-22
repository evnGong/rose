package com.island.gyy.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * Fragment 适配器
 * @author BD
 *
 */
public class BaseFragmentAdapter extends FragmentPagerAdapter {
	
	private List<Fragment> fragmentList;
	
	public BaseFragmentAdapter(FragmentManager fm, List<Fragment> fragmentList) {
		super(fm);
		this.fragmentList = fragmentList;
	}

	@Override
	public Fragment getItem(int position) {
		return fragmentList == null ? null : fragmentList.get(position);
	}

	@Override
	public int getCount() {
		return fragmentList == null ? 0 : fragmentList.size();
	}
}
