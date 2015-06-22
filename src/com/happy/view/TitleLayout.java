package com.happy.view;

import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import com.happy.coco.R;

/**
 * 自定义标题返回按钮
 */
public class TitleLayout extends RelativeLayout{

	private ImageView titleBack;

	public TitleLayout(Context context, AttributeSet attrs) {
		super(context, attrs);
		LayoutInflater.from(context).inflate(R.layout.title, this);
		
		titleBack = (ImageView) findViewById(R.id.back);
		titleBack.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				((Activity) getContext()).finish();
			}
		});
	}

}
