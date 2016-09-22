package com.island.gyy.utils;

import java.util.List;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentManager.BackStackEntry;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;

import com.island.gyy.R;
import com.island.gyy.base.fragment.BaseFragment;

public class FragmentUtil {

	private static final String TAG = "FragmentUtil";
	
	/**
	 * 切换页面的重载，优化了fragment的切换
	 * 
	 * @param transaction
	 * @param form
	 * @param to
	 * @param contentId
	 * @param TAG
	 */
	public static void switchFragment(FragmentTransaction transaction, Fragment form, Fragment to, int contentId,
			String TAG) {
		if (to.isAdded()) {
			transaction.hide(form).show(to).commit();
		} else {
			transaction.hide(form).add(contentId, to, TAG).commit();
		}
	}

	/**
	 * 切换页面的重载，优化了fragment的切换
	 * 
	 * @param transaction
	 * @param form
	 * @param to
	 * @param contentId
	 * @param TAG
	 */
	public static void switchFragment(FragmentActivity activity, Fragment form, BaseFragment to) {
		switchFragment(
				activity.getSupportFragmentManager().beginTransaction().addToBackStack(null).setCustomAnimations(
						R.anim.slide_in_left, R.anim.slide_out_left,R.anim.slide_in_right, R.anim.slide_out_right),
				form, to, R.id.viewContent, to.TAG);
	}

