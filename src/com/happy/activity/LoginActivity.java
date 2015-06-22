package com.happy.activity;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.listener.SaveListener;

import com.happy.coco.R;
import com.happy.util.BaseActivity;
import com.happy.util.Const;
import com.happy.util.LogUtil;

public class LoginActivity extends BaseActivity implements OnClickListener{
	
	private EditText et_account,et_pwd;	
	private TextView tv_weibo,tv_qq;	//微博,QQ
	private Button btn_login,btn_register;	//登陆,注册
	private ImageView back;
	//第三方app和微信通信的openapi接口
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		Bmob.initialize(this, Const.BMOB_APPID);	//初始化Bmob
		initView();
		BmobUser user = BmobUser.getCurrentUser(this);
		if(user!=null){
			//			Intent intent = new Intent(LoginActivity.this, MainActivity.class);
			//			startActivity(intent);
		}
	}
	
	public void initView(){
		et_account = (EditText)findViewById(R.id.et_account);
		et_pwd = (EditText)findViewById(R.id.et_pwd);
		tv_weibo = (TextView)findViewById(R.id.tv_weibo);
		tv_qq = (TextView)findViewById(R.id.tv_qq);
		btn_login = (Button)findViewById(R.id.btn_login);
		btn_register = (Button)findViewById(R.id.btn_register);
		back = (ImageView)findViewById(R.id.back);
		btn_login.setOnClickListener(this);
		btn_register.setOnClickListener(this);
		tv_weibo.setOnClickListener(this);
		tv_qq.setOnClickListener(this);
		back.setOnClickListener(this);
	}

	String account,pwd;
	@Override
	public void onClick(View v) {
		account = et_account.getText().toString().trim();
		pwd = et_pwd.getText().toString().trim();
		String regExp = "^1(3|5|8)[0-9]{9}$";
		switch (v.getId()) {
		case R.id.btn_login://登陆
			if(account.equals("")){
				toast("用户名不能为空!");
				return;
			}
			if(pwd.equals("")){
				toast("密码不能为空!");
				return;
			}
			if(account.matches(regExp)){
				toast("手机格式不正确");
			}
			BmobUser bmobUser = new BmobUser();
			bmobUser.setUsername(account);
			bmobUser.setPassword(pwd);
			bmobUser.login(this, new SaveListener() {

				@Override
				public void onSuccess() {
					// TODO Auto-generated method stub
					toast("登陆成功");
				}

				@Override
				public void onFailure(int arg0, String arg1) {
					// TODO Auto-generated method stub
					toast("用户名或密码错误");
					LogUtil.i("LoginError", arg0+"\n"+arg1);
				}
			});
			break;
		case R.id.btn_register://注册
			if(account.equals("")){
				toast("用户名不能为空!");
				return;
			}
			if(pwd.equals("")){
				toast("密码不能为空!");
				return;
			}
			BmobUser user = new BmobUser();
			user.setUsername(account);
			user.setPassword(pwd);
			user.signUp(this, new SaveListener() {

				@Override
				public void onSuccess() {
					// TODO Auto-generated method stub
					toast("恭喜你,注册成功!");
				}

				@Override
				public void onFailure(int arg0, String arg1) {
					// TODO Auto-generated method stub
					switch (arg0) {
					case 301:
						toast("邮箱格式不正确");
						break;
					case 202:
						toast("用户名已存在");
						break;
					case 203:
						toast("邮箱已存在");
					default:
						break;
					}
				}
			});
			break;
		case R.id.tv_weibo://微博登陆
//			 final SendAuth.Req req = new SendAuth.Req();
//			    req.scope = "snsapi_userinfo";
//			    req.state = "wechat_sdk_demo_test";
//			    api.sendReq(req);

				toast("暂不支持此功能,抱歉!");
			break;
		case R.id.tv_qq://QQ登陆
			//			//222222--appid,此为腾讯官方提供的AppID,个人开发者需要去QQ互联官网为自己的应用申请对应的AppId
			//			BmobUser.qqLogin(LoginActivity.this, "222222", new OtherLoginListener() {
			//				
			//				@Override
			//				public void onSuccess(JSONObject arg0) {
			//					// TODO Auto-generated method stub
			//					
			//				}
			//				
			//				@Override
			//				public void onFailure(int arg0, String arg1) {
			//					// TODO Auto-generated method stub
			//					
			//				}
			//
			//				@Override
			//				public void onCancel() {
			//					// TODO Auto-generated method stub
			//					
			//				}
			//				
			//			});
			toast("暂不支持此功能,抱歉!");
			break;
		case R.id.back:
			onBackPressed();
			break;
		default:
			break;
		}
	}
	
}
