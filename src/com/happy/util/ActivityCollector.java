package com.happy.util;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;

/**
 *	管理所有Activity的类
 */
public class ActivityCollector {
	//存放所有的Activity
	public static List<Activity> activites = new ArrayList<Activity>();
	
	/**
	 * 添加Activity到集合 
	 */
	public static void addActivity(Activity activity){
		activites.add(activity);
	}
	
	/**
	 * 移除集合里的Activity
	 */
	public static void removeActivity(Activity activity){
		activites.remove(activity);
	}
	
	/**
	 * 销毁所有Activity
	 */
	public static void finishAll(){
		for(Activity activity : activites){
			if(!activity.isFinishing()){
				activity.finish();
			}
		}
	}
}
