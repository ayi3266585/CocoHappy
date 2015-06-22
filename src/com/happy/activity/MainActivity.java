package com.happy.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

import com.happy.coco.R;
import com.happy.util.ActivityCollector;
import com.happy.util.BaseActivity;
import com.happy.util.Const;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;

public class MainActivity extends BaseActivity {
	private Button submit;
	private Button weather;
	//退出程序的时间指针
    long clickT = 0;
    //第三方app和微信通信的openapi接口
    private IWXAPI api;
    
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		regToWx();
		submit = (Button) findViewById(R.id.submit);
		weather = (Button) findViewById(R.id.weather);
		submit.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				startActivity(new Intent(MainActivity.this, LoginActivity.class));
			}
		});
		weather.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				startActivity(new Intent(MainActivity.this, ChooseAreaActivity.class));
			}
		});
		
	}
	
	/**
	 * 退出程序
	 */
	@Override
	public void onBackPressed() {
        long currentT = System.currentTimeMillis();
		if (currentT - clickT > 1500) {
            clickT = currentT;
            Toast.makeText(getApplication(), "再点一次退出程序", Toast.LENGTH_SHORT)
                    .show();
        } else {
            ActivityCollector.finishAll();
        }
	}
	
	/**
	 * 注册到微信
	 */
	private void regToWx(){
		//通过WXAPIFactory工厂获得IWXAPI的实例
		api = WXAPIFactory.createWXAPI(this, Const.WECHAT_APPID, true);
		//将应用的AppId注册到微信
		api.registerApp(Const.WECHAT_APPID);
	}
	
	
}
