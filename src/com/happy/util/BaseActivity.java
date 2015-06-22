package com.happy.util;

import android.app.Activity;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Toast;

public class BaseActivity extends Activity{

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		LogUtil.i("Activity", getLocalClassName());
		ActivityCollector.addActivity(this);
	}
	
	/**
	 * 当前Activity  Toast
	 */
	public void toast(String msg){
		Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
	}
	
	Toast mtoast;
	//全局Toast
	public void showToast(String text){
		if(!TextUtils.isEmpty(text)){
			if(mtoast == null){
				mtoast=Toast.makeText(getApplicationContext(), text, Toast.LENGTH_SHORT);
			}else{
				mtoast.setText(text);
			}
			mtoast.show();
		}
	}
	
	public void showToast(int resId){
		if(mtoast == null){
			mtoast=Toast.makeText(getApplicationContext(), resId, Toast.LENGTH_SHORT);
		}else{
			mtoast.setText(resId);
		}
		mtoast.show();
	}
	
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		ActivityCollector.removeActivity(this);
	}
	
}
