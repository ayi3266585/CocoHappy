package com.happy.activity;

import java.util.ArrayList;
import java.util.List;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.happy.biz.CocoWeatherDB;
import com.happy.coco.R;
import com.happy.entity.City;
import com.happy.entity.County;
import com.happy.entity.Province;
import com.happy.util.BaseActivity;
import com.happy.util.Const;
import com.happy.util.HttpCallbackListener;
import com.happy.util.HttpUtil;
import com.happy.util.LogUtil;
import com.happy.util.Utility;

public class ChooseAreaActivity extends BaseActivity {

	public static final int LEVEL_PROVINCE = 0;
	public static final int LEVEL_CITY = 1;
	public static final int LEVEL_COUNTY = 2;

	private ProgressDialog progressDialog;
	private TextView titleText;
	private ListView listView;
	private ImageView back;
	private ArrayAdapter<String> adapter;
	private CocoWeatherDB cocoWeatherDB;
	private List<String> dataList = new ArrayList<String>();

	/**
	 * 省列表
	 */
	private List<Province> provinceList;
	/**
	 * 市列表
	 */
	private List<City> cityList;
	/**
	 * 县列表
	 */
	private List<County> countyList;
	/**
	 * 选中的省份
	 */
	private Province selectedProvince;
	/**
	 * 选中的市
	 */
	private City selectedCity;
	/**
	 * 当前选中的级别
	 */
	private int currentLevel;
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_choose_area);
		listView = (ListView) findViewById(R.id.list_view);
		titleText = (TextView) findViewById(R.id.title_text);
		back = (ImageView)findViewById(R.id.back);
		adapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1, dataList);
		listView.setAdapter(adapter);
		cocoWeatherDB = CocoWeatherDB.getInstance(this);
		//省市县选择监听
		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				if (currentLevel == LEVEL_PROVINCE) {
					selectedProvince = provinceList.get(position);
					queryCities();
				} else if (currentLevel == LEVEL_CITY) {
					selectedCity = cityList.get(position);
					queryCounties();
				}
			}
		});
		//返回键判断当前级别,返回市列表,省列表或退出
		back.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if(currentLevel == LEVEL_COUNTY){
					queryCities();
				}else if (currentLevel == LEVEL_CITY){
					queryProvinces();
				}else if(currentLevel == LEVEL_PROVINCE){
					ChooseAreaActivity.this.finish();
				}
			}
		});
		queryProvinces();
	}

	/**
	 * 查询全国所有的省,有限从数据库查询,如果没有查到再去服务器查询
	 */
	private void queryProvinces() {
		provinceList = cocoWeatherDB.loadProvince();
		if (provinceList.size() > 0) {
			dataList.clear();
			for (Province province : provinceList) {
				dataList.add(province.getProvinceName());
			}
			adapter.notifyDataSetChanged();
			listView.setSelection(0);
			titleText.setText("中国");
			currentLevel = LEVEL_PROVINCE;
		} else {
			queryFromServer(null, "province");
		}
	}

	/**
	 * 查询选中省内所有的市,有限从数据库查询,如果没有查到再去服务器查询
	 */
	protected void queryCities() {
		cityList = cocoWeatherDB.loadCities(selectedProvince.getId());
		if (cityList.size() > 0) {
			dataList.clear();
			for (City city : cityList) {
				dataList.add(city.getCityName());
			}
			adapter.notifyDataSetChanged();
			listView.setSelection(0);
			titleText.setText(selectedProvince.getProvinceName());
			currentLevel = LEVEL_CITY;
		} else {
			queryFromServer(selectedProvince.getProvinceCode(), "city");
		}
	}

	/**
	 * 查询选中内市内所有的县,有限从数据库查询,如果没有查到再去服务器查询
	 */
	protected void queryCounties() {
		countyList = cocoWeatherDB.loadCounties(selectedCity.getId());
		LogUtil.i("cityList", countyList.size()+"");
		if (countyList.size() > 0) {
			dataList.clear();
			for (County county : countyList) {
				dataList.add(county.getCountyName());
			}
			adapter.notifyDataSetChanged();
			listView.setSelection(0);
			titleText.setText(selectedCity.getCityName());
			currentLevel = LEVEL_COUNTY;
		} else {
			queryFromServer(selectedCity.getCityCode(), "county");
		}
	}

	/**
	 * 根据传入的代号和类型从服务器上查询省市县数据
	 */
	private void queryFromServer(final String code, final String type) {
		String address;
		if (!TextUtils.isEmpty(code)) {
			address = Const.CITY + code+".xml";
		} else {
			address = Const.CITY+".xml";
		}
		showProgressDialog();
		HttpUtil.sendHttpRequest(address, new HttpCallbackListener() {

			@Override
			public void onFinish(String reponse) {
				LogUtil.i("reponse", reponse);
				boolean result = false;
				if ("province".equals(type)) {
					result = Utility.handleProvincesResponse(cocoWeatherDB,
							reponse);
				} else if ("city".equals(type)) {
					result = Utility.handleCitiesResponse(cocoWeatherDB,
							reponse, selectedProvince.getId());
				} else if ("county".equals(type)) {
					result = Utility.handleCountiesResponse(cocoWeatherDB,
							reponse, selectedCity.getId());
					LogUtil.i("result", result+"");
				}
				if (result) {
					runOnUiThread(new Runnable() {
						public void run() {
							closeProgressDialog();
							if ("province".equals(type)) {
								queryProvinces();
							} else if ("city".equals(type)) {
								queryCities();
							} else if ("county".equals(type)) {
								queryCounties();
							}
						}
					});
				}
			}

			@Override
			public void onError(Exception e) {
				runOnUiThread(new Runnable() {
					public void run() {
						closeProgressDialog();
						toast("加载失败");
					}
				});
			}
		});
	}

	/**
	 * 显示进度对话框
	 */
	private void showProgressDialog() {
		if(progressDialog == null){
			progressDialog = new ProgressDialog(this);
			progressDialog.setMessage("正在加载...");
			progressDialog.setCanceledOnTouchOutside(false);;
		}
		progressDialog.show();
	}

	/**
	 * 关闭进度对话框
	 */
	private void closeProgressDialog() {
		if(progressDialog != null){
			progressDialog.dismiss();
		}
	}
}