	/**
	 * 切换页面的重载，优化了fragment的切换
	 * 
	 * @param transaction
	 * @param form
	 * @param to
	 * @param contentId
	 * @param TAG
	 */
	public static void switchFragmentCommitAllowingStateLoss(FragmentActivity activity, Fragment form,
			BaseFragment to) {
		final FragmentTransaction transaction = activity.getSupportFragmentManager().beginTransaction()
				.addToBackStack(null).setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_left,
						R.anim.slide_in_right, R.anim.slide_out_right);
		if (to.isAdded()) {
			transaction.hide(form).show(to).commitAllowingStateLoss();
		} else {
			transaction.hide(form).add(R.id.viewContent, to, to.TAG).commitAllowingStateLoss();
		}
	}

	/**
	 * 切换页面的重载，优化了fragment的切换
	 * 
	 * @param transaction
	 * @param form
	 * @param to
	 * @param contentId
	 * @param TAG
	 */
	public static void switchInFragment(FragmentActivity activity, BaseFragment form, BaseFragment to) {
		switchFragment(activity.getSupportFragmentManager().beginTransaction().addToBackStack(form.TAG)
				.setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_left, R.anim.slide_in_right, R.anim.slide_out_right), form, to, R.id.viewContent,
				to.TAG);
	}

	/**
	 * 切换页面的重载，优化了fragment的切换
	 * 
	 * @param transaction
	 * @param form
	 * @param to
	 * @param contentId
	 * @param TAG
	 */
	public static void switchOutFragment(FragmentActivity activity, BaseFragment form, BaseFragment to) {
		switchFragment(activity.getSupportFragmentManager().beginTransaction().addToBackStack(form.TAG)
				.setCustomAnimations(R.anim.slide_in_right, R.anim.slide_out_right,R.anim.slide_in_left, R.anim.slide_out_left), form, to, R.id.viewContent,
				to.TAG);
	}

	/**
	 * 切换页面的重载，优化了fragment的切换
	 * 
	 * @param transaction
	 * @param form
	 * @param to
	 * @param contentId
	 * @param TAG
	 */
	public static void switchOutFragment(FragmentActivity activity, BaseFragment form, String TOTAG) {
		FragmentManager manager = activity.getSupportFragmentManager();
		Fragment to = manager.findFragmentByTag(TOTAG);
		if (to != null) {
			switchFragment(
					manager.beginTransaction().setCustomAnimations(R.anim.slide_in_right, R.anim.slide_out_right,R.anim.slide_in_left, R.anim.slide_out_left), form,
					to, R.id.viewContent, TOTAG);
		}
	}

	public static final void replace(FragmentActivity fragmentActivity, Fragment fragment) {
		replace(fragmentActivity, fragment, false);
	}

	public static final void replace(FragmentActivity fragmentActivity, Fragment fragment, boolean isAddBack) {

		if(isAddBack) {
			fragmentActivity.getSupportFragmentManager().beginTransaction().addToBackStack(null)
				.setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_left, R.anim.slide_in_right, R.anim.slide_out_right)
					.replace(R.id.viewContent, fragment).commitAllowingStateLoss();
		}else {
			fragmentActivity.getSupportFragmentManager().beginTransaction()
				.setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_left, R.anim.slide_in_right, R.anim.slide_out_right)
					.replace(R.id.viewContent, fragment).commitAllowingStateLoss();
		}
	}

	public static final void replaceTowAnim(FragmentActivity fragmentActivity, Fragment fragment) {
		fragmentActivity.getSupportFragmentManager().beginTransaction()

			.setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_left)
				.replace(R.id.viewContent, fragment).commitAllowingStateLoss();
	}

	public static void switchOut(FragmentActivity activity, BaseFragment form, BaseFragment to) {
		if(to.isAdded()) {
			activity.getSupportFragmentManager().beginTransaction().addToBackStack(form.TAG)
				    .setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_left, R.anim.slide_in_right, R.anim.slide_out_right)
				    .hide(form).show(to).commit();
		}else {
			activity.getSupportFragmentManager().beginTransaction().addToBackStack(form.TAG)
					.setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_left, R.anim.slide_in_right, R.anim.slide_out_right)
					.hide(form).add(R.id.viewContent, to, to.TAG).commit();
		}
	}
	
	
	public static final void replaceTowAnim(FragmentActivity activity, BaseFragment targat) {
		activity.getSupportFragmentManager().beginTransaction()
			    .setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_left)
			    .replace(R.id.viewContent, targat, targat.TAG).commitAllowingStateLoss();
	}
	
	
	public static final void replace(FragmentActivity activity, BaseFragment targat) {
		activity.getSupportFragmentManager().beginTransaction()
				.setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_left, R.anim.slide_in_right, R.anim.slide_out_right)
				.replace(R.id.viewContent, targat, targat.TAG).commitAllowingStateLoss();
	}
	
	public static final void replace(FragmentActivity activity, BaseFragment targat, boolean isAddBackStack, String name) {
		
		activity.getSupportFragmentManager().beginTransaction().addToBackStack(name)
				.setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_left, R.anim.slide_in_right, R.anim.slide_out_right)
				.replace(R.id.viewContent, targat, targat.TAG).commitAllowingStateLoss();
	}
	
	/******从右到左*******/
	public static final void replaceOpposite(FragmentActivity activity, BaseFragment targat, boolean isAddBackStack, String name) {
		activity.getSupportFragmentManager().beginTransaction().addToBackStack(name)
		.setCustomAnimations(R.anim.slide_in_right, R.anim.slide_out_right, R.anim.slide_in_left, R.anim.slide_out_left)
		.replace(R.id.viewContent, targat, targat.TAG).commitAllowingStateLoss();
	}
	
	
	public static final void replace(FragmentActivity activity, Fragment source, BaseFragment targat, boolean isAddBackStack, String name) {
		activity.getSupportFragmentManager().beginTransaction().addToBackStack(name).remove(source)
				.setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_left, R.anim.slide_in_right, R.anim.slide_out_right)
				.replace(R.id.viewContent, targat, targat.TAG).commitAllowingStateLoss();
	}


	/**
	 * 清空Fragment返回堆栈
	 *
	 * @param fragmentManager
	 */
	public static void clearBackStack(FragmentManager fragmentManager) {
		int count = fragmentManager.getBackStackEntryCount();
		for (int i = 0; i < count; i++) {
			fragmentManager.popBackStackImmediate();
		}
	}

	public static <T extends BaseFragment> void popBackToFragment(FragmentActivity activity, String fragTag) {
		activity.getSupportFragmentManager().popBackStackImmediate(fragTag, FragmentManager.POP_BACK_STACK_INCLUSIVE);
	}

	public static void popBackImmediate(FragmentActivity activity) {
		activity.getSupportFragmentManager().popBackStackImmediate();
	}
	
	public static final void popBackStack(FragmentActivity activity) {
		activity.getSupportFragmentManager().popBackStack();
	}
	
	
	public static final void popBackStack(FragmentActivity activity, String name) {
		activity.getSupportFragmentManager().popBackStack(name, FragmentManager.POP_BACK_STACK_INCLUSIVE);
	}
	
	
	public static final void popBackStackAndRemove(FragmentActivity activity, Fragment fragment) {
		FragmentManager manager = activity.getSupportFragmentManager();
		manager.beginTransaction().remove(fragment).commit();   // remove 立即销毁视图
		manager.popBackStack();
	}
	
	public static final void popBackStackAndRemove(FragmentActivity activity, Fragment fragment, String name) {
		FragmentManager manager = activity.getSupportFragmentManager();
		manager.beginTransaction().remove(fragment).commit();   // remove 立即销毁视图
		manager.popBackStack(name, FragmentManager.POP_BACK_STACK_INCLUSIVE);
	}

	
	
	/**
	 * 打印所有已添加的 Fragment
	 * @param activity
	 */
	public static void printAllActiveFragment(FragmentActivity activity) {
		List<Fragment> fragments = activity.getSupportFragmentManager().getFragments();
		Log.d(TAG, "fragments size = " + (fragments == null ? 0 : fragments.size()));
		Fragment fragment = null;
		if(fragments != null && fragments.size() > 0) {
			for (int i = 0; i < fragments.size(); i++) {
				fragment = fragments.get(i);
				Log.d(TAG, "" + (fragment == null ? fragment : fragment.getClass().getSimpleName()));
			}
		}
	}
	
	
	/**
	 * 打印返回栈
	 * @param activity
	 */
	public static void printBackStack(FragmentActivity activity) {
		FragmentManager fragmentManager = activity.getSupportFragmentManager();
		int count = fragmentManager.getBackStackEntryCount();
		Log.d(TAG, "BackStackEntryCount = " + count);
		BackStackEntry backStackEntry = null;
		for (int i = 0; i < count; i++) {
			backStackEntry = fragmentManager.getBackStackEntryAt(i);
			Log.d(TAG, "" + (backStackEntry != null ? backStackEntry.getName() : null));
		}
	}
	
	
	public static void printAllFragment(FragmentActivity activity, String TAG) {
		Log.d(FragmentUtil.TAG, "===================================" + TAG + "===================================");
		printAllActiveFragment(activity);
		Log.d(FragmentUtil.TAG, "---------------------------------------------------------------------------------");
		printBackStack(activity);
		Log.d(FragmentUtil.TAG, "=================================================================================");
	}

	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
