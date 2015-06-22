package com.happy.activity;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.happy.adapter.ViewPagerAdapter;
import com.happy.coco.R;
import com.happy.util.BaseActivity;

public class WelcomeActivity extends BaseActivity implements OnClickListener, OnPageChangeListener{
	private ViewPager vp;  
	private ViewPagerAdapter vpAdapter;  
	private List<View> views;  
	private SharedPreferences spf;

	//引导图片资源  
	private static final int[] pics = { R.drawable.welcome01,  
		R.drawable.welcome02, R.drawable.welcome03,  
		R.drawable.welcome04 };  

	//底部小店图片  
	private ImageView[] dots ;  
	//记录当前选中位置  
	private int currentIndex;
	private Button btn;  
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_welcome);
		//初始化SharedPreferences
		spf = getSharedPreferences("data", Context.MODE_PRIVATE);
		//获取welcome状态判断是否是第一次打开软件
		boolean welcome = spf.getBoolean("welcome", false);
		if(welcome){
			startActivity(new Intent(this, MainActivity.class));
			finish();
		}
		
		btn = (Button)findViewById(R.id.go);
		btn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Editor edit = spf.edit();
				edit.putBoolean("welcome", true);
				edit.commit();
				startActivity(new Intent(WelcomeActivity.this, MainActivity.class));
				WelcomeActivity.this.finish();
			}
		});
		
		views = new ArrayList<View>();  
		LinearLayout.LayoutParams mParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,  
				LinearLayout.LayoutParams.WRAP_CONTENT);  

		//初始化引导图片列表  
		for(int i=0; i<pics.length; i++) {  
			ImageView iv = new ImageView(this);  
			iv.setLayoutParams(mParams);  
			iv.setImageResource(pics[i]);  
			views.add(iv);  
		}  
		vp = (ViewPager) findViewById(R.id.viewpager);  
		//初始化Adapter  
		vpAdapter = new ViewPagerAdapter(views);  
		vp.setAdapter(vpAdapter);  
		//绑定回调  
		vp.setOnPageChangeListener(this);  

		//初始化底部小点  
		initDots();  

	}  

	private void initDots() {  
		LinearLayout ll = (LinearLayout) findViewById(R.id.ll);  

		dots = new ImageView[pics.length];  

		//循环取得小点图片  
		for (int i = 0; i < pics.length; i++) {  
			dots[i] = (ImageView) ll.getChildAt(i);  
			dots[i].setEnabled(true);//都设为灰色  
			dots[i].setOnClickListener(this);  
			dots[i].setTag(i);//设置位置tag，方便取出与当前位置对应  
		}  

		currentIndex = 0;  
		dots[currentIndex].setEnabled(false);//设置为白色，即选中状态  
	}  

	/** 
	 *设置当前的引导页  
	 */  
	private void setCurView(int position)  
	{  
		if (position < 0 || position >= pics.length) {  
			return;  
		}  

		btn.setVisibility(View.VISIBLE);
		vp.setCurrentItem(position);  
	}  

	/** 
	 *这只当前引导小点的选中  
	 */  
	private void setCurDot(int positon)  
	{  
		if (positon < 0 || positon > pics.length - 1 || currentIndex == positon) {  
			return;  
		}  

		dots[positon].setEnabled(false);  
		dots[currentIndex].setEnabled(true);  

		currentIndex = positon;  
	}  

	//当滑动状态改变时调用  
	@Override  
	public void onPageScrollStateChanged(int arg0) {  
		// TODO Auto-generated method stub  

	}  

	//当当前页面被滑动时调用  
	@Override  
	public void onPageScrolled(int arg0, float arg1, int arg2) {  
	}  

	//当新的页面滑动完成以后调用
	@Override  
	public void onPageSelected(int arg0) {  
		//设置底部小点选中状态  
		setCurDot(arg0);  
		if(arg0==3){
			Animation animation = AnimationUtils.loadAnimation(WelcomeActivity.this, R.anim.gradually);
			btn.setVisibility(View.VISIBLE);
			btn.startAnimation(animation);
		}else{
			btn.setVisibility(View.GONE);
		}
	}  

	@Override  
	public void onClick(View v) {  
		int position = (Integer)v.getTag();  
		setCurView(position);  
		setCurDot(position);  
	}
	
}
