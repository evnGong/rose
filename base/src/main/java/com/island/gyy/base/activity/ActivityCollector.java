package com.island.gyy.base.activity;

import android.app.Activity;

import java.lang.ref.WeakReference;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map.Entry;

/**
 * Activity管理器
 * @author BD
 * @param
 */
public class ActivityCollector {

	private ActivityCollector() {}
	private static final LinkedHashMap<String, WeakReference<Activity>> mActivitys = 
			   								new LinkedHashMap<String, WeakReference<Activity>>();

	public static LinkedHashMap<String, WeakReference<Activity>> getInstance() {
		return mActivitys;
	}
	
	/**
	 * 添加 Activity
	 * @param activity
	 * @return
	 */
	public static WeakReference<Activity> addActivity(String key, final Activity activity) {
		return mActivitys.put(key, new WeakReference<Activity>(activity));
	}

	
	/**
	 * 获取 Activity
	 * @param activity
	 * @return
	 */
	public static WeakReference<Activity> getActivity(String key) {
		return mActivitys.get(key);
	}
	

	/**
	 * 删除 Activity
	 * @param activity
	 * @return
	 */
	public static WeakReference<Activity> removeActivity(String key) {
		return mActivitys.remove(key);
	}


	/**
	 * 删除所有Activity, 退出程序
	 */
	public static void finishAllActivity() {
		if(mActivitys != null && mActivitys.size() > 0) {
			Iterator<Entry<String, WeakReference<Activity>>> iterator = mActivitys.entrySet().iterator();
			Entry<String, WeakReference<Activity>> entry = null;
			
			while(iterator.hasNext()) {
				entry = iterator.next();
				WeakReference<Activity> weakReference = entry.getValue();
				if(weakReference != null && weakReference.get() != null && !weakReference.get().isFinishing()) {
					weakReference.get().finish();  // 销毁页面
				}
			} 
			mActivitys.clear();   // 清除数据
		}
	}

	public static void exitOtherActivity(String key, Activity activity) {
		if(mActivitys != null && mActivitys.size() > 0) {
			Iterator<Entry<String, WeakReference<Activity>>> iterator = mActivitys.entrySet().iterator();
			Entry<String, WeakReference<Activity>> entry = null;

			while(iterator.hasNext()) {
				entry = iterator.next();
				if(entry.getKey().equals(key)) continue;
				WeakReference<Activity> weakReference = entry.getValue();
				if(weakReference != null && weakReference.get() != null && !weakReference.get().isFinishing()) {
					weakReference.get().finish();  // 销毁页面
				}
			}
		}else {
			addActivity(key, activity);
		}
	}
	
	public static Activity currentActivity() {
		if(mActivitys != null && mActivitys.size() > 0) {
			Iterator<Entry<String, WeakReference<Activity>>> iterator = mActivitys.entrySet().iterator();
			WeakReference<Activity> weakReference = null;
			while(iterator.hasNext()) {
				weakReference = iterator.next().getValue();
			}
			if(weakReference != null && weakReference.get() != null) {
				return weakReference.get();
			}
		}
		return null;
	}
}
