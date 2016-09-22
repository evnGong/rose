package com.island.gyy.base;

import android.app.Activity;
import android.content.Context;

import java.util.Stack;

/**
 * 应用程序Activity管理类：用于Activity管理和应用程序退出
 * @author admin
 */
public class AppManager {
	public static Stack<Activity> activityStack;
//	public static Map<String,Activity> map; 
	private static AppManager instance;
	
	private AppManager(){}
	/**
	 * 单一实例
	 */
	public static AppManager getAppManager(){
		if(instance==null){
			instance=new AppManager();
		}
//		if(map==null){
//			map=new HashMap<String,Activity>();
//		}
		if(activityStack==null){
			activityStack=new Stack<Activity>();
		}
		return instance;
	}
	
	/**
	 * 移除Activity
	 * 
	 * @param activity
	 * @return
	 */
	public  boolean removeActivity(Activity activity) {
		return activityStack.remove(activity);
	}
	 	
	/**
	 * 是否不存在activity
	 * @return
	 */
	public boolean isEmpty(){
		if(activityStack==null || activityStack.size() == 0){
			return true;
		}
		return false;
	}
	/**
	 * 添加Activity到堆栈
	 */
	public void addActivity(Activity activity){
		
//		map.put(activity.getClass().toString(), activity);
		activityStack.add(activity);
//		System.out.println("名字::"+activity.getClass().getSimpleName());
	}
	/**
	 * 获取当前Activity（堆栈中最后一个压入的）
	 */
	public Activity currentActivity(){
		Activity activity=activityStack.lastElement();
		return activity;
	}
	/**
	 * 结束当前Activity（堆栈中最后一个压入的）
	 */
	public void finishActivity(){	
		Activity activity=activityStack.lastElement();
		finishActivity(activity);
		
	}
	/**
	 * 结束指定的Activity
	 */
	public void finishActivity(Activity activity){
		if(activity!=null){
			activityStack.remove(activity);
//			map.remove(activity.getClass().toString());
			activity.finish();
			activity=null;
		}
	}
	
	/**
	 * 结束指定类名的Activity
	 */
	public void finishActivity(Class<?> cls){
		for (Activity activity : activityStack) {
			if(activity == null){
				continue;
			}
			if(activity.getClass().equals(cls) ){
				finishActivity(activity);
			}
		}
	}
	
	/**
	 * 是否含有指定类名的Activity
	 */
	public boolean isHaveActivity(Class<?> cls){
		for (Activity activity : activityStack) {
			if(activity == null){
				continue;
			}
			if(activity.getClass().equals(cls) ){
				return true;
			}
		}
		return false;
	}
	
	/**
	 * 结束所有Activity
	 */
	public void exit(){
		for (int i = 0, size = activityStack.size(); i < size; i++){
            if (null != activityStack.get(i)){
            	
            	activityStack.get(i).finish();
            }
	    }
		activityStack.clear();
	}
	
	/**
	 * 结束除指定Activity外的所有activity
	 */
	public void exitOtherActivity(Class<?> cls){
		Activity markActivity = null;
		for (int i = 0, size = activityStack.size(); i < size; i++){
            if (null != activityStack.get(i)){
            	Activity activity = activityStack.get(i);
            	if(!activity.getClass().equals(cls) ){
            		activity.finish();
            	}else{
            		markActivity = activity;
            	}           	
            }
	    }
		activityStack.clear();
		addActivity(markActivity);
	}
	
	/**
	 * 退出应用程序
	 */
	public void AppExit(Context context) {
		try {
			exit();
//			ActivityCollector activityMgr= (ActivityCollector) mContext.getSystemService(Context.ACTIVITY_SERVICE);
//			activityMgr.restartPackage(mContext.getPackageName());
			System.exit(0);
		} catch (Exception e) {	}
	}
}