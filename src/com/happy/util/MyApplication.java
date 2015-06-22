package com.happy.util;

import android.app.Application;
import android.content.Context;

/**
 * 全局获取Context
 *
 */
public class MyApplication extends Application {

	private static Context context;

	@Override
	public void onCreate() {
		context = getApplicationContext();
	}
	
	public static Context getContext(){
		return context;
	}
}
